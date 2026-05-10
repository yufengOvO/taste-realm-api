package edu.cdtu.web.sys_user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import edu.cdtu.utils.ResultUtils;
import edu.cdtu.utils.ResultVo;
import edu.cdtu.web.sys_menu.entity.SysMenu;
import edu.cdtu.web.sys_menu.service.SysMenuService;
import edu.cdtu.web.sys_user.entity.*;
import edu.cdtu.web.sys_user.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController//复合注解

@RequestMapping("/api/sysUser")//在前端输入接口地址进行接对

public class SysUserController {

    //测试
    //  自动注入sysUserService对象 ==SysUserServiceImpl
    @Autowired
    private SysUserService sysUserService;
    //123
    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private SysMenuService sysMenuService;

    @PostMapping
    //新增管理员
    public ResultVo add(@RequestBody SysUser sysUser) {
        //将密码进行md5加密
        String password = DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes());
        //把加密后的密码写入sysuser
        sysUser.setPassword(password);
        if (sysUserService.save(sysUser)) {
            return ResultUtils.success("新增成功!");
        }
        return ResultUtils.error("新增失败!");
    }

    //修改
    @PutMapping
    public ResultVo edit(@RequestBody SysUser sysUser) {
        //将密码进行md5加密
        String password = DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes());
        //把加密后的密码写入sysuser
        sysUser.setPassword(password);
        if (sysUserService.updateById(sysUser)) {
            return ResultUtils.success("编辑成功!");
        }
        return ResultUtils.error("编辑失败!");
    }

    //删除
    @DeleteMapping("/{userId}")
    public ResultVo delete(@PathVariable("userId") Long userId) {
        if (sysUserService.removeById(userId)) {
            return ResultUtils.success("删除成功!");
        }
        return ResultUtils.error("删除失败!");
    }

    //查询
    @GetMapping("/getList")
    public ResultVo getList(PageParm parm) {
//构造查询条件
        QueryWrapper<SysUser> query = new QueryWrapper<>();
//传入的用户名参数来进行模糊查询 SysUser 实体中的 nickName 字段，并且只有在参数 parm 中的 nickName 不为空或 null 时才执行这个查询操作
        query.lambda().like(StringUtils.isNotEmpty(parm.getNickName()), SysUser::getNickName, parm.getNickName());
//构造分页对象获取第几页和数据长度
        IPage<SysUser> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
//查询
        IPage<SysUser> list = sysUserService.page(page, query);
        return ResultUtils.success("查询成功", list);
    }

    //生成验证码
    @PostMapping("/image")
    public ResultVo imageCode(HttpServletRequest request) {
        //生成验证码
        String text = defaultKaptcha.createText();
        //验证码存到session
        HttpSession session = request.getSession();
        session.setAttribute("code", text);
        //生成图片,转换为base64
        BufferedImage bufferedImage = defaultKaptcha.createImage(text);
        ByteArrayOutputStream outputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);
            BASE64Encoder encoder = new BASE64Encoder();
            String base64 = encoder.encode(outputStream.toByteArray());
            String captchaBase64 = "data:image/jpeg;base64," + base64.replaceAll("\r\n", "");
            ResultVo result = new ResultVo("生成成功", 200, captchaBase64);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //登录
    @PostMapping("/login")
    public ResultVo login(@RequestBody LoginParm parm, HttpServletRequest request) {
        //返回用户的菜单和按钮
        List<SysMenu> menuList = null;
        //获取sesson里面的code验证码
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute("code");
        //获取前端传递过来的验证码
        String codeParm = parm.getCode();
        if (StringUtils.isEmpty(code)) {
            return ResultUtils.error("验证码过期!");
        }
        //对比验证码
        if (!codeParm.equals(code)) {
            return ResultUtils.error("验证码错误!");
        }
        //验证用户信息 DigestUtils.md5DigestAsHex()
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        //如果传入有账户和密码，将账户以及md5加密后的账户进行查询
        query.lambda().eq(SysUser::getUsername, parm.getUsername()).eq(SysUser::getPassword,DigestUtils.md5DigestAsHex(parm.getPassword().getBytes()));
        //放入服务层进行查询
        SysUser user = sysUserService.getOne(query);


        if (user == null) {
            return ResultUtils.error("用户名或者密码错误!");
        }
        if (user.getStatus().equals("1")) {
            return ResultUtils.error("账户被停用，请联系管理员!");
        }
        //是超级管理员
        if (StringUtils.isNotEmpty(user.getIsAdmin()) && "1".equals(user.getIsAdmin())){
            menuList = sysMenuService.list();

        }else {
            menuList = sysMenuService.getMenuByUserId(user.getUserId());
        }
        //获取权限字段
        List<String> codelist = Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .map(item -> item.getCode())
                .collect(Collectors.toList());
        //获取菜单
        List<MenuVo> menuVoList = Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(item -> item.getType().equals("1"))
                .map(item -> new MenuVo(item.getMenuId(),item.getTitle(),item.getPath(),item.getIcon(),item.getParentId()))
                .collect(Collectors.toList());
         //返回登录信息
        LoginVo vo = new LoginVo();
        //写入
        vo.setCodeList(codelist);
        vo.setMenuList(menuVoList);
        vo.setUserId(user.getUserId());
        vo.setNickName(user.getNickName());
        return ResultUtils.success("登录成功", vo);
    }

//密码修改
    @PutMapping("/updatePassword")
    public ResultVo updatePassword(@RequestBody UpdatePasswordParm parm){
        //验证原原密码是否正确
        SysUser user = sysUserService.getById(parm.getUserId());
        System.out.println(user);
        //前端传过来原密码加密
        String oldPassword = DigestUtils.md5DigestAsHex(parm.getOldPassword().getBytes());
        System.out.println(oldPassword);
        if(!user.getPassword().equals(oldPassword)){
            return ResultUtils.error("原密码不正确");
        }
//        设置新密码
        UpdateWrapper<SysUser> query = new UpdateWrapper<>();
        query.lambda().set(SysUser::getPassword,DigestUtils.md5DigestAsHex(parm.getPassword().getBytes()))
                .eq(SysUser::getUserId,parm.getUserId());
        if (sysUserService.update(query)){
            return ResultUtils.success("修改成功");
        }
        return ResultUtils.error("修改失败");
    }
}

