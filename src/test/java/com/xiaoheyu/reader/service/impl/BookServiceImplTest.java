package com.xiaoheyu.reader.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaoheyu.reader.entity.Book;
import com.xiaoheyu.reader.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class BookServiceImplTest {
    @Resource
    private BookService bookService;

    @Test
    public void paging() {
        IPage<Book> pageObject = bookService.paging(2l,"quantity",2, 10);
        List<Book> booksList=pageObject.getRecords();//获得当前页的具体数据
        for(Book b:booksList){
            System.out.println(b.getBookId()+":"+b.getBookName());
        }
        System.out.println("总页数为:"+pageObject.getPages());
        System.out.println("总记录数为:"+pageObject.getTotal());
    }
}