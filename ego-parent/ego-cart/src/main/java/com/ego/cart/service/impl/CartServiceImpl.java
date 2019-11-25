/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: CartServiceImpl
 * Author:   sjx
 * Date:     2019/11/21 10:48
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.pojo.TbItem;
import com.ego.service.ItemService;
import com.ego.vo.CartItem;
import com.ego.cart.service.CartService;
import com.ego.dao.JedisDao;
import com.ego.pojo.TbUser;
import com.ego.utils.CookieUtils;
import com.ego.utils.JsonUtils;
import com.ego.vo.EgoResult;
import com.ego.vo.ItemChild;
import com.ego.vo.VsftdpFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/21
 * @since 1.0.0
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private JedisDao jedisDao;
    @Autowired
    private VsftdpFile vsftdpFile;
    @Reference
    private ItemService itemService;

    //添加购物车
    @Override
    public void addCart(Long itemId, int num, HttpServletRequest req) {
        //判断是否为第一次添加购物车
        //获取user中的id
        String cookieVal = CookieUtils.getCookieValue(req, "TT_TOKEN");
        String userJson = jedisDao.get(cookieVal);
        TbUser user = JsonUtils.jsonToPojo(userJson, TbUser.class);
        String cartKey = vsftdpFile.getCartKey() + user.getId();
        //根据cartkey获取cart
        String cartItem = jedisDao.get(cartKey);
        if (cartItem != null && !"".equalsIgnoreCase(cartItem)) {
            //不为空判断是否有相同商品
            List<CartItem> carts = JsonUtils.jsonToList(cartItem, CartItem.class);
            String itemJson = jedisDao.get(vsftdpFile.getItemKey() + itemId);
            if (itemJson != null && !"".equalsIgnoreCase(itemJson)) {
                ItemChild itemChild = JsonUtils.jsonToPojo(itemJson, ItemChild.class);
                int index = -1;
                for (int i = 0; i < carts.size(); i++) {
                    if ((long) carts.get(i).getId() == (long) itemId) {
                        index = i;
                    }
                }
                if (index == -1) {
                    //没有，直接添加到list中
                    CartItem cart = new CartItem();
                    cart.setNum(num);
                    cart.setId(itemChild.getId());
                    cart.setImages(itemChild.getImages());
                    cart.setPrice(itemChild.getPrice());
                    cart.setTitle(itemChild.getTitle());
                    carts.add(cart);
                } else {
                    //有，就修改数量
                    carts.get(index).setNum(carts.get(index).getNum() + num);
                }
                jedisDao.add(cartKey, JsonUtils.objectToJson(carts));
            }
        } else {
            //是直接添加
            String itemJson = jedisDao.get(vsftdpFile.getItemKey() + itemId);
            if (itemJson != null && !"".equalsIgnoreCase(itemJson)) {
                List<CartItem> carts = new ArrayList<>();
                ItemChild itemChild = JsonUtils.jsonToPojo(itemJson, ItemChild.class);
                CartItem cart = new CartItem();
                cart.setNum(num);
                cart.setId(itemChild.getId());
                cart.setImages(itemChild.getImages());
                cart.setPrice(itemChild.getPrice());
                cart.setTitle(itemChild.getTitle());
                carts.add(cart);
                jedisDao.add(cartKey, JsonUtils.objectToJson(carts));
            }
        }
    }

    //显示购物车详情
    @Override
    public List<CartItem> showCart(HttpServletRequest req) {
        String cookieVal = CookieUtils.getCookieValue(req, "TT_TOKEN");
        String userJson = jedisDao.get(cookieVal);
        TbUser user = JsonUtils.jsonToPojo(userJson, TbUser.class);
        String cartJson = jedisDao.get(vsftdpFile.getCartKey() + user.getId());
        return JsonUtils.jsonToList(cartJson, CartItem.class);
    }

    //修改购物车中商品数量

    @Override
    public EgoResult updateNum(Long itemid, int num, HttpServletRequest req) {
        EgoResult result = new EgoResult();
        String cookieVal = CookieUtils.getCookieValue(req, "TT_TOKEN");
        String userJson = jedisDao.get(cookieVal);
        if (userJson != null && !"".equalsIgnoreCase(userJson)) {
            TbUser user = JsonUtils.jsonToPojo(userJson, TbUser.class);
            String cartJson = jedisDao.get(vsftdpFile.getCartKey() + user.getId());
            if (cartJson != null && !"".equalsIgnoreCase(cartJson)) {
                List<CartItem> carts = JsonUtils.jsonToList(cartJson, CartItem.class);
                int index = -1;
                for (int i = 0; i < carts.size(); i++) {
                    if (((long) itemid == (long) carts.get(i).getId())) {
                        index = i;
                    }
                }
                if (index != -1) {
                    TbItem item = itemService.getItemById(itemid);
                    Integer itemNum = item.getNum();
                    if (itemNum >= num) {
                        //商品库存大于用户想要买的数量
                        carts.get(index).setNum(num);
                        String res = jedisDao.add(vsftdpFile.getCartKey() + user.getId(), JsonUtils.objectToJson(carts));
                        if ("OK".equalsIgnoreCase(res)) {
                            result.setStatus(200);
                        }
                    } else {
                        //商品库存不足
                        result.setStatus(300);
                    }
                }
            }
        }
        return result;
    }

    //删除
    @Override
    public EgoResult delCart(Long itemid, HttpServletRequest req) {
        EgoResult result = new EgoResult();
        String cookieVal = CookieUtils.getCookieValue(req, "TT_TOKEN");
        String userJson = jedisDao.get(cookieVal);
        if (userJson != null && !"".equalsIgnoreCase(userJson)) {
            TbUser user = JsonUtils.jsonToPojo(userJson, TbUser.class);
            String cartJson = jedisDao.get(vsftdpFile.getCartKey() + user.getId());
            if (cartJson != null && !"".equalsIgnoreCase(cartJson)) {
                List<CartItem> carts = JsonUtils.jsonToList(cartJson, CartItem.class);
                int index = -1;
                for (int i = 0; i < carts.size(); i++) {
                    if (((long) itemid == (long) carts.get(i).getId())) {
                        index = i;
                    }
                }
                if (index != -1) {
                    carts.remove(index);
                    for (CartItem cart : carts) {
                        System.out.println(cart.getTitle());
                    }
                }
                String res = jedisDao.add(vsftdpFile.getCartKey() + user.getId(), JsonUtils.objectToJson(carts));
                if ("OK".equalsIgnoreCase(res)) {
                    result.setStatus(200);
                }
            }
        }
        return result;
    }
}