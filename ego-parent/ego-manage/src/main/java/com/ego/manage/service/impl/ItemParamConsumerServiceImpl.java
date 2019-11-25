/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ItemParamServiceImpl
 * Author:   sjx
 * Date:     2019/11/11 9:47
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.manage.pojo.TbItemParamChild;
import com.ego.manage.service.ItemParamConsumerService;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemParam;
import com.ego.service.ItemCatService;
import com.ego.service.ItemParamService;
import com.ego.vo.EgoResult;
import com.ego.vo.EgoTree;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
@Service
public class ItemParamConsumerServiceImpl implements ItemParamConsumerService {
    @Reference
    private ItemParamService itemParamService;
    @Reference
    private ItemCatService itemCatService;

    //分页显示商品规格信息
    @Override
    public Map<String, Object> getAllItemParams(int page, int rows) {
        Map<String, Object> map = itemParamService.getAllItemParamsService(page, rows);
        List<TbItemParam> params = (List<TbItemParam>) map.get("rows");
        List<TbItemParamChild> childList = new ArrayList<>();
        for (TbItemParam param : params) {
            TbItemParamChild child = new TbItemParamChild();
            child.setId(param.getId());
            child.setItemCatId(param.getItemCatId());
            child.setParamData(param.getParamData());
            child.setCreated(param.getCreated());
            child.setUpdated(param.getUpdated());
            child.setItemCatName(itemCatService.getItemCatByCatId(param.getItemCatId()).getName());
            childList.add(child);
        }
        map.put("rows", childList);
        return map;
    }

    //新增规格参数-根据商品分类id查询商品规格参数信息
    @Override
    public EgoResult getItemParamByItemCatId(Long catId) {
        TbItemParam param = itemParamService.getItemParamByItemCatIdService(catId);
        EgoResult result = new EgoResult();
        if (param != null) {
            result.setStatus(200);
            result.setData(param);
        }
        return result;
    }

    //新增规格参数
    @Override
    public EgoResult addItemParam(TbItemParam param) {
        Date date = new Date();
        EgoResult result = new EgoResult();
        param.setCreated(date);
        param.setUpdated(date);
        int res = itemParamService.addItemParamService(param);
        if (res > 0) {
            result.setStatus(200);
        }
        return result;
    }

    //删除规格参数
    @Override
    public EgoResult deleteItemParamById(List<Long> ids) {
        EgoResult result = new EgoResult();
        int res = 0;
        for (Long id : ids) {
            res += itemParamService.deleteItemParamByIdService(id);
        }
        if (res == ids.size()) {
            result.setStatus(200);
        }
        return result;
    }
}