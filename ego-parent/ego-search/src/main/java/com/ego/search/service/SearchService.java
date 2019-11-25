package com.ego.search.service;

import com.ego.vo.EgoResult;

import java.util.Map;

public interface SearchService {
    //初始化数据库
    public void initSolr();

    //使用solr检索查询
    Map<String, Object> selectSolrItem(String q, int page);

    //新增
    EgoResult addSolr(Map<String, String> map);

    //删除
    EgoResult delSolr(Map<String, String> map);

    //删除所有
    EgoResult delAll();

}
