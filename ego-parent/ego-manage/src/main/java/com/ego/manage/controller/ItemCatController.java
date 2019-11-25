/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ItemCatController
 * Author:   sjx
 * Date:     2019/11/8 11:41
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.manage.controller;

import com.ego.manage.service.ItemCatConsumerService;
import com.ego.vo.EgoResult;
import com.ego.vo.EgoTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/8
 * @since 1.0.0
 */
@Controller
public class ItemCatController {
    @Autowired
    private ItemCatConsumerService itemCatConsumerService;

    //查询指定状态下的所有正常状态的商品分类
    @RequestMapping("/item/cat/list")
    @ResponseBody
    List<EgoTree> getAllMenus(@RequestParam(defaultValue = "0") Long id) {
        return itemCatConsumerService.getAllMenus(id);
    }

}