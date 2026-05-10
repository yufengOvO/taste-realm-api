package edu.cdtu.web.wx_user.entity;

import lombok.Data;
//登陆成功返回用户的数据
@Data
public class LoginVo {
    private Long userId;
    private String phone;
    private String nickName;
    private String picture;
    private String token;
}
