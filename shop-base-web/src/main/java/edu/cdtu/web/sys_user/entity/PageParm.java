package edu.cdtu.web.sys_user.entity;

import lombok.Data;


//数据
@Data
public class PageParm {
//    对用户查询操作时对数据列表进行分页处理
    private Long currentPage; //第几页
    private Long pageSize; //数据条
    private String nickName;//用户名
}
