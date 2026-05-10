package edu.cdtu.web.cook_ingred.entity;

import lombok.Data;

import java.util.List;

//菜品新增实体类
@Data
public class CookIngredParm {
    private Long cooksId;
    private List<Long> list;//[1,2,3,4]
}
