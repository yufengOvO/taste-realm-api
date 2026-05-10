package edu.cdtu.web.goods.entity;

import lombok.Data;

@Data
public class WxIndexParm {
    private long currentPage;
    private long pageSize;
    private String keywords;
}
