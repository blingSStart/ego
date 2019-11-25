package com.ego.passport.controller; /**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: com.ego.passport.controller.UserController
 * Author:   sjx
 * Date:     2019/11/20 11:21
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

import com.ego.passport.service.PassportService;
import com.ego.pojo.TbUser;
import com.ego.vo.EgoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/20
 * @since 1.0.0
 */
@Controller
public class PassportController {


    @Autowired
    private PassportService passportService;

    //显示登录页
    @RequestMapping(value = "/user/showLogin")
    public String showLogin(@RequestHeader("Referer") String referer, Model model) {
        if (referer != null) {
            model.addAttribute("redirect", referer);
        } else {
            model.addAttribute("redirect", "");
        }
        return "/WEB-INF/jsp/login.jsp";
    }

    //用户登录校验
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public EgoResult checkLogin(TbUser user, HttpServletRequest req, HttpServletResponse resp) {
        return passportService.checkLogin(user, req, resp);
    }

    //通过token查询信息-返回主界面用户信息
    @RequestMapping(value = "/user/token/{token}", method = RequestMethod.GET)
    @ResponseBody
    public Object getUserByToken(@PathVariable String token, String callback) {
        return passportService.getUserByToken(callback, token);
    }

    //显示注册页面
    @RequestMapping("/user/showRegister")
    public String showRegister() {
        return "/WEB-INF/jsp/register.jsp";
    }

    //注册页面校验
    @RequestMapping("/user/check/{param}/{key}")
    @ResponseBody
    public Object checkRegister(@PathVariable String param, @PathVariable String key, String callback) {
        //校验username
        TbUser user = new TbUser();
        if ("1".equalsIgnoreCase(key)) {
            //usernname
            user.setUsername(param);
            return passportService.checkRegister(user, callback);
        } else if ("2".equalsIgnoreCase(key)) {
            //phone
            user.setPhone(param);
            return passportService.checkRegister(user, callback);
        } else {
            //email
            user.setEmail(param);
            return passportService.checkRegister(user, callback);
        }
    }

    //注册实现
    @RequestMapping("/user/register")
    @ResponseBody
    public EgoResult userRegister(TbUser user) {
        return passportService.userRegister(user);
    }

    //安全退出
    @RequestMapping("/user/logout/{token}")
    @ResponseBody
    public Object logout(@PathVariable String token, String callback, HttpServletRequest req, HttpServletResponse resp) {
        return passportService.logout(token, callback, req, resp);
    }
}