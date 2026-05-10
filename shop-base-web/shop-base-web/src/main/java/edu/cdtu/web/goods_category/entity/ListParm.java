package edu.cdtu.web.goods_category.entity;
//模糊查询分类页实体类，查询操作对数据列表进行分页处理

import lombok.Data;

//商品分类分页实体类
@Data
public class ListParm {
    private Integer currentPage;//当前页
    private Integer pageSize;//每页查询的条数
    private String categoryName;
}