package edu.cdtu.web.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.cdtu.web.goods.entity.Goods;
import edu.cdtu.web.goods.mapper.GoodsMapper;
import edu.cdtu.web.goods.service.GoodsService;
import org.springframework.stereotype.Service;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
}
