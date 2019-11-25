/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: CartInterceptor
 * Author:   sjx
 * Date:     2019/11/20 19:23
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.cart.interceptor;

import com.ego.dao.JedisDao;
import com.ego.utils.CookieUtils;
import com.ego.vo.VsftdpFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

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
public class CartInterceptor implements HandlerInterceptor {
    @Autowired
    private JedisDao jedisDao;
    @Autowired
    private VsftdpFile vsftdpFile;


    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object o) throws Exception {
        //获取cookieval
        String cookieVal = CookieUtils.getCookieValue(req, "TT_TOKEN");
        if (cookieVal != null && !"".equalsIgnoreCase(cookieVal)) {
            //从redis
            String jsonRes = jedisDao.get(cookieVal);
            if (jsonRes != null && !"".equalsIgnoreCase(jsonRes)) {
                //用户已经登录
                return true;
            }
        }
        //跳转登录页
        resp.sendRedirect(vsftdpFile.getPassportUrl() + "/user/showLogin");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}