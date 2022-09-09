package com.atguigu.common.to.es;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lxl
 * @Date: 2022/08/31/19:13
 * @Description:
 */
@Data
public class SkuHasStockVo {

    private Long skuId;

    private Boolean hasStock;
}
