package edu.cdtu.web.sys_user.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
//后台登陆返回实体类

public class LoginVo {
    private  Long userId;
    private  String nickName;
    //多加一个菜单列表
    private List<MenuVo> menuList = new ArrayList<>();
    //多加一个权限码列表
    private  List<String> codeList = new ArrayList<>();
}
