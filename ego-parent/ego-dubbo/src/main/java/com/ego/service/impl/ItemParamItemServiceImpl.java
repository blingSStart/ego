/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ItemParamItemServiceImpl
 * Author:   sjx
 * Date:     2019/11/11 14:33
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.service.impl;

import com.ego.mapper.TbItemParamItemMapper;
import com.ego.pojo.TbItemParamItem;
import com.ego.pojo.TbItemParamItemExample;
import com.ego.service.ItemParamItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/11
 * @since 1.0.0
 */
public class ItemParamItemServiceImpl implements ItemParamItemService {
    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;

    //添加商品规格信息
    @Override
    public int addItemParamItemService(TbItemParamItem itemParamItem) {
        return tbItemParamItemMapper.insertSelective(itemParamItem);
    }

    //根据itemid查询
    @Override
    public TbItemParamItem getItemParamItemByItemid(Long itemid) {
        TbItemParamItemExample e = new TbItemParamItemExample();
        e.createCriteria().andItemIdEqualTo(itemid);
        List<TbItemParamItem> items = tbItemParamItemMapper.selectByExampleWithBLOBs(e);
        return items == null ? null : items.get(0);
    }
}