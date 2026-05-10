package edu.cdtu.web.goods.entity;

import lombok.Data;

@Data
public class StatusParm {
    private Long goodsId;
    private String status;
    private String setIndex;
}
