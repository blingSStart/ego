/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ItemDescIServiceImpl
 * Author:   sjx
 * Date:     2019/11/18 20:39
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.item.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.dao.JedisDao;
import com.ego.item.service.ItemDescIService;
import com.ego.pojo.*;
import com.ego.service.*;
import com.ego.utils.JsonUtils;
import com.ego.vo.MyItemV;
import com.ego.vo.MyKV;
import com.ego.vo.VsftdpFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/18
 * @since 1.0.0
 */
@Service
public class ItemDescIServiceImpl implements ItemDescIService {
    @Autowired
    private JedisDao jedisDao;
    @Autowired
    private VsftdpFile vsftdpFile;
    @Reference
    private ItemDescService itemDescService;
    @Reference
    private ItemService itemService;
    @Reference
    private ItemParamItemService itemParamItemService;

    //显示商品介绍
    @Override
    public TbItemDesc showDesc(Long itemid) {
        //先去redis中查找是否有数据存在
        String descKey = vsftdpFile.getItemDescKey() + itemid;
        if (jedisDao.exists(descKey)) {
            String jsonRes = jedisDao.get(descKey);
            return JsonUtils.jsonToPojo(jsonRes, TbItemDesc.class);
        } else {
            //不存在去myql中找
            TbItemDesc res = itemDescService.getDescByItemIDSearch(itemid);
            //将res放进redis中
            jedisDao.add(descKey, JsonUtils.objectToJson(res));
            return res;
        }
    }

    //显示商品规格参数
    @Override
    public String showParam(Long itemid) {
        //先去redis中查找是否有数据存在
        String descKey = vsftdpFile.getItemParamKey() + itemid;
        if (jedisDao.exists(descKey)) {
            String res = jedisDao.get(descKey);
            return res;
        } else {
            //不存在去myql中找
            TbItemParamItem itemParamItem = itemParamItemService.getItemParamItemByItemid(itemid);
            //将res放进redis中
            String paramData = itemParamItem.getParamData();
            List<MyItemV> myItem = JsonUtils.jsonToList(paramData, MyItemV.class);
            StringBuffer sb = new StringBuffer();
            for (MyItemV itemV : myItem) {
                List<MyKV> params = itemV.getParams();
                for (int i = 0; i < params.size(); i++) {
                    if (i == 0) {
                        sb.append("<table>");
                        sb.append("<tr>");
                        sb.append("<td>");
                        sb.append(itemV.getGroup());
                        sb.append("</td>");
                        sb.append("<td>");
                        sb.append(params.get(i).getK());
                        sb.append("</td>");
                        sb.append("<td>");
                        sb.append(params.get(i).getV());
                        sb.append("</td>");
                        sb.append("</tr>");
                        sb.append("</table>");
                    } else {
                        sb.append("<table>");
                        sb.append("<tr>");
                        sb.append("<td>");
                        sb.append("</td>");
                        sb.append("<td>");
                        sb.append(params.get(i).getK());
                        sb.append("</td>");
                        sb.append("<td>");
                        sb.append(params.get(i).getV());
                        sb.append("</td>");
                        sb.append("</tr>");
                        sb.append("</table>");
                    }
                }
                jedisDao.add(descKey, sb.toString());
            }
            return sb.toString();
        }
    }
}