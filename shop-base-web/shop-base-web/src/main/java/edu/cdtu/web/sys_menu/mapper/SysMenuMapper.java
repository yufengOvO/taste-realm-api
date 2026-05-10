package edu.cdtu.web.sys_menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.cdtu.web.sys_menu.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysMenuMapper extends BaseMapper<SysMenu> {
    //根据用户ID查询菜单
    List<SysMenu> getMenuByUserId(@Param("userId") Long userId);
}
