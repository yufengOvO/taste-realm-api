package edu.cdtu.web.sys_user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.cdtu.untils.ResultUtils;
import edu.cdtu.untils.ResultVo;
import edu.cdtu.web.sys_user.entity.PageParm;
import edu.cdtu.web.sys_user.entity.SysUser;
import edu.cdtu.web.sys_user.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//控制层
@RestController//注解方法返回的数据都是json格式
@RequestMapping("/api/sysUser")//请求地址用来回应post请求
public class SysUserController {
//    自动注入service服务层
    //方便控制类使用这个方法
    @Autowired
    private SysUserService sysUserService;
//    新增
    @PostMapping//当注释下面还有post响应，方法名就会是请求连接
    public ResultVo add(@RequestBody SysUser sysUser){
        if (sysUserService.save(sysUser)){
            return ResultUtils.success("新增成功");
        }
        return ResultUtils.error("新增失败");
    }
//    修改
    @PutMapping
    public ResultVo edit(@RequestBody SysUser sysUser){
        if (sysUserService.updateById(sysUser)){
            return ResultUtils.success("修改成功");
        }
        return  ResultUtils.error("修改失败");
    }
//    删除
    @DeleteMapping("/{userId}")//用于回应put请求，并吧连接中的数据填入方法
    public  ResultVo delete(@PathVariable("userId") Long userID){
        if (sysUserService.removeById(userID)){
            return ResultUtils.success("删除成功");
        }
        return ResultUtils.error("删除失败");
    }
    //查询
    @GetMapping("/getList")//用于回应get请求
    public ResultVo getList(PageParm parm){
//        创造查询条件实例化对象
        QueryWrapper<SysUser> query= new QueryWrapper<>();
//        通过lambda模糊查询，如果用户发起了请求中带有parm.getNickName()，就将用户的输入的昵称放入SysUser实体类进行查询。如果用户没有放入查询条件则不执行
        query.lambda().like(StringUtils.isNotEmpty(parm.getNickName()),SysUser::getNickName,parm.getNickName());
//        创建分页对象，获取第几页，和数据条长度
        IPage<SysUser> page =new Page<>(parm.getCurrentPage(),parm.getPageSize());

        //sysUserService分页查寻，放入分页对象和模糊查询对象
        IPage<SysUser> list = sysUserService.page(page,query);
        return ResultUtils.success("查询成功",list);
    }

}
