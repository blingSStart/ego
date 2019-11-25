/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: OrderServiceImpl
 * Author:   sjx
 * Date:     2019/11/21 20:13
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.order.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.dao.JedisDao;
import com.ego.order.service.OrderCService;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;
import com.ego.pojo.TbUser;
import com.ego.service.OrderItemService;
import com.ego.service.OrderService;
import com.ego.service.OrderShippingService;
import com.ego.utils.CookieUtils;
import com.ego.utils.IDUtils;
import com.ego.utils.JsonUtils;
import com.ego.vo.CartItem;
import com.ego.vo.MyOrder;
import com.ego.vo.VsftdpFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/21
 * @since 1.0.0
 */
@Service
public class OrderCServiceImpl implements OrderCService {
    @Autowired
    private JedisDao jedisDao;
    @Autowired
    private VsftdpFile vsftdpFile;
    @Reference
    private OrderService orderService;
    @Reference
    private OrderItemService orderItemService;
    @Reference
    private OrderShippingService orderShippingService;


    //通过itemid对比查询order -显示订单

    @Override
    public List<CartItem> getCartsById(List<Long> id, HttpServletRequest req) {
        List<CartItem> res = new ArrayList<>();
        String cookieVal = CookieUtils.getCookieValue(req, "TT_TOKEN");
        String userJson = jedisDao.get(cookieVal);
        if (userJson != null && !"".equalsIgnoreCase(userJson)) {
            TbUser user = JsonUtils.jsonToPojo(userJson, TbUser.class);
            String cartKey = vsftdpFile.getCartKey() + user.getId();
            String cartJson = jedisDao.get(cartKey);
            if (cartJson != null && !"".equalsIgnoreCase(cartJson)) {
                List<CartItem> carts = JsonUtils.jsonToList(cartJson, CartItem.class);
                for (int i = 0; i < carts.size(); i++) {
                    for (Long a : id) {

                        if ((long) a == (long) carts.get(i).getId()) {
                            res.add(carts.get(i));
                        }
                    }
                }
            }
        }
        return res;
    }

    //添加订单

    @Override
    public Map<String, Object> addOrder(MyOrder myOrder, HttpServletRequest req) {
        Map<String, Object> res = new HashMap<>();
        int index = 0;
        //添加订单表
        TbOrder order = new TbOrder();
        String orderId = IDUtils.genItemId() + "";
        Date date = new Date();
        order.setOrderId(orderId);
        order.setPayment(myOrder.getPayment());
        order.setPaymentType(myOrder.getPaymentType());
        order.setCreateTime(date);
        order.setUpdateTime(date);
        index += orderService.insertOrder(order);
        //添加订单发货表
        TbOrderShipping orderShipping = myOrder.getOrderShipping();
        orderShipping.setOrderId(orderId);
        orderShipping.setCreated(date);
        orderShipping.setUpdated(date);
        index += orderShippingService.insertOrderShipping(orderShipping);
        //添加订单商品表
        List<TbOrderItem> orderItems = myOrder.getOrderItems();
        for (TbOrderItem orderItem : orderItems) {
            orderItem.setId(IDUtils.genItemId() + "");
            orderItem.setOrderId(orderId);
            index += orderItemService.insertOrderItem(orderItem);
        }

        if (index == orderItems.size() + 2) {
            res.put("orderId", orderId);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_WEEK, 2);
            res.put("date", calendar.getTime());
            res.put("payment", order.getPayment());
            //删除购物车中的数据
            String cookieValue = CookieUtils.getCookieValue(req, "TT_TOKEN");
            if (cookieValue != null && !"".equalsIgnoreCase(cookieValue)) {
                String userJson = jedisDao.get(cookieValue);
                if (userJson != null && !"".equalsIgnoreCase(userJson)) {
                    TbUser user = JsonUtils.jsonToPojo(userJson, TbUser.class);
                    jedisDao.delete(vsftdpFile.getCartKey() + user.getId());
                }
            }
        }
        return res;
    }
}