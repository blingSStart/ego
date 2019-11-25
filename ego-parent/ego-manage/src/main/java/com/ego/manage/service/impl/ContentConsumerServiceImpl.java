/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ContentConsumerServiceImpl
 * Author:   sjx
 * Date:     2019/11/13 9:47
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.dao.JedisDao;
import com.ego.manage.service.ContentConsumerService;
import com.ego.pojo.TbContent;
import com.ego.service.ContentService;
import com.ego.utils.IDUtils;
import com.ego.utils.JsonUtils;
import com.ego.vo.EgoResult;
import com.ego.vo.VsftdpFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/13
 * @since 1.0.0
 */
@Service
public class ContentConsumerServiceImpl implements ContentConsumerService {
    @Autowired
    private JedisDao jedisDao;
    @Autowired
    private VsftdpFile vsftdpFile;
    @Reference
    private ContentService contentService;

    //根据catid分页显示商品内容详情
    @Override
    public Map<String, Object> getAllContent(Long categoryId, int page, int rows) {
//        查询行数
        int count = contentService.getCountContentService(categoryId);
//        查询数据
        List<TbContent> contentList = contentService.getAllContentService(categoryId, page, rows);
//        封装数据
        Map<String, Object> map = new HashMap<>();
        map.put("total", count);
        map.put("rows", contentList);
        return map;
    }

    //新增内容
    @Override
    public EgoResult addContent(TbContent content) {
        EgoResult result = new EgoResult();
        content.setId(IDUtils.genItemId());
        Date date = new Date();
        content.setCreated(date);
        content.setUpdated(date);
        int res = contentService.addContentService(content);
        //判断redis中是否有数据
        String bigAdvKey = vsftdpFile.getRedisBigAdvKey();
        if (res > 0) {
            if (jedisDao.exists(bigAdvKey)) {
                //存在将其取出
                String jsonKey = jedisDao.get(bigAdvKey);
                //判断jsonKey是否为空
                if (jsonKey != null && !"".equalsIgnoreCase(jsonKey)) {
                    List<TbContent> contentList = JsonUtils.jsonToList(jsonKey, TbContent.class);
                    //再将新增的对象封装给解析后的list
                    contentList.add(content);
                    //再将list重新封装进redis
                    String jsonRes = jedisDao.add(bigAdvKey, JsonUtils.objectToJson(contentList));
                    if ("OK".equalsIgnoreCase(jsonRes)) {
                        result.setStatus(200);
                    }
                }
            }
            result.setStatus(200);
        }
        return result;
    }

    //修改内容
    @Override
    public EgoResult updateContent(TbContent content) {
        EgoResult result = new EgoResult();
        Date date = new Date();
        content.setUpdated(date);
        int res = contentService.updateContentService(content);
        String bigAdvKey = vsftdpFile.getRedisBigAdvKey();
        if (res > 0) {
            if (jedisDao.exists(bigAdvKey)) {
                //存在将其取出
                String jsonKey = jedisDao.get(bigAdvKey);
                //判断jsonKey是否为空
                if (jsonKey != null && !"".equalsIgnoreCase(jsonKey)) {
                    List<TbContent> contentList = JsonUtils.jsonToList(jsonKey, TbContent.class);
                    //再将要修改的对象取出来
                    int index = -1;
                    for (int i = 0; i < contentList.size(); i++) {
                        //俩者id相同说明list中有该数据
                        if ((Long) contentList.get(i).getId() == content.getId()) {
                            index = i;
                        }
                    }
                    if (index == -1) {
                        //index没有改变说明数据出现问题删除就完了
                        jedisDao.delete(bigAdvKey);
                    } else {
                        //说明正常，将值替换即可,先把创建时间拿出来以免覆盖
                        content.setCreated(contentList.get(index).getCreated());
                        contentList.remove(index);
                        contentList.add(index, content);
                        //封装进redis
                        String jsonRes = jedisDao.add(bigAdvKey, JsonUtils.objectToJson(contentList));
                    }
                }
            }
            result.setStatus(200);
        }
        return result;
    }

    //删除内容
    @Override
    public EgoResult deleteContent(List<Long> ids) {
        EgoResult result = new EgoResult();
        int res = 0;
        for (Long id : ids) {
            res += contentService.deleteContentService(id);
        }
        String bigAdvKey = vsftdpFile.getRedisBigAdvKey();
        if (ids.size() == res) {
            if (jedisDao.exists(bigAdvKey)) {
                //存在将其取出
                String jsonKey = jedisDao.get(bigAdvKey);
                //判断jsonKey是否为空
                if (jsonKey != null && !"".equalsIgnoreCase(jsonKey)) {
                    List<TbContent> contentList = JsonUtils.jsonToList(jsonKey, TbContent.class);
                    List<TbContent> remList = new ArrayList<>();
                    for (Long id : ids) {
                        TbContent remContent = null;
                        for (int i = 0; i < contentList.size(); i++) {
                            //如果相同就是找到要删除的元素了
                            if ((Long) contentList.get(i).getId() == id) {
                                remContent = contentList.get(i);
                                break;
                            }
                        }
                        remList.add(remContent);
                    }
                    //删除数据
                    contentList.removeAll(remList);
                    jedisDao.add(bigAdvKey,JsonUtils.objectToJson(contentList));
                }
            }
            result.setStatus(200);
        }
        return result;
    }
}