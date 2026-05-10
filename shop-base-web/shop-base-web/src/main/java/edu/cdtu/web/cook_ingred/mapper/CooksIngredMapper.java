package edu.cdtu.web.cook_ingred.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.cdtu.web.cook_ingred.entity.CookIngred;
import edu.cdtu.web.goods.entity.Goods;
import edu.cdtu.web.goods_category.entity.GoodsCategory;
import edu.cdtu.web.ingredients_category.entity.IngredientsCategory;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public interface CooksIngredMapper extends BaseMapper<CookIngred> {
    //保存
        boolean saveIngred(@Param("cooksId") Long cooksId, @Param("ingredIdList") List<Long> ingredIdList);

    List<IngredientsCategory> getIngredBycookId(@Param("ingredIds") Collection<Long> ingredIds);

    //根据食材来查菜单
    List<Goods> findCooksByIngredIds(@Param("ingredIds") List<Long> ingredIds);
}
