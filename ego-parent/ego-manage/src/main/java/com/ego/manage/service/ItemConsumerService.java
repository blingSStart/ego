package com.ego.manage.service;

import com.ego.pojo.TbItem;
import com.ego.vo.EgoResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ItemConsumerService {
    //分页显示所有商品
    Map<String, Object> getAllItems(int page, int rows);

    //改变商品状态
    EgoResult updateItems(List<Long> ids, byte status);

    //上传图片
    Map<String, Object> imgUpload(MultipartFile uploadFile);

    //添加商品及详情信息
    EgoResult addItemADesc(TbItem item, String desc, String itemParams);
}
