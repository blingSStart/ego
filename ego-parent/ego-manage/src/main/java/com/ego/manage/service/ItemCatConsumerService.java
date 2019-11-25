/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ItemCatConsumerService
 * Author:   sjx
 * Date:     2019/11/8 11:43
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.manage.service;

import com.ego.vo.EgoResult;
import com.ego.vo.EgoTree;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/8
 * @since 1.0.0
 */
public interface ItemCatConsumerService {
    //查询指定状态下的所有正常状态的商品分类
    List<EgoTree> getAllMenus(Long pid);

}