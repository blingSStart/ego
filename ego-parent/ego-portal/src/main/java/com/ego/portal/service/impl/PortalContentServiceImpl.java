/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: PortalContentServiceImpl
 * Author:   sjx
 * Date:     2019/11/13 21:36
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.portal.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.dao.JedisDao;
import com.ego.pojo.TbContent;
import com.ego.portal.service.PortalContentService;
import com.ego.service.ContentService;
import com.ego.utils.JsonUtils;
import com.ego.vo.VsftdpFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/13
 * @since 1.0.0
 */
@Service
public class PortalContentServiceImpl implements PortalContentService {

    @Reference
    private ContentService contentService;
        @Autowired
    private VsftdpFile vsftdpFile;
    @Autowired
    private JedisDao jedisDao;

    //在首页显示轮播广告
    @Override
    public String showBigAdv() {
        //先判断redis中是否有数据
        String bigAdvKey = vsftdpFile.getRedisBigAdvKey();
        String jsonRes = jedisDao.get(bigAdvKey);
        if (jsonRes == null || !jedisDao.exists(bigAdvKey) || "".equalsIgnoreCase(jsonRes)) {
            //说明redis没有数据就去mysql查
            List<TbContent> contentList = contentService.getAllContentByCatId((long) vsftdpFile.getContentCatId());
            //将contentList添加进入redis中
            jedisDao.add(bigAdvKey, JsonUtils.objectToJson(contentList));
            jsonRes = JsonUtils.objectToJson(contentList);
        }
        List<Object> list = new ArrayList<>();
        List<TbContent> contentList = JsonUtils.jsonToList(jsonRes, TbContent.class);
        for (TbContent tbContent : contentList) {
            Map<Object, Object> map = new HashMap<>();
            map.put("srcB", tbContent.getPic2());
            map.put("height", 240);
            map.put("alt", "图片正在加载");
            map.put("width", 670);
            map.put("src", tbContent.getPic());
            map.put("widthB", 550);
            map.put("href", tbContent.getUrl());
            map.put("heightB", 240);
            list.add(map);
        }
        return JsonUtils.objectToJson(list);
    }
}