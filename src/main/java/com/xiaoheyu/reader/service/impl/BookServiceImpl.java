package com.xiaoheyu.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoheyu.reader.entity.Book;
import com.xiaoheyu.reader.mapper.BookMapper;
import com.xiaoheyu.reader.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;

@Service("bookService")
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
public class BookServiceImpl implements BookService {
    @Resource
    private BookMapper bookMapper;
    @Override
    //分页查询图书
    public IPage<Book> paging(Long categoryId,String order,Integer page, int rows) {
        Page<Book> p=new Page<>(page,rows);//页号和行数
        QueryWrapper<Book> queryWrapper=new QueryWrapper<>();
        if(categoryId!=null&&categoryId!=-1){//如果传来的分类编号有效
            queryWrapper.eq("category_id",categoryId);//根据category_id筛选数据
        }
        if(order!=null){//如果排序条件不为空
            if(order.equals("quantity")){//按评价数量
                queryWrapper.orderByDesc("evaluation_quantity");//按照evaluation_quantity字段降序排列
            }
            if(order.equals("score")){//按照评分降序排列
                queryWrapper.orderByDesc("evaluation_score");
            }
        }
        IPage<Book> pageObject=bookMapper.selectPage(p,queryWrapper);
        return pageObject;
    }

    @Override
    public Book selectById(Long bookId) {
        return bookMapper.selectById(bookId);
    }
}
