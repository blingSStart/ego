package com.ego.service;

import com.ego.pojo.TbItemCat;

import java.util.List;

public interface ItemCatService {

    //查询指定状态下的所有正常状态的商品分类
    List<TbItemCat> getAllItemCats(Long pid);

    //根据catid查询cat
    TbItemCat getItemCatByCatId(Long itemCatId);

    //查询指定状态下的所有正常状态的商品分类返回给item进行jsonp数据封装返回给portal
    List<TbItemCat> getAllItemCatsToItem(Long pid);
}
