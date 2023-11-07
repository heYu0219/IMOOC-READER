package com.xiaoheyu.reader;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoheyu.reader.entity.Test;
import com.xiaoheyu.reader.mapper.TestMapper;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class MyBatisPlusTest {
    @Resource
    private TestMapper testMapper;

    @org.junit.Test
    public void testInsert(){
        Test test=new Test();
        test.setContent("MyBatis Plus测试");
        testMapper.insert(test);
    }
    @org.junit.Test
    public void testUpdate(){
        Test test=testMapper.selectById(42);
        test.setContent("测试1");
        testMapper.updateById(test);
    }
    @org.junit.Test
    public void testDelete(){
        testMapper.deleteById(42);
    }
    @org.junit.Test
    public void testSelect(){
        QueryWrapper<Test> queryWrapper=new QueryWrapper<Test>();
        queryWrapper.eq("id",40);//查询所有id=40的test对象
        queryWrapper.gt("id",30);//写多个子句时，sql语句使用and进行连接
        List<Test> testList = testMapper.selectList(queryWrapper);//返回一个集合
        System.out.println(testList.get(0));
    }
}
