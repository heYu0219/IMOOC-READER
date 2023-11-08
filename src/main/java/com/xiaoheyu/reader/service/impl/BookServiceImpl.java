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
    public IPage<Book> paging(Integer page, int rows) {
        Page<Book> p=new Page<>(page,rows);//页号和行数
        QueryWrapper<Book> queryWrapper=new QueryWrapper<>();
        IPage<Book> pageObject=bookMapper.selectPage(p,queryWrapper);
        return pageObject;
    }
}
