package edu.cdtu.web.sys_user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.cdtu.web.sys_user.entity.SysUser;
import edu.cdtu.web.sys_user.mapper.SysUserMapper;
import edu.cdtu.web.sys_user.service.SysUserService;
import org.springframework.stereotype.Service;
@Service//服务类
//扩展ServiceImpl 类并实现了 SysUserService 接口
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
}
