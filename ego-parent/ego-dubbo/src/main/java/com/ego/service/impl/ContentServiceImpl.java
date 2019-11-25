/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ContentServiceImpl
 * Author:   sjx
 * Date:     2019/11/13 9:52
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.service.impl;

import com.ego.mapper.TbContentMapper;
import com.ego.pojo.TbContent;
import com.ego.pojo.TbContentExample;
import com.ego.service.ContentService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/13
 * @since 1.0.0
 */
public class ContentServiceImpl implements ContentService {
    @Autowired
    private TbContentMapper tbContentMapper;

    //    查询记录数
    @Override
    public int getCountContentService(Long categoryId) {
        TbContentExample ex = new TbContentExample();
        if (categoryId != 0) {
            ex.createCriteria().andCategoryIdEqualTo(categoryId);
        }
        return tbContentMapper.countByExample(ex);
    }

    //    查询所有的内容信息
    @Override
    public List<TbContent> getAllContentService(Long categoryId, int page, int rows) {
        PageHelper.startPage(page, rows);
        TbContentExample ex = new TbContentExample();
        if (categoryId != 0) {
            ex.createCriteria().andCategoryIdEqualTo(categoryId);
        }
        return tbContentMapper.selectByExampleWithBLOBs(ex);
    }

    //新增内容
    @Override
    public int addContentService(TbContent content) {
        return tbContentMapper.insertSelective(content);
    }

    //修改内容
    @Override
    public int updateContentService(TbContent content) {
        return tbContentMapper.updateByPrimaryKeySelective(content);
    }

    //删除内容
    @Override
    public int deleteContentService(Long id) {
        return tbContentMapper.deleteByPrimaryKey(id);
    }

    //提供catid查询所有的content

    @Override
    public List<TbContent> getAllContentByCatId(Long catId) {
        TbContentExample tbContentExample = new TbContentExample();
        tbContentExample.createCriteria().andCategoryIdEqualTo(catId);
        return tbContentMapper.selectByExampleWithBLOBs(new TbContentExample());
    }
}