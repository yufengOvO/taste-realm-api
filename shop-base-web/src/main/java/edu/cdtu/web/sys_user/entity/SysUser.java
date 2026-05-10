package edu.cdtu.web.sys_user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;



//数据，3层共有。用于指定数据库表的名字和字段
//数据库横线隔开的字母下一个大写
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
