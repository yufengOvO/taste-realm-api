package edu.cdtu.web.cook_ingred.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("cooks_ingred")
public class CookIngred {
    @TableId(type = IdType.AUTO)
    private Long numId;
    private Long ingredId;
    private Long cooksId;
}
