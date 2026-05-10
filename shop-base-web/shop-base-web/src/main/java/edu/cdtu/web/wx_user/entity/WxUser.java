package edu.cdtu.web.wx_user.entity;
//普通用户实体类


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("wx_user")
public class WxUser {
    @TableId (type = IdType.AUTO)
    private Long userId;
    private String nickName;
    private String picture;
    private String phone;
    private String username;
    private String password;
//    0:启用1：停用
    private String status;

}
