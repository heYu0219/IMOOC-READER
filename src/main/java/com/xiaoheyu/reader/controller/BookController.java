package com.xiaoheyu.reader.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaoheyu.reader.entity.Book;
import com.xiaoheyu.reader.entity.Category;
import com.xiaoheyu.reader.service.BookService;
import com.xiaoheyu.reader.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class BookController {//实现URL与方法之间的绑定
    @Resource
    private CategoryService categoryService;
    @Resource
    private BookService bookService;
    /*显示首页*/
    @GetMapping("/")//显示页面通常使用get方法
    public ModelAndView showIndex(){
        ModelAndView mav=new ModelAndView("/index");
        List<Category> categoryList=categoryService.selectAll();
        mav.addObject("categoryList",categoryList);
        return mav;
    }

    /**
     * 分页查询图书列表
     * @param p 页号
     * @return 分页对象
     * 实现服务器端的数据提供
     */
    @GetMapping("/books")
    @ResponseBody //使用Spring MVC对IPage对象实现JSON序列化输出
    public IPage<Book> selectBook(Integer p){
        if(p ==null){//容错处理，如果传来的页号为空，则默认查询第一页数据
            p =1;
        }
        IPage<Book> pageObject=bookService.paging(p,10);//传入页号和固定每页显示十条数据
        return pageObject;
    }
}
