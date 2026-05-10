package edu.cdtu.web.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import edu.cdtu.web.ingredients_category.entity.IngredientsCategory;
import lombok.Data;


import java.util.Date;
import java.util.List;

@Data
@TableName("goods")
public class Goods {
    //商品id
    @TableId(type = IdType.AUTO)
    private Long goodsId;
    //发布人id
    private Long userId;
    //分类id
    private Long categoryId;
    //分类名字
    private String categoryName;
    //商品名称
    private String goodsName;
    //商品简介
    private String goodsDesc;
    //步骤
    private String make;
    //姓名
    private String userName;
    //电话
    private String phone;
    //视频
    private String video;
    //图片
    private String image;
    //状态 0：上架 1：下架
    private  String status;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    // 0：未推荐 1：推荐首页
    private String setIndex;
    // 0：未删除 1：已删除
    private String deleteStatus;
    //食材列表
    @TableField(exist = false)
    private List<IngredientsCategory> goodsCategories;


}