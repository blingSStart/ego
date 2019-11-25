/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ContentCategoryController
 * Author:   sjx
 * Date:     2019/11/12 14:55
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.manage.controller;

import com.ego.manage.service.ContentCategoryService;
import com.ego.pojo.TbContentCategory;
import com.ego.vo.EgoResult;
import com.ego.vo.EgoTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/12
 * @since 1.0.0
 */
@Controller
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    //内容分类管理-显示所有广告信息
    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EgoTree> showAllCategory(@RequestParam(defaultValue = "0") Long id) {
        return contentCategoryService.showAllCategory(id);
    }

    //内容分类管理-新增
    @RequestMapping("/content/category/create")
    @ResponseBody
    public EgoResult addContentCat(TbContentCategory cat) {
        return contentCategoryService.addContentCat(cat);
    }

    //内容分类管理-重命名
    @RequestMapping("/content/category/update")
    @ResponseBody
    public EgoResult updateContentCat(TbContentCategory cat) {
        return contentCategoryService.updateContentCat(cat);
    }

    //内容分类管理-删除-修改
    @RequestMapping("/content/category/delete")
    @ResponseBody
    public EgoResult delContentCat(TbContentCategory cat) {
        return contentCategoryService.delContent(cat);
    }

}