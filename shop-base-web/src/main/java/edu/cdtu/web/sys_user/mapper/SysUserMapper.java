package edu.cdtu.web.sys_user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.cdtu.web.sys_user.entity.SysUser;



//用于实现简单sql继承了BaseMapper的方法提供一个泛型SysUser数据库字段
//数据访问层
//BaseMapper是MyBatis-Plus 框架提供的一个基础 Mapper 接口。里面包含了对数据增删查改的方法
public interface SysUserMapper  extends BaseMapper<SysUser> {

}