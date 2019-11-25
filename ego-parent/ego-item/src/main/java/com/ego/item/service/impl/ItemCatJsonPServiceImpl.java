/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ItemCatJsonPServiceImpl
 * Author:   sjx
 * Date:     2019/11/12 10:38
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.item.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.item.service.ItemCatJsonPService;
import com.ego.pojo.TbItemCat;
import com.ego.service.ItemCatService;
import com.ego.vo.MenuCat;
import com.ego.vo.MenuCatChild;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/12
 * @since 1.0.0
 */
@Service
public class ItemCatJsonPServiceImpl implements ItemCatJsonPService {

    @Reference
    private ItemCatService itemCatService;

    @Override
    public MenuCat transferJsonP(Long pid) {
        MenuCat cat = new MenuCat();
        cat.setData(packageJsonP(itemCatService.getAllItemCatsToItem(pid)));
        return cat;
    }

    private List<Object> packageJsonP(List<TbItemCat> catList) {
        List<Object> list = new ArrayList<>();
        for (TbItemCat tbItemCat : catList) {
            //是父节点目录
            if (tbItemCat.getIsParent()) {
                MenuCatChild child = new MenuCatChild();
                child.setU("/products/" + tbItemCat.getId() + ".html");
                child.setN("<a href='/products/" + tbItemCat.getId() + ".html'>" + tbItemCat.getName() + "</a>");
                child.setI(packageJsonP(tbItemCat.getCatList()));
                list.add(child);
            } else {
                //不是父节点目录
                list.add("/products/" + tbItemCat.getId() + ".html|" + tbItemCat.getName());
            }
        }
        return list;
    }
}