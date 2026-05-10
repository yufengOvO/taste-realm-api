package edu.cdtu.web.goods.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.cdtu.utils.ResultUtils;
import edu.cdtu.utils.ResultVo;
import edu.cdtu.web.cook_ingred.entity.CookIngred;
import edu.cdtu.web.cook_ingred.entity.CookIngredParm;
import edu.cdtu.web.cook_ingred.service.CooksIngredService;
import edu.cdtu.web.goods.entity.Goods;
import edu.cdtu.web.goods.entity.GoodsListParm;
import edu.cdtu.web.goods.entity.StatusParm;
import edu.cdtu.web.goods.entity.WxIndexParm;
import edu.cdtu.web.goods.service.GoodsService;

import edu.cdtu.web.goods_category.service.GoodsCategoryService;

import edu.cdtu.web.ingredients_category.entity.IngredientsCategory;

import edu.cdtu.web.user_collection.entity.UserCollection;
import edu.cdtu.web.user_collection.service.UserCollectionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private CooksIngredService cooksIngredService;

    @Autowired
    private GoodsCategoryService goodsCategoryService;

    //发布
    @PostMapping("/release")
    public ResultVo release(@RequestBody Goods goods) {
        //设置发布时间 当前时间
        goods.setCreateTime(new Date());
        goods.setStatus("1");
        if (goodsService.save(goods)) {
            // 获取刚刚保存的商品ID
//            Long goodsId = goods.getGoodsId();
            //返回传入的菜品信息，然后在放入食材id对应表
            QueryWrapper<Goods> goosBYId = new QueryWrapper<>();
            goosBYId.lambda().eq(Goods::getGoodsName,goods.getGoodsName());
            List<Goods> goodList = goodsService.list(goosBYId);
            return ResultUtils.success("新增成功!",goodList);
        }
        return ResultUtils.error("发布失败!");
    }

    //发布菜品食材
    @PostMapping("/addIngred")
    public ResultVo addIngred(@RequestBody CookIngredParm cookIngred){
        if(cooksIngredService.saveIngred(cookIngred)){
            return ResultUtils.success("发布成功");
        }
        return ResultUtils.error("发布失败!");
    }

    //后台查询菜品列表
    @GetMapping("/list")
    public ResultVo getList(GoodsListParm parm) {
        //构造分页对象
        IPage<Goods> page = new Page<>(parm.getCurrentPage(), parm.getPageSize()); //构造查询条件
        QueryWrapper<Goods> query = new QueryWrapper<>();

        //只要未删除数据
        query.eq("delete_status", "0");
        //模糊查询商品名称 降序排列
        query.lambda().like(StringUtils.isNotEmpty(parm.getGoodsName()), Goods::getGoodsName, parm.getGoodsName()).orderByDesc(Goods::getCreateTime);
        IPage<Goods> list = goodsService.page(page, query);
        //查询菜品的分类
        // 查询每个商品的分类信息
        list.getRecords().forEach(goods -> {
            // 创建查询包装器
            QueryWrapper<CookIngred> queryGoodsIds = new QueryWrapper<>();
            // 设置查询条件，查询匹配的商品ID
            queryGoodsIds.lambda().eq(CookIngred::getCooksId, goods.getGoodsId());
            // 从服务层进行查询
            List<CookIngred> cookIngreds = cooksIngredService.list(queryGoodsIds);
            // 获取分类ID列表
            List<Long> cookIngredsId = cookIngreds.stream().map(CookIngred::getIngredId).collect(Collectors.toList());
            // 使用分类ID列表进行连表查询，获取商品的分类信息
            if (!cookIngredsId.isEmpty()) {
                // 根据分类ID列表查询分类信息
                List<IngredientsCategory> categories = cooksIngredService.getIngredBycookId(cookIngredsId);
                goods.setGoodsCategories(categories);
            }
        });
        return ResultUtils.success("查询成功", list);

    }
    //通过食材来查询菜单
    @PostMapping("/getByingred")
    public ResultVo getCooksByIngred(@RequestBody List<Long> ingred){
        List<Goods> list = cooksIngredService.findCooksByIngredIds(ingred);
        list.forEach(goods -> {
            // 创建查询包装器
            QueryWrapper<CookIngred> queryGoodsIds = new QueryWrapper<>();
            // 设置查询条件，查询匹配的商品ID
            queryGoodsIds.lambda().eq(CookIngred::getCooksId, goods.getGoodsId());
            // 从服务层进行查询
            List<CookIngred> cookIngreds = cooksIngredService.list(queryGoodsIds);
            // 获取分类ID列表
            List<Long> cookIngredsId = cookIngreds.stream().map(CookIngred::getIngredId).collect(Collectors.toList());
            // 使用分类ID列表进行连表查询，获取商品的分类信息
            if (!cookIngredsId.isEmpty()) {
                // 根据分类ID列表查询分类信息
                List<IngredientsCategory> categories = cooksIngredService.getIngredBycookId(cookIngredsId);
                goods.setGoodsCategories(categories);
            }
        });
        return ResultUtils.success("查询成功", list);

    }
