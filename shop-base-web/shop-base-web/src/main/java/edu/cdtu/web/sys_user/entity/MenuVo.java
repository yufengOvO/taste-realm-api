package edu.cdtu.web.sys_user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
//菜单数据
public class MenuVo {
    private  Long menuId;
    private String title;
    private String path;
    private String icon;
    private Long parentId;
}
