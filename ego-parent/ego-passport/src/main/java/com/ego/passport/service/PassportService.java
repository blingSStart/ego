package com.ego.passport.service;

import com.ego.pojo.TbUser;
import com.ego.vo.EgoResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface PassportService {

    //登录校验
    EgoResult checkLogin(TbUser user, HttpServletRequest req, HttpServletResponse resp);

    //通过token查询信息
    Object getUserByToken(String callback, String token);

    //校验username
    Object checkRegister(TbUser param, String callback);

    //注册实现
    EgoResult userRegister(TbUser user);

    //安全退出
    Object logout(String token, String callback, HttpServletRequest req, HttpServletResponse resp);

}
