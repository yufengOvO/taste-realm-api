package edu.cdtu.web.goods.entity;

import lombok.Data;

//分页参数
@Data
public class GoodsListParm {
    private Long currentPage;
    private Long pageSize;
    private String goodsName;

}
