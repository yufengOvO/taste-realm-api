package edu.cdtu.web.sys_user.entity;

import lombok.Data;

@Data
//登陆参数

public class LoginParm {
    private String username;
    private String password;
    private String code;


}
