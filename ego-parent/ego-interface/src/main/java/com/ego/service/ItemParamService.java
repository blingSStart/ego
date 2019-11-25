package com.ego.service;

import com.ego.pojo.TbItemParam;

import java.util.Map;

public interface ItemParamService {

    //分页显示商品规格信息
    Map<String,Object> getAllItemParamsService(int page, int rows);

    //新增规格参数-根据商品分类id查询商品规格参数信息
    TbItemParam getItemParamByItemCatIdService(Long catId);

    //新增规格参数
    int addItemParamService(TbItemParam param);

    //删除商品规格参数
    int deleteItemParamByIdService(Long id);
}
