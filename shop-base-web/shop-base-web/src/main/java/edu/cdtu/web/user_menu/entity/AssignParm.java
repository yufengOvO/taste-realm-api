package edu.cdtu.web.user_menu.entity;

import lombok.Data;

import java.util.List;

@Data
public class AssignParm {
    private Long assId;
    private List<Long> list;
}
