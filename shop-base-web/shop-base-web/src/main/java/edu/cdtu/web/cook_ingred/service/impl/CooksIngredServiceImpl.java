package edu.cdtu.web.cook_ingred.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.cdtu.web.cook_ingred.entity.CookIngred;
import edu.cdtu.web.cook_ingred.entity.CookIngredParm;
import edu.cdtu.web.cook_ingred.mapper.CooksIngredMapper;
import edu.cdtu.web.cook_ingred.service.CooksIngredService;
import edu.cdtu.web.goods.entity.Goods;
import edu.cdtu.web.ingredients_category.entity.IngredientsCategory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CooksIngredServiceImpl extends ServiceImpl<CooksIngredMapper, CookIngred> implements CooksIngredService{

    @Override
    @Transactional
    public boolean saveIngred(CookIngredParm parm){
        // 1. 删除与给定 cooksId 关联的所有 CookIngred 记录
        QueryWrapper<CookIngred> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cooks_id", parm.getCooksId()); // 假设 cooksId 是数据库中的列名
        this.baseMapper.delete(queryWrapper);

        // 2. 插入新的记录列表（这里假设你需要自定义插入方法或循环插入）
        // 假设 baseMapper 有一个自定义的批量插入方法 insertList
        if (this.baseMapper.saveIngred(parm.getCooksId(),parm.getList())) { // 假设 getList() 返回的是 CookIngred 对象的列表
            return true;
        }
        return false;
    }


    @Override
    public List<IngredientsCategory> getIngredBycookId(List<Long> ingredId) {
        return this.baseMapper.getIngredBycookId(ingredId);
    }

    @Override
    public List<Goods> findCooksByIngredIds(List<Long> ingredId) {
        return this.baseMapper.findCooksByIngredIds(ingredId);
    }


}
