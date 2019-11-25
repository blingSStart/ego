/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: CartController
 * Author:   sjx
 * Date:     2019/11/20 19:21
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.cart.controller;

import com.ego.vo.CartItem;
import com.ego.cart.service.CartService;
import com.ego.vo.EgoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/20
 * @since 1.0.0
 */
@Controller
public class CartController {
    @Autowired
    private CartService cartService;


    //显示购物车添加成功页
    @RequestMapping("/cart/add/{itemId}.html")
    public String showCart(@PathVariable Long itemId, int num, HttpServletRequest req) {
        cartService.addCart(itemId, num, req);
        return "/WEB-INF/jsp/cartSuccess.jsp";
    }

    //添加购物车显示结算详情页
    @RequestMapping("/cart/cart.html")
    public String showCartDetail(Model model, HttpServletRequest req) {
        List<CartItem> carts = cartService.showCart(req);
        model.addAttribute("cartList", carts);
        return "/WEB-INF/jsp/cart.jsp";
    }

    //修改购物车中商品数量
    @RequestMapping("/cart/update/num/{itemid}/{num}.action")
    @ResponseBody
    public EgoResult updateNum(@PathVariable Long itemid, @PathVariable int num, HttpServletRequest req) {
        return cartService.updateNum(itemid, num, req);
    }

    //修改购物车中商品数量
    @RequestMapping("/service/cart/update/num/{itemid}/{num}")
    @ResponseBody
    public EgoResult updateNum2(@PathVariable Long itemid, @PathVariable int num, HttpServletRequest req) {
        return cartService.updateNum(itemid, num, req);
    }

    //删除购物车商品
    @RequestMapping("/cart/delete/{itemid}.action")
    @ResponseBody
    public EgoResult delCart(@PathVariable Long itemid, HttpServletRequest req) {
        return cartService.delCart(itemid, req);
    }
}