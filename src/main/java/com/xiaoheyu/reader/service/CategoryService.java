package com.xiaoheyu.reader.service;

import com.xiaoheyu.reader.entity.Category;
import com.xiaoheyu.reader.mapper.CategoryMapper;

import java.util.List;

public interface CategoryService {
    public List<Category> selectAll();
}
