package edu.cdtu.web.goods_category.entity;
//小程序下拉菜单实体类，用于小程序显示商品分类列表

import lombok.Data;

//小程序下拉菜单
@Data
public class SelectType {
    private Long value;
    private String label;
    private String image;
}
