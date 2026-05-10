package edu.cdtu.web.ingredients_category.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.cdtu.utils.ResultUtils;
import edu.cdtu.utils.ResultVo;

import edu.cdtu.web.goods_category.entity.SelectType;
import edu.cdtu.web.ingredients_category.entity.IngredientsCategory;
import edu.cdtu.web.ingredients_category.entity.IngredientsParm;
import edu.cdtu.web.ingredients_category.service.IngredientsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/ingredients")
public class IngredientsController {
    @Autowired IngredientsService ingredientsService;
    //新增
    @PostMapping
    public ResultVo add(@RequestBody IngredientsCategory ingredientsCategory) {
        if (ingredientsService.save(ingredientsCategory)) {
            return ResultUtils.success("新增成功!");
        }
        return ResultUtils.error("新增失败!");
    }

    //编辑
    @PutMapping
    public ResultVo edit(@RequestBody IngredientsCategory ingredientsCategory) {
        if (ingredientsService.updateById(ingredientsCategory)) {
            return ResultUtils.success("编辑成功!");
        }
        return ResultUtils.error("编辑失败!");
    }
    @DeleteMapping("/{categoryId}")
    public ResultVo delete(@PathVariable("categoryId") Long categoryId) {
        if (ingredientsService.removeById(categoryId)) {
            return ResultUtils.success("删除成功!");
        }
        return ResultUtils.error("删除失败!");
    }

    //列表
    @GetMapping("/list")
    public ResultVo list(IngredientsParm parm) {
        //构造分页对象
        IPage<IngredientsCategory> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
        //构造查询条件
        QueryWrapper<IngredientsCategory> query = new QueryWrapper<>();
        query.lambda().like(StringUtils.isNotEmpty(parm.getIngredName()), IngredientsCategory::getIngredName, parm.getIngredName()).orderByDesc(IngredientsCategory::getIngredNum);
        //查询数据
        IPage<IngredientsCategory> list = ingredientsService.page(page, query);
        return ResultUtils.success("查询成功", list);
    }

    //小程序显示食材
    @GetMapping("/getSelectList")
    public ResultVo getSelectList() {
        //查询分类列表
        QueryWrapper<IngredientsCategory> query = new QueryWrapper<>();
        query.lambda().orderByAsc(IngredientsCategory::getIngredName);
        List<IngredientsCategory> list = ingredientsService.list(query);
        //存储小程序需要的类型
        List<SelectType> selectList = new ArrayList<>();
        //构造小程序需要的类型
        Optional.ofNullable(list).orElse(new ArrayList<>()).stream().forEach(item -> {
            SelectType type = new SelectType();
            type.setLabel(item.getIngredName());
            type.setValue(item.getIngredId());
            selectList.add(type);
        });
        return ResultUtils.success("查询成功", selectList);
    }

}
