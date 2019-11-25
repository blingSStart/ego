/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: UserService
 * Author:   sjx
 * Date:     2019/11/20 13:00
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.service;

import com.ego.pojo.TbUser;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/20
 * @since 1.0.0
 */
public interface UserService {

    //登录校验
    TbUser checkLoginService(TbUser user);

    //校验username，phone，email
    TbUser checkRegisterService(TbUser param);

    //注册用户
    int userRegisterService(TbUser user);
}