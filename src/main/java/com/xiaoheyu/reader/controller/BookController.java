package com.xiaoheyu.reader.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaoheyu.reader.entity.*;
import com.xiaoheyu.reader.service.BookService;
import com.xiaoheyu.reader.service.CategoryService;
import com.xiaoheyu.reader.service.EvaluationService;
import com.xiaoheyu.reader.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class BookController {//实现URL与方法之间的绑定
    @Resource
    private CategoryService categoryService;
    @Resource
    private BookService bookService;
    @Resource
    private EvaluationService evaluationService;
    @Resource
    private MemberService memberService;
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
    public IPage<Book> selectBook(Long categoryId,String order,Integer p){
        if(p ==null){//容错处理，如果传来的页号为空，则默认查询第一页数据
            p =1;
        }
        IPage<Book> pageObject=bookService.paging(categoryId,order,p,10);//传入页号和固定每页显示十条数据
        return pageObject;
    }

    @GetMapping("book/{id}")//使用路径变量获取存放在url中的图书编号
    public  ModelAndView showDetail(@PathVariable("id") Long id, HttpSession session){
        Book book=bookService.selectById(id);
        List<Evaluation> evaluationList=evaluationService.selectByBookId(id);
        Member member=(Member)session.getAttribute("loginMember");
        ModelAndView mav=new ModelAndView("/detail");//跳转到detail页面
        if(member!=null){
            //获取会员阅读状态
            MemberReadState memberReadState=memberService.selectMemberReadState(member.getMemberId(),id);
            mav.addObject("memberReadState",memberReadState);
        }
        mav.addObject("book",book);//将查询到的图书对象载入modelAndView中
        mav.addObject("evaluationList",evaluationList);
        return mav;
    }


}
