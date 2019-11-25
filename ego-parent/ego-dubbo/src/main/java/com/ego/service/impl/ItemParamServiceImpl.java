/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ItemParamServiceImpl
 * Author:   sjx
 * Date:     2019/11/11 9:51
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.service.impl;

import com.ego.mapper.TbItemParamMapper;
import com.ego.pojo.TbItemParam;
import com.ego.pojo.TbItemParamExample;
import com.ego.service.ItemParamService;
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
 * @create 2019/11/11
 * @since 1.0.0
 */
public class ItemParamServiceImpl implements ItemParamService {

    @Autowired
    private TbItemParamMapper tbItemParamMapper;

    //分页显示商品规格信息
    @Override
    public Map<String, Object> getAllItemParamsService(int page, int rows) {
        Map<String, Object> map = new HashMap<>();
        //开启分页助手
        PageHelper.startPage(page, rows);
        List<TbItemParam> params = tbItemParamMapper.selectByExampleWithBLOBs(new TbItemParamExample());
        PageInfo<TbItemParam> pageInfo = new PageInfo<>(params);
        map.put("total", pageInfo.getTotal());
        map.put("rows", pageInfo.getList());
        return map;
    }

    //新增规格参数-根据商品分类id查询商品规格参数信息
    @Override
    public TbItemParam getItemParamByItemCatIdService(Long catId) {
        TbItemParamExample ex = new TbItemParamExample();
        ex.createCriteria().andItemCatIdEqualTo(catId);
        List<TbItemParam> params = tbItemParamMapper.selectByExampleWithBLOBs(ex);
        if (params != null && params.size() > 0) {
            return params.get(0);
        }
        return null;
    }

    //新增规格参数
    @Override
    public int addItemParamService(TbItemParam param) {
        return tbItemParamMapper.insertSelective(param);
    }

    //删除规格参数

    @Override
    public int deleteItemParamByIdService(Long id) {
        return tbItemParamMapper.deleteByPrimaryKey(id);
    }
}