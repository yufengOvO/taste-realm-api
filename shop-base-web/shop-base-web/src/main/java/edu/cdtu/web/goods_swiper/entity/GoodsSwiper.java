package edu.cdtu.web.goods_swiper.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("goods_swiper")
public class GoodsSwiper {
    @TableId(type = IdType.AUTO)
    private Long banId;
    private String title;
    private String status;
    private Long orderNum;
    //
    private String images;
}
