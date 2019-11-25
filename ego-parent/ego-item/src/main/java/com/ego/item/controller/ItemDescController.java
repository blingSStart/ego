/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ItemDescController
 * Author:   sjx
 * Date:     2019/11/18 20:30
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.item.controller;

import com.ego.item.service.ItemDescIService;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParam;
import com.ego.pojo.TbItemParamItem;
import com.ego.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/18
 * @since 1.0.0
 */
@Controller
public class ItemDescController {
    @Autowired
    private ItemDescIService itemDescIService;

    //显示商品介绍
    @RequestMapping(value = "/item/desc/{itemid}.html", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String showDesc(@PathVariable Long itemid, Model model) {
        TbItemDesc res = itemDescIService.showDesc(itemid);
        model.addAttribute("itemDesc", res);
        return res.getItemDesc();
    }

    //    显示商品规格信息
    @RequestMapping(value = "/item/param/{itemid}.html", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String showParam(@PathVariable Long itemid, Model model) {
        return itemDescIService.showParam(itemid);
    }
}