package edu.cdtu.web.user_collection.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user_collection")
public class UserCollection {
    @TableId(type = IdType.AUTO)
    private Long numId;
    private Long goodsId;
    private Long userId;
}
