/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ContentController
 * Author:   sjx
 * Date:     2019/11/13 9:44
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.manage.controller;

import com.ego.manage.service.ContentConsumerService;
import com.ego.pojo.TbContent;
import com.ego.vo.EgoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
 * @create 2019/11/13
 * @since 1.0.0
 */
@Controller
public class ContentController {
    @Autowired
    private ContentConsumerService contentConsumerService;

    //根据catid分页显示商品内容详情
    @RequestMapping("/content/query/list")
    @ResponseBody
    public Map<String, Object> getAllContent(Long categoryId, int page, int rows) {
        return contentConsumerService.getAllContent(categoryId, page, rows);
    }

    //新增内容
    @RequestMapping("/content/save")
    @ResponseBody
    public EgoResult addContent(TbContent content) {
        return contentConsumerService.addContent(content);
    }

    //编辑内容
    @RequestMapping("/rest/content/edit")
    @ResponseBody
    public EgoResult updateContent(TbContent content) {
        return contentConsumerService.updateContent(content);
    }

    //删除内容
    @RequestMapping("/content/delete")
    @ResponseBody
    public EgoResult deleteContent(@RequestParam List<Long> ids) {
        return contentConsumerService.deleteContent(ids);
    }

}