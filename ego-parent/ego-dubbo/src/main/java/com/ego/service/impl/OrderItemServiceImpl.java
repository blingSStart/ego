/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: OrderItemServiceImpl
 * Author:   sjx
 * Date:     2019/11/22 19:41
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.service.impl;

import com.ego.mapper.TbOrderItemMapper;
import com.ego.pojo.TbOrderItem;
import com.ego.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/22
 * @since 1.0.0
 */
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    private TbOrderItemMapper tbOrderItemMapper;


    //添加订单商品表
    @Override
    public int insertOrderItem(TbOrderItem orderItem) {
        return tbOrderItemMapper.insertSelective(orderItem);
    }
}