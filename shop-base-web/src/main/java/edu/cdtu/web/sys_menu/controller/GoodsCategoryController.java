package edu.cdtu.web.sys_menu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.cdtu.untils.ResultUtils;
import edu.cdtu.untils.ResultVo;
import edu.cdtu.web.sys_menu.entity.GoodsCategory;
import edu.cdtu.web.sys_menu.entity.ListParm;
import edu.cdtu.web.sys_menu.entity.SelectType;
import edu.cdtu.web.sys_menu.service.GoodsCategoryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/category")
public class GoodsCategoryController {

    @Autowired

    private GoodsCategoryService GoodsCategoryService;
    //新增

    @PostMapping
    public ResultVo add(@RequestBody GoodsCategory goodsCategory) {
        if (GoodsCategoryService.save(goodsCategory)) {
            return ResultUtils.success("新增成功!");
        }
        return ResultUtils.error("新增失败!");
    }
    //编辑

    @PutMapping

    public ResultVo edit(@RequestBody GoodsCategory goodsCategory) {
        if (GoodsCategoryService.updateById(goodsCategory)) {
            return ResultUtils.success("编辑成功!");
        }
        return ResultUtils.error("编辑失败!");
    }
    //删除

    @DeleteMapping("/{categoryId}")
    public ResultVo delete(@PathVariable("categoryId") Long categoryId) {
        if (GoodsCategoryService.removeById(categoryId)) {
            return ResultUtils.success("删除成功!");
        }
        return ResultUtils.error("删除失败!");
    }
    //列表

    @GetMapping("/list")

    public ResultVo list(ListParm parm) {//构造分页对象
        IPage<GoodsCategory> page = new Page<>(parm.getCurrentPage(), parm.getPageSize()); //构造查询条件
        QueryWrapper<GoodsCategory> query = new QueryWrapper<>();
        query.lambda().like(StringUtils.isNotEmpty(parm.getCategoryName()), GoodsCategory::getCategoryName, parm.getCategoryName()).orderByDesc(GoodsCategory::getOrderNum);
        //查询数据
        IPage<GoodsCategory> list = GoodsCategoryService.page(page, query);
        return ResultUtils.success("查询成功", list);
    }
//小程序显示分类


    @GetMapping("/getSelectList")
    public ResultVo getSelectList() {
        //查询分类列表
        QueryWrapper<GoodsCategory> query = new QueryWrapper<>();
        query.lambda().orderByAsc(GoodsCategory::getOrderNum);
        List<GoodsCategory> list = GoodsCategoryService.list(query);
        //存储小程序需要的类型
        List<SelectType> selectList = new ArrayList<>();
        //构造小程序需要的类型
        Optional.ofNullable(list).orElse(new ArrayList<>()).stream().forEach(item -> {
            SelectType type = new SelectType();
            type.setLabel(item.getCategoryName());
            type.setValue(item.getCategoryId());
            selectList.add(type);
        });
        return ResultUtils.success("查询成功", selectList);
    }
}
