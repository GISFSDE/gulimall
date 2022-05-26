package com.atguigu.gulimall.product.vo;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lxl
 * @Date: 2022/05/25/20:45
 * @Description:
 */
@Data
public class AttrRespVo extends AttrVo{

    /**所属分类名字*/
    private String catelogName;
    /**所属分组名字*/
    private String groupName;
    private Long[] catelogPath;
}
