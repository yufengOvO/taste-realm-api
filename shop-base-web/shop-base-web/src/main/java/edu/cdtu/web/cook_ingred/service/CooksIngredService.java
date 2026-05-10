package edu.cdtu.web.cook_ingred.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.cdtu.web.cook_ingred.entity.CookIngred;
import edu.cdtu.web.cook_ingred.entity.CookIngredParm;
import edu.cdtu.web.goods.entity.Goods;
import edu.cdtu.web.goods_category.entity.GoodsCategory;
import edu.cdtu.web.ingredients_category.entity.IngredientsCategory;


import java.util.List;


public interface CooksIngredService extends IService<CookIngred> {
    boolean saveIngred(CookIngredParm parm);

    List<IngredientsCategory> getIngredBycookId(List<Long> ingredId);

    List<Goods> findCooksByIngredIds(List<Long> ingredId);
}
