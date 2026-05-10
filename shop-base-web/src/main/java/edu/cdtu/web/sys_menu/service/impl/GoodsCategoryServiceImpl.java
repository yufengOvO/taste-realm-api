package edu.cdtu.web.sys_menu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.cdtu.web.sys_menu.entity.GoodsCategory;
import edu.cdtu.web.sys_menu.mapper.GoodsCategoryMapper;
import edu.cdtu.web.sys_menu.service.GoodsCategoryService;
import org.springframework.stereotype.Service;

@Service
public class GoodsCategoryServiceImpl extends ServiceImpl<GoodsCategoryMapper, GoodsCategory> implements GoodsCategoryService {
}
