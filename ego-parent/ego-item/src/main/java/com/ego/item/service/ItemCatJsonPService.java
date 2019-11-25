package com.ego.item.service;

import com.ego.vo.MenuCat;

public interface ItemCatJsonPService {

    //进行jsonp数据封装返回给portal
    MenuCat transferJsonP(Long pid);
}
