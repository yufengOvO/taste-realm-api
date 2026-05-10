package edu.cdtu.web.sys_menu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.cdtu.annotation.Auth;
import edu.cdtu.utils.ResultUtils;
import edu.cdtu.utils.ResultVo;
import edu.cdtu.web.sys_menu.entity.MakeMenuTree;
import edu.cdtu.web.sys_menu.entity.PermissonVo;
import edu.cdtu.web.sys_menu.entity.SysMenu;
import edu.cdtu.web.sys_menu.service.SysMenuService;

import edu.cdtu.web.sys_user.entity.SysUser;
import edu.cdtu.web.sys_user.service.SysUserService;
import edu.cdtu.web.user_menu.entity.AssignParm;
import edu.cdtu.web.user_menu.service.UserMenuService;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/menu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private UserMenuService userMenuService;

    @PostMapping
    //新增
    public ResultVo add(@RequestBody SysMenu sysMenu){
        sysMenu.setCreateTime(new Date());
        if (sysMenuService.save(sysMenu)){
            return ResultUtils.success("新增成功");
        }
        return ResultUtils.success("新增失败");
    }

    //编辑
    @PutMapping
    public ResultVo edit(@RequestBody SysMenu sysMenu ){
        if (sysMenuService.updateById(sysMenu)){
            return ResultUtils.success("编辑成功");
        }
        return ResultUtils.success("编辑失败");
    }

    //删除
    @DeleteMapping("/{menuId}")
    public ResultVo delete(@PathVariable("menuID") Long menuId){
        if (sysMenuService.removeById(menuId)){
            return ResultUtils.success("删除成功");
        }
        return ResultUtils.success("删除失败");
    }

    //列表
    @GetMapping("/list")
    public ResultVo list(){
        QueryWrapper<SysMenu> quer = new QueryWrapper<>();
        quer.lambda().orderByAsc(SysMenu::getOrderNum);
        //查询数据库
        List<SysMenu> menuList = sysMenuService.list(quer);
        //组装树状数据调用maketree来自动转换成树状数据，0L代表父节点为0
        List<SysMenu> list = MakeMenuTree.makeTree(menuList,0L);
        //返回父节点
        return ResultUtils.success("查询成功",list);
    }

    //上级菜单，菜单查询
    @GetMapping("/getParent")
    public ResultVo getParent(){
        //通过服务层的getParent来进行查询根据数据库的parent_id来进行树状排序
        List<SysMenu> parent = sysMenuService.getParent();
        return ResultUtils.success("查询成功",parent);
    }

    //分类菜单数据查询和回显
    @GetMapping("/getAssignTree")
    public ResultVo getAssignTree(Long userId,Long assId){
        //根据前端传入用户Id放入数据库进行查询，获取当前用户登录信息
        SysUser user = sysUserService.getById(userId);
        //用于存储查询到的菜单信息
        List<SysMenu> menuList = null;
        //判断用户是否是超级管理员，如果等于1就是超级管理员
        if(StringUtils.isNotEmpty(user.getIsAdmin()) && "1".equals(user.getIsAdmin())){
            //如果用户是超级管理员则获取全部的列表数据
            menuList = sysMenuService.list();
        }else {
            //根据用户id查询菜单信息
            menuList = sysMenuService.getMenuByUserId(userId);
        }
        //组装树数据
        List<SysMenu> menus = MakeMenuTree.makeTree(menuList,0L);
        //设置菜单数据，存储菜单数据和回显数据
        PermissonVo vo = new PermissonVo();
        //
        vo.setMenuList(menus);
        //查询回显数据
        List<SysMenu> menuByUserId = sysMenuService.getMenuByUserId(assId);
        List<Long> ids = new ArrayList<>();
        //查询
        Optional.ofNullable(menuByUserId).orElse(new ArrayList<>())
                .stream()
                .filter(item -> item != null)
                .forEach(item ->{
                    ids.add(item.getMenuId());
                });
        //将查询写入菜单数据
        vo.setCheckList(ids.toArray());
        return ResultUtils.success("查询成功",vo);
    }
    //菜单分配保存
    @PostMapping("/assignSave")
    @Auth
    public ResultVo assignSave(@RequestBody AssignParm parm){
        //判断是否是超级管理员
        SysUser user = sysUserService.getById(parm.getAssId());
        if(user != null && StringUtils.isEmpty(user.getIsAdmin()) && user.getIsAdmin().equals("1")){
            //如果用户是超级管理员则获取全部的列表数据
            return ResultUtils.error("当前用户不是超级管理员，无需分配菜单");
        }
        userMenuService.saveMenu(parm);
        return ResultUtils.success("新增成功");
    }
}
