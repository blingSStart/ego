/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ContentCatServiceImpl
 * Author:   sjx
 * Date:     2019/11/12 15:07
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.service.impl;

import com.ego.mapper.TbContentCategoryMapper;
import com.ego.pojo.TbContentCategory;
import com.ego.pojo.TbContentCategoryExample;
import com.ego.service.ContentCatService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/12
 * @since 1.0.0
 */
public class ContentCatServiceImpl implements ContentCatService {

    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;

    //内容分类管理-显示所有广告信息
    @Override
    public List<TbContentCategory> showAllCategoryService(Long pid) {
        TbContentCategoryExample ex = new TbContentCategoryExample();
        ex.createCriteria().andStatusEqualTo(1).andParentIdEqualTo(pid);
        ex.setOrderByClause("sort_order asc");
        return tbContentCategoryMapper.selectByExample(ex);
    }

    //模糊查询
    @Override
    public List<TbContentCategory> selectContentCat(TbContentCategory cat) {
        TbContentCategoryExample ex = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria cri = ex.createCriteria();
        if (cat.getId() != null) {
            cri.andIdEqualTo(cat.getId());
        }
        if (cat.getParentId() != null) {
            cri.andParentIdEqualTo(cat.getParentId());
        }
        if (cat.getName() != null) {
            cri.andNameEqualTo(cat.getName());
        }
        if (cat.getStatus() != null) {
            cri.andStatusEqualTo(cat.getStatus());
        }
        if (cat.getSortOrder() != null) {
            cri.andSortOrderEqualTo(cat.getSortOrder());
        }
        if (cat.getIsParent() != null) {
            cri.andIsParentEqualTo(cat.getIsParent());
        }
        if (cat.getCreated() != null) {
            cri.andCreatedEqualTo(cat.getCreated());
        }
        if (cat.getUpdated() != null) {
            cri.andUpdatedEqualTo(cat.getUpdated());
        }
        List<TbContentCategory> catList = tbContentCategoryMapper.selectByExample(ex);
        return catList;
    }

    //新增
    @Override
    public int addContentCat(TbContentCategory category) {
        return tbContentCategoryMapper.insertSelective(category);
    }

    //修改-删除
    @Override
    public int updateContentCat(TbContentCategory catParent) {
        return tbContentCategoryMapper.updateByPrimaryKeySelective(catParent);
    }
}