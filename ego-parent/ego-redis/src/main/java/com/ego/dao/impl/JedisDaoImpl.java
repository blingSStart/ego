package com.ego.dao.impl;

import com.ego.dao.JedisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.JedisCluster;

/**
 * @Author: Victor Wu
 * @Date: 2019/11/13 14:10
 * @Description:
 */
@Repository
public class JedisDaoImpl implements JedisDao {
    @Autowired
    private JedisCluster jedisCluster;

    //判断redis中是否有key对应的数据
    @Override
    public Boolean exists(String key) {
        return jedisCluster.exists(key);
    }


    //新增
    @Override
    public String add(String key, String value) {

        return jedisCluster.set(key, value);
    }


    //删除
    @Override
    public Long delete(String key) {

        return jedisCluster.del(key);
    }


    //查询
    @Override
    public String get(String key) {
        return jedisCluster.get(key);
    }
}
