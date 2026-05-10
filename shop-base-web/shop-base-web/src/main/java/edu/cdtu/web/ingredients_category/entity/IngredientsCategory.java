package edu.cdtu.web.ingredients_category.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("ingred_category")
public class IngredientsCategory {
    @TableId(type = IdType.AUTO)
    private Long ingredId;
    private String IngredName;
    private Long IngredNum;
}
