package edu.cdtu.web.sys_menu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//新建菜单实体类
@Data
@TableName("sys_menu")
public class SysMenu {
    @TableId(type = IdType.AUTO)
    private Long menuId;
    private Long parentId;
    private String title;
    private String code;

    //瞬态字段只存在于实体类中，不会储存到数据库，和前端的树状组件对应
    @TableField(exist = false)
    private Long value;
    //同理
    @TableField(exist = false)
    private String label;

    private String type;
    private String icon;
    private String path;
    private String parentName;
    private Integer orderNum;
    private Date createTime;
    //可以用来包含多个SysMenu对象递归调用，
    @TableField(exist = false)
    private List<SysMenu> children = new ArrayList<>();
}
