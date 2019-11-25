/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: OrderController
 * Author:   sjx
 * Date:     2019/11/21 20:01
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.order.controller;

import com.ego.order.service.OrderCService;
import com.ego.vo.CartItem;
import com.ego.vo.MyOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/21
 * @since 1.0.0
 */
@Controller
public class OrderController {
    @Autowired
    private OrderCService orderCService;

    //显示订单
    @RequestMapping("/order/order-cart.html")
    public String showOrder(@RequestParam List<Long> id, HttpServletRequest req, Model model) {
        //通过cartid查询cart
        List<CartItem> carts = orderCService.getCartsById(id, req);
        model.addAttribute("cartList", carts);
        return "/WEB-INF/jsp/order-cart.jsp";
    }

    //添加订单
    @RequestMapping("/order/create.html")
    public String addOrder(MyOrder myOrder, Model model, HttpServletRequest req) {
        Map<String, Object> res = orderCService.addOrder(myOrder, req);
        if (res != null) {
            model.addAttribute("orderId", res.get("orderId"));
            model.addAttribute("payment", res.get("payment"));
            model.addAttribute("date", res.get("date"));
            return "/WEB-INF/jsp/success.jsp";
        } else {
            model.addAttribute("message", "提交订单失败！");
            return "/WEB-INF/jsp/error/orderException.jsp";
        }
    }

    //显示个人订单
    @RequestMapping("/order/my-orders.html")
    public String showOrder() {
        return "/WEB-INF/jsp/my-orders.jsp";
    }
}