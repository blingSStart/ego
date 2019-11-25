/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ItemIServiceImpl
 * Author:   sjx
 * Date:     2019/11/18 20:02
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.item.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.dao.JedisDao;
import com.ego.item.service.ItemIService;
import com.ego.vo.ItemChild;
import com.ego.pojo.TbItem;
import com.ego.service.ItemService;
import com.ego.utils.JsonUtils;
import com.ego.vo.VsftdpFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/18
 * @since 1.0.0
 */
@Service
public class ItemIServiceImpl implements ItemIService {
    @Reference
    private ItemService itemService;
    @Autowired
    private JedisDao jedisDao;
    @Autowired
    private VsftdpFile vsftdpFile;

    //根据id查询商品详情
    @Override
    public ItemChild getItemById(Long id) {
        //根据key值查询redis数据库中是否有值
        String itemKey = vsftdpFile.getItemKey();
        if (jedisDao.exists(itemKey)) {
            String jsonRes = jedisDao.get(itemKey);
            ItemChild res = JsonUtils.jsonToPojo(jsonRes, ItemChild.class);
            return res;
        } else {
            //redis中没有值，从mysql中拿
            TbItem item = itemService.getItemById(id);
            ItemChild res = new ItemChild();
            String image = item.getImage();
            if (image != null && !"".equalsIgnoreCase(image)) {
                res.setImages(image.split(","));
            } else {
                res.setImages(new String[1]);
            }
            res.setId(item.getId());
            res.setPrice(item.getPrice());
            res.setTitle(item.getTitle());
            res.setSellPoint(item.getSellPoint());
            //将查询结果放入redis中
            jedisDao.add(itemKey + id, JsonUtils.objectToJson(res));
            return res;
        }
    }
}