//    后台删除
    @PostMapping("/delete")
    public ResultVo delete(@RequestBody StatusParm parm) {
        UpdateWrapper<Goods> query = new UpdateWrapper<>();
        query.lambda().set(Goods::getDeleteStatus,"1")
                .eq(Goods::getGoodsId,parm.getGoodsId());
        //删除分类对应表
        QueryWrapper<CookIngred> queryGoodsIds = new QueryWrapper<>();
        queryGoodsIds.lambda().eq(CookIngred::getCooksId, parm.getGoodsId());
        if(goodsService.update(query) && cooksIngredService.remove(queryGoodsIds)){
            return ResultUtils.success("删除成功");
        }
        return  ResultUtils.error("删除失败");
    }
    //后台上下架
    @PostMapping("/upanddown")
    public ResultVo upandown(@RequestBody StatusParm parm){
        UpdateWrapper<Goods> query = new UpdateWrapper();
        query.lambda().set(Goods::getStatus,parm.getStatus())
                .eq(Goods::getGoodsId,parm.getGoodsId());
        if(goodsService.update(query)){
            return ResultUtils.success("设置成功");
        }
        return ResultUtils.error("设置失败");
    }

    //后台推荐首页
    @PostMapping("/setIndex")
    public ResultVo setIndex(@RequestBody StatusParm parm){
        UpdateWrapper<Goods> query  = new UpdateWrapper<>();
        query.lambda().set(Goods::getSetIndex,parm.getSetIndex())
                .eq(Goods::getGoodsId,parm.getGoodsId());
        if(goodsService.update(query)){
            return ResultUtils.success("设置成功");
        }
        return ResultUtils.error("设置失败");
    }

    //小程序首页推荐列表
    @GetMapping("/getIndexList")
    public ResultVo getIndexList(WxIndexParm parm) {
        QueryWrapper<Goods> query = new QueryWrapper<>();
        query.lambda().like(StringUtils.isNotEmpty(parm.getKeywords()), Goods::getGoodsName, parm.getKeywords())
                .eq(Goods::getSetIndex, "1")//推荐首页
                .eq(Goods::getStatus, "0")//上架
                .orderByDesc(Goods::getCreateTime);//上架的
        IPage<Goods> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
        IPage<Goods> list = goodsService.page(page, query);
        list.getRecords().forEach(goods -> {
            // 创建查询包装器
            QueryWrapper<CookIngred> queryGoodsIds = new QueryWrapper<>();
            // 设置查询条件，查询匹配的商品ID
            queryGoodsIds.lambda().eq(CookIngred::getCooksId, goods.getGoodsId());
            // 从服务层进行查询
            List<CookIngred> cookIngreds = cooksIngredService.list(queryGoodsIds);
            // 获取分类ID列表
            List<Long> cookIngredsId = cookIngreds.stream().map(CookIngred::getIngredId).collect(Collectors.toList());
            // 使用分类ID列表进行连表查询，获取商品的分类信息
            if (!cookIngredsId.isEmpty()) {
                // 根据分类ID列表查询分类信息
                List<IngredientsCategory> categories = cooksIngredService.getIngredBycookId(cookIngredsId);
                goods.setGoodsCategories(categories);
            }
        });
        return ResultUtils.success("查询成功", list);
    }
    //小程序根据分类查询菜品
    @GetMapping("/getcooksCategory")
    public ResultVo getcooksCategory(WxIndexParm parm){
        QueryWrapper<Goods> query = new QueryWrapper();
        query.lambda().eq(Goods::getCategoryName,parm.getKeywords()).orderByDesc(Goods::getStatus);
        IPage<Goods> page = new Page<>(parm.getCurrentPage(),parm.getPageSize());
        IPage<Goods> list = goodsService.page(page,query);
        //查询食材
        list.getRecords().forEach(goods -> {
            // 创建查询包装器
            QueryWrapper<CookIngred> queryGoodsIds = new QueryWrapper<>();
            // 设置查询条件，查询匹配的商品ID
            queryGoodsIds.lambda().eq(CookIngred::getCooksId, goods.getGoodsId());
            // 从服务层进行查询
            List<CookIngred> cookIngreds = cooksIngredService.list(queryGoodsIds);
            // 获取分类ID列表
            List<Long> cookIngredsId = cookIngreds.stream().map(CookIngred::getIngredId).collect(Collectors.toList());
            // 使用分类ID列表进行连表查询，获取商品的分类信息
            if (!cookIngredsId.isEmpty()) {
                // 根据分类ID列表查询分类信息
                List<IngredientsCategory> categories = cooksIngredService.getIngredBycookId(cookIngredsId);
                goods.setGoodsCategories(categories);
            }

        });
        return ResultUtils.success("查询成功", list);
    }

    //查询自己发布的菜品
    @PostMapping("/getcooksWiter")
    public ResultVo getcooksWiter(@RequestBody WxIndexParm parm){
        QueryWrapper<Goods> query = new QueryWrapper();
        query.lambda().eq(Goods::getUserId,parm.getKeywords()).orderByDesc(Goods::getStatus);
        IPage<Goods> page = new Page<>(parm.getCurrentPage(),parm.getPageSize());
        IPage<Goods> list = goodsService.page(page,query);
        //查询食材
        list.getRecords().forEach(goods -> {
            // 创建查询包装器
            QueryWrapper<CookIngred> queryGoodsIds = new QueryWrapper<>();
            // 设置查询条件，查询匹配的商品ID
            queryGoodsIds.lambda().eq(CookIngred::getCooksId, goods.getGoodsId());
            // 从服务层进行查询
            List<CookIngred> cookIngreds = cooksIngredService.list(queryGoodsIds);
            // 获取分类ID列表
            List<Long> cookIngredsId = cookIngreds.stream().map(CookIngred::getIngredId).collect(Collectors.toList());
            // 使用分类ID列表进行连表查询，获取商品的分类信息
            if (!cookIngredsId.isEmpty()) {
                // 根据分类ID列表查询分类信息
                List<IngredientsCategory> categories = cooksIngredService.getIngredBycookId(cookIngredsId);
                goods.setGoodsCategories(categories);
            }

        });
        return ResultUtils.success("查询成功", list);
    }
}