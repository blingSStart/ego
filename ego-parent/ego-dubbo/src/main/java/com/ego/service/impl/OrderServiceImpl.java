/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: OrderServiceImpl
 * Author:   sjx
 * Date:     2019/11/22 19:39
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.service.impl;

import com.ego.mapper.TbOrderMapper;
import com.ego.pojo.TbOrder;
import com.ego.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/22
 * @since 1.0.0
 */
public class OrderServiceImpl implements OrderService {
    @Autowired
    private TbOrderMapper tbOrderMapper;

    //添加订单表
    @Override
    public int insertOrder(TbOrder order) {
        return tbOrderMapper.insertSelective(order);
    }
}