/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: PageController
 * Author:   sjx
 * Date:     2019/11/7 18:49
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/7
 * @since 1.0.0
 */
@Controller
public class PageController {

    //    显示首页
    @RequestMapping("/")
    public String showIndex() {
        return "/WEB-INF/jsp/index.jsp";
    }

    //    显示其他页面
    @RequestMapping("/{page}")
    public String showOthers(@PathVariable String page) {
        return "/WEB-INF/jsp/" + page + ".jsp";
    }
}