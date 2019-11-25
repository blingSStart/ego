package com.ego.cart.service;

import com.ego.vo.CartItem;
import com.ego.vo.EgoResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CartService {

    //添加购物车到redis
    void addCart(Long itemId, int num, HttpServletRequest req);


    //显示购物车详情
    List<CartItem> showCart(HttpServletRequest req);

    //修改购物车中商品数量
    EgoResult updateNum(Long itemid, int num, HttpServletRequest req);

    //删除购物车
    EgoResult delCart(Long itemid, HttpServletRequest req);

}
