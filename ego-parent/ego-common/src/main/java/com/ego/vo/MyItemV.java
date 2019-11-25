/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: MyItemV
 * Author:   sjx
 * Date:     2019/11/19 10:41
 * Description: 作为itemParamItem对象
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.vo;

import lombok.Data;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈作为itemParamItem对象〉
 *
 * @author sjx
 * @create 2019/11/19
 * @since 1.0.0
 */
@Data
public class MyItemV {
    private String group;
    private List<MyKV> params;
}