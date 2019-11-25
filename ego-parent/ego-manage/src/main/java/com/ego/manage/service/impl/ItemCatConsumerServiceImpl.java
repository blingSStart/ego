/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: IntemCatConsumerServiceImpl
 * Author:   sjx
 * Date:     2019/11/8 11:50
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.manage.service.ItemCatConsumerService;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemParam;
import com.ego.service.ItemCatService;
import com.ego.service.ItemParamService;
import com.ego.vo.EgoResult;
import com.ego.vo.EgoTree;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/8
 * @since 1.0.0
 */
@Service
public class ItemCatConsumerServiceImpl implements ItemCatConsumerService {
    @Reference
    private ItemCatService itemCatService;

    //查询指定状态下的所有正常状态的商品分类
    @Override
    public List<EgoTree> getAllMenus(Long pid) {
        List<TbItemCat> itemCats = itemCatService.getAllItemCats(pid);
        List<EgoTree> trees = new ArrayList<>();
        for (TbItemCat itemCat : itemCats) {
            EgoTree t = new EgoTree();
            t.setId(itemCat.getId());
            t.setText(itemCat.getName());
            t.setState(itemCat.getIsParent() ? "closed" : "open");
            trees.add(t);
        }
        return trees;
    }

}