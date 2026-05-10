package edu.cdtu.web.wx_user.entity;

import lombok.Data;

@Data
public class WxUserPageParm {

    private Long currentPage;//页码
    private Long pageSize;//数据条
    private String phone;//用户名
}
