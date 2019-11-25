/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ItemCatController
 * Author:   sjx
 * Date:     2019/11/12 10:51
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.item.controller;

import com.ego.item.service.ItemCatJsonPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/12
 * @since 1.0.0
 */
@Controller
public class ItemCatController {

    @Autowired
    private ItemCatJsonPService itemCatJsonPService;

    @RequestMapping("/rest/itemcat/all")
    @ResponseBody
    public MappingJacksonValue showItemCatsJsonP(String callback) {
        MappingJacksonValue value = new MappingJacksonValue(itemCatJsonPService.transferJsonP((long) 0));
        value.setJsonpFunction(callback);
        return value;
    }
}