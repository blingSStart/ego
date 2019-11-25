/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: PassportServiceImpl
 * Author:   sjx
 * Date:     2019/11/20 12:49
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.passport.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.dao.JedisDao;
import com.ego.passport.service.PassportService;
import com.ego.pojo.TbUser;
import com.ego.service.UserService;
import com.ego.utils.CookieUtils;
import com.ego.utils.JsonUtils;
import com.ego.vo.EgoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/20
 * @since 1.0.0
 */
@Service
public class PassportServiceImpl implements PassportService {
    @Reference
    private UserService userService;
    @Autowired
    private JedisDao jedisDao;

    //登录校验
    @Override
    public EgoResult checkLogin(TbUser user, HttpServletRequest req, HttpServletResponse resp) {
        EgoResult res = new EgoResult();
        TbUser userRes = userService.checkLoginService(user);
        if (userRes != null) {
            //设置cookie
            String cookieVal = UUID.randomUUID().toString();
            CookieUtils.setCookie(req, resp, "TT_TOKEN", cookieVal);
            //存入redis
            jedisDao.add(cookieVal, JsonUtils.objectToJson(userRes));
            res.setStatus(200);
            res.setMsg("OK");
            res.setData(cookieVal);
        }
        return res;
    }

    //通过token查询user
    @Override
    public Object getUserByToken(String callback, String token) {
        EgoResult res = new EgoResult();
        String jsonRes = jedisDao.get(token);
        if (jsonRes != null && !"".equalsIgnoreCase(jsonRes)) {
            TbUser user = JsonUtils.jsonToPojo(jsonRes, TbUser.class);
            TbUser userRes = new TbUser();
            userRes.setId(user.getId());
            userRes.setCreated(user.getCreated());
            userRes.setEmail(user.getEmail());
            userRes.setPhone(user.getPhone());
            userRes.setUpdated(user.getUpdated());
            userRes.setUsername(user.getUsername());
            res.setStatus(200);
            res.setMsg("OK");
            res.setData(userRes);
        }
        if (callback != null) {
            MappingJacksonValue value = new MappingJacksonValue(res);
            value.setJsonpFunction(callback);
            return value;
        } else {
            return res;
        }
    }

    //校验username
    @Override
    public Object checkRegister(TbUser param, String callback) {
        EgoResult res = new EgoResult();
        TbUser user = userService.checkRegisterService(param);
        if (user != null) {
            //用户名重复
            res.setMsg("OK");
            res.setStatus(200);
            res.setData(false);
            return res;
        } else {
            res.setMsg("OK");
            res.setStatus(200);
            res.setData(true);
            if (callback != null) {
                MappingJacksonValue value = new MappingJacksonValue(res);
                value.setJsonpFunction(callback);
                return value;
            } else {
                return res;
            }
        }
    }

    //注册实现

    @Override
    public EgoResult userRegister(TbUser user) {
        EgoResult res = new EgoResult();
        Date date = new Date();
        user.setCreated(date);
        user.setUpdated(date);
        int addRes = userService.userRegisterService(user);
        if (addRes > 0) {
            res.setStatus(200);
        }
        return res;
    }


    //安全退出

    @Override
    public Object logout(String token, String callback, HttpServletRequest req, HttpServletResponse resp) {
        EgoResult res = new EgoResult();
        //删除cookie
        CookieUtils.deleteCookie(req, resp, "TT_TOKEN");
        //删除redis
        Long redisRes = jedisDao.delete(token);
        if (redisRes > 0) {
            //删除成功
            res.setStatus(200);
            res.setMsg("OK");
            res.setData("");
        }
        if (callback != null) {
            MappingJacksonValue value = new MappingJacksonValue(res);
            value.setJsonpFunction(callback);
            return value;
        } else {
            return res;
        }
    }
}