/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ItemController
 * Author:   sjx
 * Date:     2019/11/7 18:49
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.manage.controller;

import com.ego.manage.service.ItemConsumerService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.vo.EgoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
@Controller
public class ItemController {

    @Autowired
    private ItemConsumerService itemConsumerService;

    //分页显示所有的商品
    @RequestMapping("/item/list")
    @ResponseBody
    public Map<String, Object> getAllItems(int page, int rows) {
        return itemConsumerService.getAllItems(page, rows);
    }

    //修改商品状态
    @RequestMapping("/rest/item/{status}")
    @ResponseBody
    EgoResult updateItems(@RequestParam List<Long> ids, @PathVariable String status) {
        if ("instock".equalsIgnoreCase(status)) {
            return itemConsumerService.updateItems(ids, (byte) 2);
        } else if ("reshelf".equalsIgnoreCase(status)) {
            return itemConsumerService.updateItems(ids, (byte) 1);
        } else {
            return itemConsumerService.updateItems(ids, (byte) 3);
        }
    }

    //文件图片
    @RequestMapping("/pic/upload")
    @ResponseBody
    Map<String, Object> imgUpload(MultipartFile uploadFile) {
        return itemConsumerService.imgUpload(uploadFile);
    }

    //添加商品(包括商品描述)
    @RequestMapping("/item/save")
    @ResponseBody
    EgoResult addItemADesc(TbItem item, String desc, String itemParams) {
        return itemConsumerService.addItemADesc(item, desc, itemParams);
    }
}