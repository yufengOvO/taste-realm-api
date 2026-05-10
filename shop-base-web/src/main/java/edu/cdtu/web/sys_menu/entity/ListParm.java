package edu.cdtu.web.sys_menu.entity;

import lombok.Data;

@Data
public class ListParm {
    private Integer currentPage;//当前页
    private Integer pageSize;//每页查询条数
    private  String categoryName;
}
