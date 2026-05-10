package edu.cdtu.web.sys_menu.entity;

import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//将给定的菜单项列表转换为树状结构的菜单数据，以便于在前端页面展示为树形菜单
public class MakeMenuTree {
    //树状结构
    public static List<SysMenu> makeTree(List<SysMenu> menuList,Long pid){
        //接收组装后的树数据
        List<SysMenu> list = new ArrayList<>();
        //判断传入的menuList是否为空，如果为空直接返回空的数据
        //把menuList转换成Optional对象，这个对象可以为空，如果为空则创建一个新的列表
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                //把list转换成流方便进行流式处理，将列表内的数据排队进行处理
                .stream()
                //对流中的每个数据进行筛选，过滤掉为空的数值，根据传入的pid值来判断谁是父节点，0L就是数字为0的就是父节点
                .filter(item -> item != null && item.getParentId().equals(pid))
                //创建一个新的 SysMenu 对象 menu。
                //使用 BeanUtils.copyProperties(item,menu) 快速复制菜单项的属性到 menu 对象中。
                //设置 menu 的 label 和 value 属性。
                //递归调用 makeTree 方法，获取当前菜单项的子菜单，并将结果设置为当前菜单项的子菜单列表。
                //将组装好的菜单项添加到 list 中
                .forEach(item ->{
                    //组装树数据实例化变量
                    SysMenu menu = new SysMenu();
                    //快速复制值
                    BeanUtils.copyProperties(item,menu);
                    //设置
                    menu.setLabel(item.getTitle());
                    menu.setValue(item.getMenuId());
                    //递归查询下级，自动调用自己，这里已经拥有父节点，然后再循环每一条数据根据的menuID来找子节点，例如子节点ParentId是1，传入的是父节点的menuid为1就会找到所有子节点id为的数据
                    List<SysMenu> children = makeTree(menuList, item.getMenuId());
                    //写入子节点
                    menu.setChildren(children);
                    list.add(menu);
                });
        return list;
    }
}
