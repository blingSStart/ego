/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ItemDescServiceImpl
 * Author:   sjx
 * Date:     2019/11/8 17:25
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.service.impl;

import com.ego.mapper.TbItemDescMapper;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemDescExample;
import com.ego.service.ItemDescService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/8
 * @since 1.0.0
 */
public class ItemDescServiceImpl implements ItemDescService {
    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    //显示添加
    @Override
    public int addItemDesc(TbItemDesc itemDesc) {
        return tbItemDescMapper.insertSelective(itemDesc);
    }

    //查询

    @Override
    public TbItemDesc getDescByItemIDSearch(Long itemId) {
        TbItemDescExample ex = new TbItemDescExample();
        ex.createCriteria().andItemIdEqualTo(itemId);
        List<TbItemDesc> list = tbItemDescMapper.selectByExampleWithBLOBs(ex);
        if (list != null) {
            return list.get(0);
        }
        return null;
    }
}