package edu.cdtu.web.sys_user.entity;

import lombok.Data;

@Data
public class PageParm {
    //    对用户查询操作时对数据列表进行分页处理
    private Long currentPage;//页码
    private Long pageSize;//数据条
    private String nickName;//用户名
}
