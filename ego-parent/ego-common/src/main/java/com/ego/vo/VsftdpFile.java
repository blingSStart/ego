/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: File
 * Author:   sjx
 * Date:     2019/11/8 16:13
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/8
 * @since 1.0.0
 */
@Component
@Data
public class VsftdpFile {
    String host = "192.168.78.131";
    int port = 21;
    String username = "ftpuser";
    String password = "ftpuser";
    String basePath = "/home/ftpuser";
    String filePath = "/";
    String url = "http://192.168.78.131/";
    //大广告key
    String redisBigAdvKey = "bigAdv";
    //内容详情key
    int contentCatId = 89;
    int pageSize = 20;
    //搜索search模块地址
    String searchUrl = "http://localhost:8083/";
    //商品key
    String itemKey = "item:";
    //商品详情key
    String itemDescKey = "item_desc:";
    //商品规格参数key
    String itemParamKey = "item_param_item:";
    //登录模块地址
    String passportUrl = "http://localhost:8084/";
    //cartkey
    String cartKey = "cart:";
}