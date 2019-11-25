/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: SolrItem
 * Author:   sjx
 * Date:     2019/11/18 10:56
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.vo;

import lombok.Data;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/18
 * @since 1.0.0
 */
@Data
public class SolrItem {
    private Long id;
    private String title;
    private String sellPoint;
    private Long price;
    private String[] images;
}