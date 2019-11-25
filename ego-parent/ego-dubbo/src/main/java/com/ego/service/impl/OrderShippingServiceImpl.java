/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: OrderShippingServiceImpl
 * Author:   sjx
 * Date:     2019/11/22 19:40
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.service.impl;

import com.ego.mapper.TbOrderShippingMapper;
import com.ego.pojo.TbOrderShipping;
import com.ego.service.OrderShippingService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/22
 * @since 1.0.0
 */
public class OrderShippingServiceImpl implements OrderShippingService {
    @Autowired
    private TbOrderShippingMapper tbOrderShippingMapper;

    //添加订单发货表
    @Override
    public int insertOrderShipping(TbOrderShipping orderShipping) {
        return tbOrderShippingMapper.insertSelective(orderShipping);
    }
}