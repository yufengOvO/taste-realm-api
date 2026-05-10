package edu.cdtu.web.user_collection.entity;

import edu.cdtu.web.goods.entity.Goods;
import lombok.Data;

import java.util.List;

@Data
public class GoodsParm {
    private List<Goods> goodsList;
}
