package edu.cdtu.web.goods_swiper.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.cdtu.utils.ResultUtils;
import edu.cdtu.utils.ResultVo;
import edu.cdtu.web.goods_swiper.entity.GoodsSwiper;
import edu.cdtu.web.goods_swiper.entity.SwiperParm;
import edu.cdtu.web.goods_swiper.service.GoodsSwiperService;
import edu.cdtu.web.wx_user.entity.WxUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/swiper")
public class GoodsSwiperController {

    @Autowired
    GoodsSwiperService goodsSwiperService;

    //新增
    @PostMapping("/addswipr")
    public ResultVo add(@RequestBody GoodsSwiper goodsSwiper){
        if(goodsSwiperService.save(goodsSwiper)){
            return ResultUtils.success("新增成功");
        }
        return ResultUtils.error("新增失败");
    }

    //查询
    @GetMapping("/getList")
    public  ResultVo getList(SwiperParm parm){
        //构造分页对象
        IPage<GoodsSwiper> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
        //构造查询条件
        QueryWrapper<GoodsSwiper> query = new QueryWrapper<>();
        query.lambda().like(StringUtils.isNotEmpty(parm.getTitle()), GoodsSwiper::getTitle, parm.getTitle()).orderByDesc(GoodsSwiper::getTitle);
        IPage<GoodsSwiper> list = goodsSwiperService.page(page, query);
        return ResultUtils.success("查询成功", list);
    }

    // 小程序轮播列表
    @GetMapping("/getSwiperList")
    public ResultVo getSwiperList(SwiperParm parm) {
        // 构造分页对象
        IPage<GoodsSwiper> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
        // 构造查询条件
        QueryWrapper<GoodsSwiper> query = new QueryWrapper<>();
        query.lambda()
                .like(StringUtils.isNotEmpty(parm.getTitle()), GoodsSwiper::getTitle, parm.getTitle())
                .eq(GoodsSwiper::getStatus, "0") // 假设GoodsSwiper有一个getStatus方法并且你想查询status为0的记录
                .orderByDesc(GoodsSwiper::getTitle); // 排序应该在所有条件之后
        // 执行分页查询
        IPage<GoodsSwiper> list = goodsSwiperService.page(page, query);
        return ResultUtils.success("查询成功", list);
    }

    //删除
    @PostMapping("/del")
    public ResultVo del(@RequestBody GoodsSwiper goodsSwiper) {
        if (goodsSwiperService.removeById(goodsSwiper.getBanId())) {
            return ResultUtils.success("删除成功");
        }
        return ResultUtils.error("删除失败");
    }
    //停用
    @PostMapping("/stopSwiper")
    public  ResultVo stopUser(@RequestBody GoodsSwiper goodsSwiper){
        UpdateWrapper<GoodsSwiper> query = new UpdateWrapper<>();

        query.lambda().set(GoodsSwiper::getStatus,goodsSwiper.getStatus()).eq(GoodsSwiper::getBanId,goodsSwiper.getBanId());
        if (goodsSwiperService.update(query)){
            return ResultUtils.success("设置成功");
        }
        return ResultUtils.error("设置失败");
    }

}
