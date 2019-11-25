package com.ego.dao;

/**
 * @Author: Victor Wu
 * @Date: 2019/11/13 14:07
 * @Description:
 */
public interface JedisDao {

    //判断redis中是否有key对应的数据
    Boolean exists(String key);
    //新增
    String add(String key,String value);

    //删除
    Long delete(String key);

    //查询
    String get(String key);


}
