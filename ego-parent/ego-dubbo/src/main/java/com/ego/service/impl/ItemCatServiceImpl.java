/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ItemCatServiceImpl
 * Author:   sjx
 * Date:     2019/11/8 13:36
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.service.impl;

import com.ego.mapper.TbItemCatMapper;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemCatExample;
import com.ego.service.ItemCatService;
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
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private TbItemCatMapper itemCatMapper;

    //查询指定状态下的所有正常状态的商品分类
    @Override
    public List<TbItemCat> getAllItemCats(Long pid) {
        TbItemCatExample ex = new TbItemCatExample();
        //排序
        ex.setOrderByClause("sort_order asc");
        //提交构造器
        ex.createCriteria().andParentIdEqualTo(pid).andStatusEqualTo(1);
        return itemCatMapper.selectByExample(ex);
    }

    //根据catid查询cat
    @Override
    public TbItemCat getItemCatByCatId(Long itemCatId) {
        return itemCatMapper.selectByPrimaryKey(itemCatId);
    }

    //查询指定状态下的所有正常状态的商品分类返回给item进行jsonp数据封装返回给portal
    @Override
    public List<TbItemCat> getAllItemCatsToItem(Long pid) {
        TbItemCatExample ex = new TbItemCatExample();
        ex.setOrderByClause("sort_order asc");
        ex.createCriteria().andParentIdEqualTo(pid).andStatusEqualTo(1);
        List<TbItemCat> catList = itemCatMapper.selectByExample(ex);
        for (TbItemCat tbItemCat : catList) {
            if (tbItemCat.getIsParent()) {
                tbItemCat.setCatList(getAllItemCatsToItem(tbItemCat.getId()));
            }
        }
        return catList;
    }
}