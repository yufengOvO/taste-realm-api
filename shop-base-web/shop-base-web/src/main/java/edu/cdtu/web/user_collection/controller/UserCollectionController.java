package edu.cdtu.web.user_collection.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.cdtu.annotation.Auth;
import edu.cdtu.utils.ResultUtils;
import edu.cdtu.utils.ResultVo;
import edu.cdtu.web.cook_ingred.entity.CookIngred;
import edu.cdtu.web.cook_ingred.service.CooksIngredService;
import edu.cdtu.web.goods.entity.Goods;
import edu.cdtu.web.goods.service.GoodsService;
import edu.cdtu.web.goods_category.service.GoodsCategoryService;
import edu.cdtu.web.ingredients_category.entity.IngredientsCategory;
import edu.cdtu.web.user_collection.entity.UserCollection;
import edu.cdtu.web.user_collection.service.UserCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController//复合注解

@RequestMapping("/api/userCollection")
public class UserCollectionController {
    @Autowired
    UserCollectionService userCollectionService;

    @Autowired
    CooksIngredService cooksIngredService;

    @Autowired
    GoodsService  goodsService;
    //查询用户收藏列表
    @PostMapping("/list")
    public ResultVo list(@RequestBody Long Id){
        QueryWrapper<UserCollection> query = new QueryWrapper<>();
        query.lambda().eq(UserCollection::getUserId,Id);
        //获取用户收藏的Id
        List<UserCollection> list = userCollectionService.list(query);
        //获取收藏的Id
        List<Long> goodsIds = list.stream().map(UserCollection::getGoodsId).collect(Collectors.toList());
        if (goodsIds.isEmpty()){
            return ResultUtils.success("没有收藏的菜单",new ArrayList<>());
        }
        //查询菜单列表
        QueryWrapper<Goods> goodsQuery = new QueryWrapper<>();
        goodsQuery.lambda().in(Goods::getGoodsId,goodsIds);
        List<Goods> goodsList = goodsService.list(goodsQuery);
        goodsList.forEach(goods -> {
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
        return ResultUtils.success("查询成功",goodsList);
    }

    //用户删除收藏
    @DeleteMapping("/del")
    public ResultVo del(@RequestBody Long goodsId){
        QueryWrapper<UserCollection> query = new QueryWrapper<>();
        query.lambda().eq(UserCollection::getGoodsId,goodsId);
        if(userCollectionService.removeById(query)){
            return ResultUtils.success("删除成功");
        }
        return ResultUtils.error("删除失败");
    }

    //收藏
    @PostMapping("/collect")
    @Auth
    public ResultVo collect(@RequestBody UserCollection userCollection){
        //判断是否已经收藏
        QueryWrapper<UserCollection> query = new QueryWrapper<>();
        query.lambda().eq(UserCollection::getGoodsId,userCollection.getGoodsId())
                .eq(UserCollection::getUserId,userCollection.getUserId());
        UserCollection one = userCollectionService.getOne(query);
        if(one == null){ //未收藏

            if(userCollectionService.save(userCollection)){
                return ResultUtils.success("收藏成功!");
            }
            return ResultUtils.error("收藏失败!");
        }else{ //已收藏,取消收藏
            if(userCollectionService.remove(query)){
                return ResultUtils.success("收藏成功!");
            }
            return ResultUtils.error("收藏失败!");
        }
    }

    //判断是否已经收藏
    @GetMapping("/hasCollect")
    @Auth
    public ResultVo hasCollect(UserCollection userCollection){
        //判断是否已经收藏
        QueryWrapper<UserCollection> query = new QueryWrapper<>();
        query.lambda().eq(UserCollection::getGoodsId,userCollection.getGoodsId())
                .eq(UserCollection::getUserId,userCollection.getUserId());
        UserCollection one = userCollectionService.getOne(query);
        if(one != null){ //已经收藏
            return ResultUtils.success("查询成功","1");
        }else{ //未收藏
            return ResultUtils.success("查询成功","0");
        }
    }
}
