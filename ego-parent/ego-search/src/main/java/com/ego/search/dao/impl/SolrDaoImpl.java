/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: SolrDaoImpl
 * Author:   sjx
 * Date:     2019/11/18 17:46
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.search.dao.impl;

import com.ego.search.dao.SolrDao;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/18
 * @since 1.0.0
 */
@Repository
public class SolrDaoImpl implements SolrDao {
    @Autowired
    private CloudSolrServer cloudSolrServer;

    //添加
    @Override
    public int addSolr(SolrInputDocument document) {
        int status = -1;
        try {
            UpdateResponse updRes = cloudSolrServer.add(document);
            status = updRes.getStatus();
            cloudSolrServer.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }

    //删除
    @Override
    public int delSolr(String id) {
        int status = -1;
        try {
            UpdateResponse updRes = cloudSolrServer.deleteById(id);
            status = updRes.getStatus();
            cloudSolrServer.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }


    //删除所有
    @Override
    public int delAllSolr() {
        int status = -1;
        try {
            UpdateResponse updRes = cloudSolrServer.deleteByQuery("*:*");
            status = updRes.getStatus();
            cloudSolrServer.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }
}