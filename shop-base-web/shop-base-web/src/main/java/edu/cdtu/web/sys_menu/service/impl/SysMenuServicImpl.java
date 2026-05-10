package edu.cdtu.web.sys_menu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.cdtu.web.sys_menu.entity.MakeMenuTree;
import edu.cdtu.web.sys_menu.entity.SysMenu;
import edu.cdtu.web.sys_menu.mapper.SysMenuMapper;
import edu.cdtu.web.sys_menu.service.SysMenuService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysMenuServicImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Override
    public List<SysMenu> getParent(){
        //查询菜单实例化查询条件构造器
        QueryWrapper<SysMenu> query = new QueryWrapper<>();
        //查询出sysmenu里面type等于1的值，1为菜单
        query.lambda().eq(SysMenu::getType,"1");
        //将数据库查询出来的值写入menulist
        List<SysMenu> menuList = this.baseMapper.selectList(query);
        //构造Sysmenu实体类
        SysMenu menu = new SysMenu();
        //设置menuId为0
        menu.setMenuId(0L);
        //设置顶级数据为-1，固定输出顶级菜单为一级菜单，用于菜单添加
        menu.setParentId(-1L);
        menu.setTitle("一级菜单");
        menu.setLabel("一级菜单");
        //子节点id为0于数据库对应
        menu.setValue(0L);
        //将固定父级写入列表
        menuList.add(menu);
        //组装数据，将列表转换成树状结构，夫节点为-1
        return MakeMenuTree.makeTree(menuList,-1L);
    }
    @Override
    public List<SysMenu> getMenuByUserId(Long userId){
        return this.baseMapper.getMenuByUserId(userId);
    }
}
