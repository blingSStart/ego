/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ItemConsumerServiceImpl
 * Author:   sjx
 * Date:     2019/11/7 19:23
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.manage.producer.MyProducer;
import com.ego.manage.service.ItemConsumerService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;
import com.ego.service.ItemCatService;
import com.ego.service.ItemDescService;
import com.ego.service.ItemParamItemService;
import com.ego.service.ItemService;
import com.ego.utils.HttpClientUtil;
import com.ego.utils.JsonUtils;
import com.ego.vo.EgoResult;
import com.ego.utils.FtpUtil;
import com.ego.utils.IDUtils;
import com.ego.vo.VsftdpFile;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/7
 * @since 1.0.0
 */
@Service
public class ItemConsumerServiceImpl implements ItemConsumerService {
    @Autowired
    private VsftdpFile vsftdpFile;
    @Reference
    private ItemParamItemService itemParamItemService;
    @Reference
    private ItemService itemService;
    @Reference
    private ItemDescService itemDescService;
    @Reference
    private ItemCatService itemCatService;
    @Autowired
    private MyProducer myProducer;

    //分页显示所有商品
    @Override
    public Map<String, Object> getAllItems(int page, int rows) {
        return itemService.getAllItems(page, rows);
    }

    //改变商品状态
    @Override
    public EgoResult updateItems(List<Long> ids, byte status) {
        EgoResult result = new EgoResult();
        int n = 0;
        for (Long id : ids) {
            n += itemService.updateItems(id, status);
        }
        if (n == ids.size()) {
            if (status == 1) {
                //上架
                //调用HttpClient发送远程请求
                for (Long id : ids) {
//                    myProducer.sendMessage(id.toString());
                    Map<String, String> map = new HashMap<>();
                    TbItem item = itemService.getItemById(id);
                    map.put("id", String.valueOf(item.getId()));
                    map.put("item_title", item.getTitle());
                    map.put("item_sell_point", item.getSellPoint());
                    map.put("item_price", String.valueOf(item.getPrice()));
                    map.put("item_image", item.getImage());
                    map.put("item_category_name", itemCatService.getItemCatByCatId(item.getCid()).getName());
                    map.put("item_desc", itemDescService.getDescByItemIDSearch(item.getId()).getItemDesc());
                    //将数据封装进map中
                    String jsonRes = HttpClientUtil.doPost(vsftdpFile.getSearchUrl() + "solr/add", map);
                    EgoResult res = JsonUtils.jsonToPojo(jsonRes, EgoResult.class);
                    if (res.getStatus() != 200) {
                        //出现脏读，直接删除所有solr中的数据
                        HttpClientUtil.doPost(vsftdpFile.getSearchUrl() + "solr/delAll");
                    }
                }
            } else {
                //下架删除
                for (Long id : ids) {
                    Map<String, String> map = new HashMap<>();
                    map.put("id", String.valueOf(id));
                    String jsonRes = HttpClientUtil.doPost(vsftdpFile.getSearchUrl() + "solr/del", map);
                    EgoResult res = JsonUtils.jsonToPojo(jsonRes, EgoResult.class);
                    if (res.getStatus() != 200) {
                        //出现脏读，直接删除所有solr中的数据
                        HttpClientUtil.doPost(vsftdpFile.getSearchUrl() + "solr/delAll");
                    }
                }
            }
            result.setStatus(200);
        }
        return result;
    }

    //上传图片
    @Override
    public Map<String, Object> imgUpload(MultipartFile uploadFile) {
        Map<String, Object> map = new HashMap<>();
        try {
            String filename = IDUtils.genImageName() + uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().lastIndexOf("."));
            boolean flag = FtpUtil.uploadFile(vsftdpFile.getHost(), vsftdpFile.getPort(), vsftdpFile.getUsername(), vsftdpFile.getPassword(), vsftdpFile.getBasePath(), vsftdpFile.getFilePath(), filename, uploadFile.getInputStream());
            if (flag) {
                map.put("error", 0);
                map.put("url", vsftdpFile.getUrl() + filename);
            } else {
                map.put("error", 1);
                map.put("message", "上传图片失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    //添加商品及商品信息
    @Override
    public EgoResult addItemADesc(TbItem item, String desc, String itemParams) {
        //添加商品
        EgoResult re = new EgoResult();
        TbItemDesc itemDesc = new TbItemDesc();
        TbItemParamItem itemParamItem = new TbItemParamItem();
        int result = 0;
        long itemId = IDUtils.genItemId();
        item.setId(itemId);
        item.setStatus((byte) 1);
        Date date = new Date();
        item.setCreated(date);
        item.setUpdated(date);
        result += itemService.addItem(item);
        //添加商品描述
        itemDesc.setItemId(itemId);
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);
        itemDesc.setItemDesc(desc);
        result += itemDescService.addItemDesc(itemDesc);
        //添加商品规格信息
        itemParamItem.setItemId(itemId);
        itemParamItem.setParamData(itemParams);
        itemParamItem.setCreated(date);
        itemParamItem.setUpdated(date);
        result += itemParamItemService.addItemParamItemService(itemParamItem);
        if (result == 3) {
            re.setStatus(200);
        }
        return re;
    }

}