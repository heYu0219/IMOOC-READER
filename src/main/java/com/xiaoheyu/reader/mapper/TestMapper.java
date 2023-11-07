package com.xiaoheyu.reader.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoheyu.reader.entity.Test;

public interface TestMapper extends BaseMapper<Test> {
    public void insertSample();//自行创建的函数也可以使用
}
