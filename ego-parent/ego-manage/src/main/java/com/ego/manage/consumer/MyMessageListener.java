/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: MyMessageListener
 * Author:   sjx
 * Date:     2019/11/19 17:55
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.manage.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.dao.JedisDao;
import com.ego.pojo.TbItem;
import com.ego.service.ItemCatService;
import com.ego.service.ItemDescService;
import com.ego.service.ItemService;
import com.ego.utils.HttpClientUtil;
import com.ego.utils.JsonUtils;
import com.ego.vo.EgoResult;
import com.ego.vo.VsftdpFile;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/19
 * @since 1.0.0
 */
public class MyMessageListener implements MessageListener {
    @Autowired
    private VsftdpFile vsftdpFile;
    @Reference
    private ItemService itemService;
    @Reference
    private ItemDescService itemDescService;
    @Reference
    private ItemCatService itemCatService;
    @Autowired
    private JedisDao jedisDao;

    @Override
    public void onMessage(Message message) {
        try {
            //同步solr中的数据
            TextMessage textMessage = (TextMessage) message;
            String id = textMessage.getText();
            Map<String, String> map = new HashMap<>();
            TbItem item = itemService.getItemById(Long.valueOf(id));
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
            //同步redis中的数据
            jedisDao.add(vsftdpFile.getItemKey() + id, JsonUtils.objectToJson(item));
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}