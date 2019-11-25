package com.ego.service;

import com.ego.pojo.TbItemDesc;

import java.util.List;

public interface ItemDescService {


    //添加商品描述
    int addItemDesc(TbItemDesc itemDesc);

    //根据商品id查询商品描述信息
    TbItemDesc getDescByItemIDSearch(Long itemId);
}
