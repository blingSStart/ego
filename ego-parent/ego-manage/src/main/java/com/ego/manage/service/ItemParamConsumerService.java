/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ItemParamService
 * Author:   sjx
 * Date:     2019/11/11 9:46
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.manage.service;

import com.ego.pojo.TbItemParam;
import com.ego.vo.EgoResult;

import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/11
 * @since 1.0.0
 */
public interface ItemParamConsumerService {

    //分页显示商品规格信息
    Map<String, Object> getAllItemParams(int page, int rows);

    //新增规格参数-根据商品分类id查询商品规格参数信息
    EgoResult getItemParamByItemCatId(Long catId);

    //新增规格参数
    EgoResult addItemParam(TbItemParam param);

    //删除商品规格参数
    EgoResult deleteItemParamById(List<Long> ids);
}