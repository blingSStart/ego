/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ItemController
 * Author:   sjx
 * Date:     2019/11/18 19:49
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.item.controller;

import com.ego.item.service.ItemIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/18
 * @since 1.0.0
 */
@Controller
public class ItemController {
    @Autowired
    private ItemIService itemIService;

    @RequestMapping("/item/{id}.html")
    public String showIndex(@PathVariable Long id, Model model) {
        model.addAttribute("item", itemIService.getItemById(id));
        return "/WEB-INF/jsp/item.jsp";
    }
}