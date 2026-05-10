package edu.cdtu.web.user_menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.cdtu.web.user_menu.entity.UserMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMenuMapper extends BaseMapper<UserMenu> {
    //新增权限
    boolean saveMenu(@Param("userId") Long userId, @Param("menuIds")List<Long> menuIds);


}
