package edu.cdtu.web.user_menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.cdtu.web.user_menu.entity.AssignParm;
import edu.cdtu.web.user_menu.entity.UserMenu;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserMenuService extends IService<UserMenu> {
    void saveMenu(AssignParm parm);
}
