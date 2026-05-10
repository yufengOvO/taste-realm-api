package edu.cdtu.web.sys_menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.cdtu.web.sys_menu.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {
    List<SysMenu> getParent();

    List<SysMenu> getMenuByUserId(Long userId);
}
