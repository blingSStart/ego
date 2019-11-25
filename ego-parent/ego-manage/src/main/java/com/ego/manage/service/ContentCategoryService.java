/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ContentCategoryService
 * Author:   sjx
 * Date:     2019/11/12 15:00
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.manage.service;

import com.ego.pojo.TbContentCategory;
import com.ego.vo.EgoResult;
import com.ego.vo.EgoTree;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/12
 * @since 1.0.0
 */
public interface ContentCategoryService {
    //内容分类管理-显示所有广告信息
    List<EgoTree> showAllCategory(Long pid);

    //内容分类管理-新增
    EgoResult addContentCat(TbContentCategory cat);

    //内容分类管理-重命名
    EgoResult updateContentCat(TbContentCategory cat);


    //内容分类管理-删除-修改
    EgoResult delContent(TbContentCategory cat);
}