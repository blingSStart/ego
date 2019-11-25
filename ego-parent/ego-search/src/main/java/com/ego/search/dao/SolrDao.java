package com.ego.search.dao;

import org.apache.solr.common.SolrInputDocument;

public interface SolrDao {

    //添加
    int addSolr(SolrInputDocument document);

    //删除
    int delSolr(String id);

    //删除所有
    int delAllSolr();
}
