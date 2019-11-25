/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: UserServiceImpl
 * Author:   sjx
 * Date:     2019/11/20 13:00
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.service.impl;

import com.ego.mapper.TbUserMapper;
import com.ego.pojo.TbUser;
import com.ego.pojo.TbUserExample;
import com.ego.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/20
 * @since 1.0.0
 */
public class UserServiceImpl implements UserService {
    @Autowired
    private TbUserMapper tbUserMapper;

    //登录校验
    @Override
    public TbUser checkLoginService(TbUser user) {
        TbUserExample ex = new TbUserExample();
        ex.createCriteria().andUsernameEqualTo(user.getUsername()).andPasswordEqualTo(user.getPassword());
        return tbUserMapper.selectByExample(ex).get(0);
    }

    //校验username，phone，email
    @Override
    public TbUser checkRegisterService(TbUser param) {
        TbUserExample e = new TbUserExample();
        TbUserExample.Criteria cri = e.createCriteria();
        if (param.getUsername() != null) {
            cri.andUsernameEqualTo(param.getUsername());
        } else if (param.getPhone() != null) {
            cri.andPhoneEqualTo(param.getPhone());
        } else {
            cri.andEmailEqualTo(param.getEmail());
        }
        List<TbUser> users = tbUserMapper.selectByExample(e);
        if (users.isEmpty()) {
            return null;
        } else {
            return users.get(0);
        }
    }


    //注册用户

    @Override
    public int userRegisterService(TbUser user) {
        return tbUserMapper.insertSelective(user);
    }
}