package com.ego.service;

import com.ego.pojo.TbContent;

import java.util.List;

public interface ContentService {

    //查询记录数
    int getCountContentService(Long categoryId);

    //查询所有内容
    List<TbContent> getAllContentService(Long categoryId, int page, int rows);

    //新增内容
    int addContentService(TbContent content);

    //修改内容
    int updateContentService(TbContent content);

    //删除内容
    int deleteContentService(Long id);

    //查询所以内容提供catid
    List<TbContent> getAllContentByCatId(Long catId);
}
