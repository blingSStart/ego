package com.ego.service;

import com.ego.pojo.TbItem;

import java.util.List;
import java.util.Map;

public interface ItemService {
    //分页显示所有商品
    Map<String, Object> getAllItems(int page, int rows);

    //修改商品的状态
    int updateItems(Long id, byte status);

    //添加商品
    int addItem(TbItem item);

    //查询所有商品
    List<TbItem> getAllItemsSearch();

    //根据id查询商品
    TbItem getItemById(Long id);
}
