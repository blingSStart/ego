package com.ego.service;

import com.ego.pojo.TbItemParamItem;

public interface ItemParamItemService {
    //新增商品规格信息
    int addItemParamItemService(TbItemParamItem itemParamItem);

    //根据itemi查询规格详细信息
    TbItemParamItem getItemParamItemByItemid(Long itemid);
}
