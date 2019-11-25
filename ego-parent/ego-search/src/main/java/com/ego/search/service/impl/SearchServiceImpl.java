/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: SearchServiceImpl
 * Author:   sjx
 * Date:     2019/11/15 17:03
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.search.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.pojo.TbItem;
import com.ego.search.dao.SolrDao;
import com.ego.search.service.SearchService;
import com.ego.service.ItemCatService;
import com.ego.service.ItemDescService;
import com.ego.service.ItemService;
import com.ego.vo.EgoResult;
import com.ego.vo.SolrItem;
import com.ego.vo.VsftdpFile;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/15
 * @since 1.0.0
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Reference
    private ItemService itemService;
    @Reference
    private ItemDescService itemDescService;
    @Reference
    private ItemCatService itemCatService;
    @Autowired
    private VsftdpFile vsftdpFile;
    @Autowired
    private SolrDao solrDao;
    @Autowired
    private CloudSolrServer cloudSolrServer;

    //初始化数据库
    @Override
    public void initSolr() {
        List<TbItem> itemList = itemService.getAllItemsSearch();
        try {
            for (TbItem tbItem : itemList) {
                SolrInputDocument document = new SolrInputDocument();
                document.setField("id", tbItem.getId());
                document.setField("item_title", tbItem.getTitle());
                document.setField("item_sell_point", tbItem.getSellPoint());
                document.setField("item_price", tbItem.getPrice());
                document.setField("item_image", tbItem.getImage());
                document.setField("item_category_name", itemCatService.getItemCatByCatId(tbItem.getCid()).getName());
                document.setField("item_desc", itemDescService.getDescByItemIDSearch(tbItem.getId()).getItemDesc());
                solrDao.addSolr(document);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //使用solr检索查询

    @Override
    public Map<String, Object> selectSolrItem(String q, int page) {
        Map<String, Object> res = new HashMap<>();
        try {
            List<SolrItem> solrItems = new ArrayList<>();
            int pageSize = vsftdpFile.getPageSize();
            //设置搜索字段
            SolrQuery query = new SolrQuery("item_keywords:" + q);
            query.setStart((page - 1) * pageSize);
            query.setRows(pageSize);
            query.setHighlight(true);
            query.addHighlightField("item_title item_sell_point");
            query.setHighlightSimplePre("<span style='color:light red'>");
            query.setHighlightSimplePost("</span>");
            QueryResponse queryResponse = cloudSolrServer.query(query);
            SolrDocumentList documentList = queryResponse.getResults();
            Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
            for (SolrDocument doc : documentList) {
                //将数据从solr云中取出放入solrItem对象中
                SolrItem solrItem = new SolrItem();
                //添加id
                solrItem.setId(Long.parseLong(doc.getFieldValue("id").toString()));
                //添加price
                solrItem.setPrice(Long.valueOf(doc.getFieldValue("item_price").toString()));
                Map<String, List<String>> map = highlighting.get("id");
                if (map != null && map.size() > 0) {
                    //添加title
                    List<String> titleList = map.get("item_title");
                    if (titleList != null && titleList.size() > 0) {
                        //如果高亮结果中有item_title字段
                        solrItem.setTitle(titleList.get(0));
                    } else {
                        //如果没有
                        solrItem.setTitle(doc.getFieldValue("item_title").toString());
                    }
                    //添加sell_point
                    List<String> pointList = map.get("item_sell_point");
                    if (pointList != null && pointList.size() > 0) {
                        solrItem.setSellPoint(pointList.get(0));
                    } else {
                        solrItem.setSellPoint(doc.getFieldValue("item_sell_point").toString());
                    }
                } else {
                    solrItem.setTitle(doc.getFieldValue("item_title").toString());
                    solrItem.setSellPoint(doc.getFieldValue("item_sell_point").toString());
                }
                //添加image
                String item_image = doc.getFieldValue("item_image").toString();
                solrItem.setImages((item_image != null && !"".equalsIgnoreCase(item_image)) ? item_image.split(",") : new String[1]);
                solrItems.add(solrItem);
            }
            res.put("itemList", solrItems);
            long numFound = documentList.getNumFound();
            long total = numFound / pageSize;
            res.put("totalPages", numFound % pageSize == 0 ? total : total + 1);
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return res;
    }


    //新增
    @Override
    public EgoResult addSolr(Map<String, String> map) {
        EgoResult res = new EgoResult();
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id", map.get("id"));
        document.setField("item_title", map.get("item_title"));
        document.setField("item_sell_point", map.get("item_sell_point"));
        document.setField("item_price", map.get("item_price"));
        document.setField("item_image", map.get("item_image"));
        document.setField("item_category_name", map.get("item_category_name"));
        document.setField("item_desc", map.get("item_desc"));
        int status = solrDao.addSolr(document);
        if (status == 0) {
            res.setStatus(200);
        }
        return res;
    }


    //删除
    @Override
    public EgoResult delSolr(Map<String, String> map) {
        EgoResult res = new EgoResult();
        int status = solrDao.delSolr(map.get("id"));
        if (status == 0) {
            res.setStatus(200);
        }
        return res;
    }


    //删除所有

    @Override
    public EgoResult delAll() {
        int status = solrDao.delAllSolr();
        EgoResult result = new EgoResult();
        if (status == 0) {
            result.setStatus(200);
        }
        return result;
    }
}