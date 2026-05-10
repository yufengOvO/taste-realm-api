package edu.cdtu.web.sys_user.entity;

import lombok.Data;

@Data
public class UpdatePasswordParm {
    private Long userId;
    private String password;
    private String oldPassword;
}
