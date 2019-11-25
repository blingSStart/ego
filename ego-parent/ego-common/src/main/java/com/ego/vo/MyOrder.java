/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: MyOrder
 * Author:   sjx
 * Date:     2019/11/22 11:22
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ego.vo;

import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;
import lombok.Data;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sjx
 * @create 2019/11/22
 * @since 1.0.0
 */
@Data
public class MyOrder {
    private Integer paymentType;
    private String payment;
    private List<TbOrderItem> orderItems;
    private TbOrderShipping orderShipping;
}