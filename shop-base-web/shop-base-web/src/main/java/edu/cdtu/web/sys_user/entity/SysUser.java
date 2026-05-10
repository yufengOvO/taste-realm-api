package edu.cdtu.web.sys_user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
//提高代码简洁性，可以省去大量get和set
@TableName("sys_user")
//指定使用的数据表名
public class SysUser {
    @TableId(type = IdType.AUTO)
    //定义字段名
    private Long userId;
    private String username;
    private String password;
    private String nickName;
    private String sex;
    private String phone;
    private String status;
    private String isAdmin;
}
