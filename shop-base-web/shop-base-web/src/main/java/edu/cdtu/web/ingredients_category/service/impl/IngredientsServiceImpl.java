package edu.cdtu.web.ingredients_category.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.cdtu.web.ingredients_category.entity.IngredientsCategory;
import edu.cdtu.web.ingredients_category.mapper.IngredientsCategoryMapper;
import edu.cdtu.web.ingredients_category.service.IngredientsService;
import org.springframework.stereotype.Service;

@Service
public class IngredientsServiceImpl extends ServiceImpl<IngredientsCategoryMapper,IngredientsCategory> implements IngredientsService {
}
