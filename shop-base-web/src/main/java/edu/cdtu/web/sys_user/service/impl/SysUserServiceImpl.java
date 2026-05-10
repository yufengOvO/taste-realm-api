package edu.cdtu.web.sys_user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.cdtu.web.sys_user.entity.SysUser;
import edu.cdtu.web.sys_user.mapper.SysUserMapper;
import edu.cdtu.web.sys_user.service.SysUserService;
import org.springframework.stereotype.Service;

//服务层注解，告诉spring这个是服务层方法
@Service
//服务层方法，实现了服务层接口，实现接口的同时需要实现接口的方法，所以继承了ServiceImpl方法用于实现接口内的方法，继承的方法需要提供2个泛型
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
}
