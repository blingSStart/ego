package com.ego.order.service;


import com.ego.vo.CartItem;
import com.ego.vo.MyOrder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface OrderCService {
    //通过itemid对比查询cart
    List<CartItem> getCartsById(List<Long> id, HttpServletRequest req);

    //添加订单
    Map<String, Object> addOrder(MyOrder myOrder, HttpServletRequest req);
}
