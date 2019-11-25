/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ItemServiceImpl
 * Author:   sjx
 * Date:     2019/11/7 18:41
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.service.impl;

import com.ego.mapper.TbItemMapper;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemExample;
import com.ego.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/7
 * @since 1.0.0
 */
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper tbItemMapper;

    //分页显示所有商品
    @Override
    public Map<String, Object> getAllItems(int page, int rows) {
        Map<String, Object> map = new HashMap<>();
        //开启分页插件，设置分页
        PageHelper.startPage(page, rows);
        //查询所有数据
        List<TbItem> items = tbItemMapper.selectByExample(new TbItemExample());
        //将结果封装进pageInfo中
        PageInfo<TbItem> pageInfo = new PageInfo<>(items);
        map.put("total", pageInfo.getTotal());
        map.put("rows", pageInfo.getList());
        System.out.println(map.get("rows"));
        return map;
    }

    //修改商品状态
    @Override
    public int updateItems(Long id, byte status) {
        TbItem it = new TbItem();
        it.setId(id);
        it.setStatus(status);
        return tbItemMapper.updateByPrimaryKeySelective(it);
    }

    //添加商品
    @Override
    public int addItem(TbItem item) {
        return tbItemMapper.insertSelective(item);
    }

    //查询所有商品
    @Override
    public List<TbItem> getAllItemsSearch() {
        TbItemExample ex = new TbItemExample();
        ex.createCriteria().andStatusEqualTo((byte) 1);
        return tbItemMapper.selectByExample(ex);
    }

    //根据id查询商品
    @Override
    public TbItem getItemById(Long id) {
        return tbItemMapper.selectByPrimaryKey(id);
    }
}