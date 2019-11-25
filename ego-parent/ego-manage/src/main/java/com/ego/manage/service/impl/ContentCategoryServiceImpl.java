/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ContentCategoryServiceImpl
 * Author:   sjx
 * Date:     2019/11/12 15:01
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.manage.service.ContentCategoryService;
import com.ego.pojo.TbContentCategory;
import com.ego.service.ContentCatService;
import com.ego.vo.EgoResult;
import com.ego.vo.EgoTree;
import com.ego.utils.IDUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
public class ContentCategoryServiceImpl implements ContentCategoryService {
    @Reference
    private ContentCatService contentCatService;

    //内容分类管理-显示所有广告信息
    @Override
    public List<EgoTree> showAllCategory(Long pid) {
        List<TbContentCategory> list = contentCatService.showAllCategoryService(pid);
        List<EgoTree> trees = new ArrayList<>();
        for (TbContentCategory cat : list) {
            EgoTree tree = new EgoTree();
            tree.setId(cat.getId());
            tree.setText(cat.getName());
            tree.setState(cat.getIsParent() ? "closed" : "open");
            trees.add(tree);
        }
        return trees;
    }

    //内容分类管理-新增
    @Override
    public EgoResult addContentCat(TbContentCategory cat) {
        EgoResult res = new EgoResult();
        //查询是否有相同名称的子目录（使用模糊查询)
        cat.setStatus(1);
        List<TbContentCategory> catList = contentCatService.selectContentCat(cat);
        if (catList != null && catList.size() > 0) {
            res.setStatus(404);
        } else {
            //新增
            cat.setId(IDUtils.genItemId());
            cat.setName(cat.getName());
            cat.setIsParent(false);
            cat.setParentId(cat.getParentId());
            cat.setSortOrder(1);
            cat.setStatus(1);
            Date date = new Date();
            cat.setCreated(date);
            cat.setUpdated(date);
            int addRes = contentCatService.addContentCat(cat);
            if (addRes > 0) {
                //修改其父节点的isParent为true
                TbContentCategory catParent = new TbContentCategory();
                catParent.setId(cat.getParentId());
                catParent.setIsParent(true);
                int updRes = contentCatService.updateContentCat(catParent);
                if (updRes > 0) {
                    res.setStatus(200);
                    res.setData(cat);
                }
            }
        }
        return res;
    }

    //内容分类管理-重命名
    @Override
    public EgoResult updateContentCat(TbContentCategory cat) {
        EgoResult res = new EgoResult();
        //查询name是否有重名，查询条件父id和name
        //先获取当前对象的详细信息获取其父id
        TbContentCategory catDetail = new TbContentCategory();
        catDetail.setId(cat.getId());
        List<TbContentCategory> catList = contentCatService.selectContentCat(catDetail);
        if (catList != null && catList.size() > 0) {
            TbContentCategory curCat = catList.get(0);
            TbContentCategory selCat = new TbContentCategory();
            selCat.setName(cat.getName());
            selCat.setParentId(curCat.getParentId());
            selCat.setStatus(1);
            List<TbContentCategory> selCats = contentCatService.selectContentCat(selCat);
            if (selCats.size() > 0) {
                //有重复值
                res.setStatus(404);
            } else {
                //不重名直接修改
                int result = contentCatService.updateContentCat(cat);
                if (result > 0) {
                    res.setStatus(200);
                }
            }
        }
        return res;
    }

    //内容分类管理-删除-修改
    @Override
    public EgoResult delContent(TbContentCategory cat) {
        EgoResult res = new EgoResult();
        //查询当前节点是否为父节点
        List<TbContentCategory> curCats = contentCatService.selectContentCat(cat);
        if (curCats != null && curCats.size() > 0) {
            TbContentCategory curCat = curCats.get(0);
            if (curCat.getIsParent()) {
                //是父节点，不允许删除
                res.setStatus(300);
            } else {
                //不是，直接删除
                TbContentCategory updCat = new TbContentCategory();
                updCat.setId(curCat.getId());
                updCat.setStatus(2);
                int updRes = contentCatService.updateContentCat(updCat);
                if (updRes > 0) {
                    //删除后判断其父节点下是否有其他的正常状态的节点
                    TbContentCategory selCat = new TbContentCategory();
                    selCat.setStatus(1);
                    selCat.setParentId(curCat.getParentId());
                    List<TbContentCategory> selRes = contentCatService.selectContentCat(selCat);
                    if (selRes.size() > 0) {
                        //有不进行修改
                        res.setStatus(200);
                    } else {
                        //没有修改父节点的IsParent为false
                        TbContentCategory parentCat = new TbContentCategory();
                        parentCat.setId(curCat.getParentId());
                        parentCat.setIsParent(false);
                        int finRes = contentCatService.updateContentCat(parentCat);
                        if (finRes > 0) {
                            res.setStatus(200);
                        }
                    }
                }
            }
        }
        return res;
    }
}