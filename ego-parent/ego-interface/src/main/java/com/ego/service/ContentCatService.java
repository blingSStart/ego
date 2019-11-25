package com.ego.service;

import com.ego.pojo.TbContentCategory;

import java.util.List;

public interface ContentCatService {
    //内容分类管理-显示所有广告信息
    List<TbContentCategory> showAllCategoryService(Long pid);

    //模糊查询
    List<TbContentCategory> selectContentCat(TbContentCategory cat);

    //新增
    int addContentCat(TbContentCategory category);

    //删除-修改
    int updateContentCat(TbContentCategory catParent);


}
