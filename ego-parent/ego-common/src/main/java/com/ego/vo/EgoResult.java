/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: EgoResult
 * Author:   sjx
 * Date:     2019/11/8 11:24
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
 * @create 2019/11/8
 * @since 1.0.0
 */
@Data
public class EgoResult {
    private int status;
    private Object data;
    private String msg;
}