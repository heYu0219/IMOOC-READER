package com.xiaoheyu.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoheyu.reader.entity.Category;
import com.xiaoheyu.reader.mapper.CategoryMapper;
import com.xiaoheyu.reader.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
@Service("categoryService")//bean id为接口的名字
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)//默认设置不需要开启事务
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;
    /**
     * 查询所有图书分类
     * @return 图书分类list
     */
    public List<Category> selectAll() {
        //查询所有数据不需要任何条件，直接传入一个queryWrapper
        return categoryMapper.selectList(new QueryWrapper<Category>());
    }
}
