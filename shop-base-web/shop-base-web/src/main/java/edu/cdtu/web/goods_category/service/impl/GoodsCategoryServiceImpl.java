package edu.cdtu.web.goods_category.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.cdtu.web.goods_category.entity.GoodsCategory;
import edu.cdtu.web.goods_category.mapper.GoodsCategoryMapper;
import edu.cdtu.web.goods_category.service.GoodsCategoryService;
import org.springframework.stereotype.Service;

@Service
//操作商品分类业务呈类GoodsCategoryServiceImpl，该类基础serviceImpl实现自定义接口GoodsCategoryService
public class GoodsCategoryServiceImpl extends ServiceImpl<GoodsCategoryMapper, GoodsCategory> implements GoodsCategoryService {

}
