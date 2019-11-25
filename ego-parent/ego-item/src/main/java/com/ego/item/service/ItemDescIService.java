package com.ego.item.service;

import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;

public interface ItemDescIService {

    //显示商品介绍
    TbItemDesc showDesc(Long itemid);

    //显示商品规格参数
    String showParam(Long itemid);

}
