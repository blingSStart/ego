/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: IndexController
 * Author:   sjx
 * Date:     2019/11/12 11:30
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.portal.controller;

import com.ego.portal.service.PortalContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/12
 * @since 1.0.0
 */
@Controller
public class IndexController {
    @Autowired
    private PortalContentService portalContentService;

    @RequestMapping("/")
    public String showIndex(Model model) {
        model.addAttribute("ad1", portalContentService.showBigAdv());
        return "/WEB-INF/jsp/index.jsp";
    }
}