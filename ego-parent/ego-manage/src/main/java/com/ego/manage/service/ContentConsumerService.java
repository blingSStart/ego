/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ContentConsumerService
 * Author:   sjx
 * Date:     2019/11/13 9:47
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.manage.service;

import com.ego.pojo.TbContent;
import com.ego.vo.EgoResult;

import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/13
 * @since 1.0.0
 */
public interface ContentConsumerService {

    //根据catid分页显示商品内容详情
    Map<String, Object> getAllContent(Long categoryId, int page, int rows);

    //新增内容
    EgoResult addContent(TbContent content);

    //修改内容
    EgoResult updateContent(TbContent content);

    //删除内容
    EgoResult deleteContent(List<Long> ids);
}