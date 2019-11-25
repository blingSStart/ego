/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ItemParamController
 * Author:   sjx
 * Date:     2019/11/11 9:41
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.manage.controller;

import com.ego.manage.service.ItemParamConsumerService;
import com.ego.pojo.TbItemParam;
import com.ego.vo.EgoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
@Controller
public class ItemParamController {

    @Autowired
    private ItemParamConsumerService itemParamConsumerService;

    //分页显示商品规格信息
    @RequestMapping("/item/param/list")
    @ResponseBody
    public Map<String, Object> getAllItemParams(int page, int rows) {
        return itemParamConsumerService.getAllItemParams(page, rows);
    }

    //新增规格参数-根据商品分类id查询商品规格参数信息
    @RequestMapping("/item/param/query/itemcatid/{catId}")
    @ResponseBody
    public EgoResult getItemParamByItemCatId(@PathVariable Long catId) {
        return itemParamConsumerService.getItemParamByItemCatId(catId);
    }

    //新增规格参数
    @RequestMapping("/item/param/save/{catId}")
    @ResponseBody
    public EgoResult addItemParam(@PathVariable Long catId, String paramData) {
        TbItemParam param = new TbItemParam();
        param.setItemCatId(catId);
        param.setParamData(paramData);
        return itemParamConsumerService.addItemParam(param);
    }

    //删除规格参数
    @RequestMapping("/item/param/delete")
    @ResponseBody
    public EgoResult deleteItemParamById(@RequestParam List<Long> ids) {
        return itemParamConsumerService.deleteItemParamById(ids);
    }
}