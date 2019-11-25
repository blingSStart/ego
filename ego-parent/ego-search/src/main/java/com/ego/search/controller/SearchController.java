/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: SearchController
 * Author:   sjx
 * Date:     2019/11/15 15:54
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.search.controller;

import com.ego.search.service.SearchService;
import com.ego.vo.EgoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/15
 * @since 1.0.0
 */
@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;

    @RequestMapping({"/search.html", "/search"})
    public String showIndex(String q, @RequestParam(defaultValue = "1") int page, Model model) {
        try {
            q = new String(q.getBytes("iso8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Map<String, Object> map = searchService.selectSolrItem(q, page);
        model.addAttribute("itemList", map.get("itemList"));
        model.addAttribute("totalPages", map.get("totalPages"));
        model.addAttribute("page", page);
        model.addAttribute("query", q);
        return "WEB-INF/jsp/search.jsp";
    }

    //初始化solr搜索引擎库，添加数据
    @RequestMapping(value = "/initSolr", produces = "application/json;charset='utf-8'")
    @ResponseBody
    public String initSolr() {
        long start = System.currentTimeMillis();
        searchService.initSolr();
        long end = System.currentTimeMillis();
        return "初始化数据库时间为：" + (end - start) / 1000 / 60;
    }


    //添加数据到solr
    @RequestMapping("solr/add")
    @ResponseBody
    public EgoResult addSolr(@RequestParam Map<String, String> map) {
        return searchService.addSolr(map);
    }

    //添加数据到solr
    @RequestMapping("solr/del")
    @ResponseBody
    public EgoResult delSolr(@RequestParam Map<String, String> map) {
        return searchService.delSolr(map);
    }


    //出现脏读删除solr中所有数据
    @RequestMapping("solr/delAll")
    @ResponseBody
    public EgoResult delAll() {
        return searchService.delAll();
    }
}