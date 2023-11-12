package com.xiaoheyu.reader.controller.management;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaoheyu.reader.entity.Book;
import com.xiaoheyu.reader.service.BookService;
import com.xiaoheyu.reader.service.exception.BussinessException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/management/book")//后台管理页面的标识
public class MBookController {
    @Resource
    private BookService bookService;

    @GetMapping("/index.html")//映射地址
    public ModelAndView showBook(){
        return new ModelAndView("/management/book");//展示book页面
    }

    /**
     * wangEditor文件上传
     * @param file 上传的文件
     * @param request 原生请求对象
     * @return
     * @throws IOException
     */
    @ResponseBody
    @PostMapping("/upload")
    //@RequestParam("img")必须设置为img
    public Map upload(@RequestParam("img") MultipartFile file, HttpServletRequest request) throws IOException {
        //在运行时获得的路径是out下的imooc_reader_Web_exploded
        //得到上传目录
        String uploadPath=request.getServletContext().getResource("/").getPath()+"/upload/";
        //文件名
        String fileName=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        //原始文件的扩展名
        String suffix=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //保存文件到upload目录：目录+文件名+扩展名
        file.transferTo(new File(uploadPath+fileName+suffix));
        Map result=new HashMap();
        result.put("errno",0);
        result.put("data",new String[]{"/upload/"+fileName+suffix});//文件地址
        return result;
    }

    @ResponseBody
    @PostMapping("/create")
    public Map createBook(Book book){
        Map result=new HashMap();
        try {
            book.setEvaluationQuantity(0);
            book.setEvaluationScore(0f);
            Document doc = Jsoup.parse(book.getDescription());//解析图书详情
            Element img=doc.select("img").first();//获取图书详情第一图的元素对象
            String cover=img.attr("src");//提取img元素的src属性值
            book.setCover(cover);//来自于description描述的第一幅图
            bookService.createBook(book);
            result.put("code",0);
            result.put("msg","success");
        } catch (BussinessException e) {
            e.printStackTrace();
            result.put("code",e.getCode());
            result.put("msg",e.getMsg());
        }
        return result;
    }

    @GetMapping("list")
    @ResponseBody
    public Map list(Integer page,Integer limit){
        if(page==null){
            page=1;
        }
        if(limit==null){
            limit=10;
        }
        //后台查看图书列表时不需要分类和排序
        IPage<Book> pageObject = bookService.paging(null, null, page, limit);
        //按照layui的数据返回格式返回
        Map result=new HashMap();
        result.put("code",0);
        result.put("msg","success");
        result.put("data",pageObject.getRecords());//当前分页的数据
        result.put("count",pageObject.getTotal());//未分页时记录总数
        return result;
    }
    @GetMapping("/id/{id}")
    @ResponseBody
    public Map selectById(@PathVariable("id") Long bookId){
        Map result=new HashMap();
        Book book=bookService.selectById(bookId);
        result.put("code",0);
        result.put("msg","success");
        result.put("data",book);
        return result;
    }

    @PostMapping("/update")
    @ResponseBody
    public Map updateBook(Book book){
        Map result=new HashMap();
        try {
            //为避免产生数据混乱，先根据前台传入的book对象查询出数据库中原始的book进行更改
            Book rawBook=bookService.selectById(book.getBookId());
            rawBook.setBookName(book.getBookName());
            rawBook.setSubTitle(book.getSubTitle());
            rawBook.setAuthor(book.getAuthor());
            rawBook.setCategoryId(book.getCategoryId());
            rawBook.setDescription(book.getDescription());
            Document document = Jsoup.parse(book.getDescription());
            String cover =document.select("img").first().attr("src");
            rawBook.setCover(cover);
            bookService.updateBook(rawBook);
            result.put("code",0);
            result.put("msg","success");
        } catch (BussinessException e) {
            e.printStackTrace();
            result.put("code",e.getMsg());
            result.put("msg",e.getMsg());
        }
        return result;
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public Map deleteBook(@PathVariable("id") Long bookId){
        Map result=new HashMap();
        try {
            bookService.deleteBook(bookId);
            result.put("code",0);
            result.put("msg","success");
        } catch (BussinessException e) {
            e.printStackTrace();
            result.put("code",e.getCode());
            result.put("msg",e.getMsg());
        }
        return result;
    }
}
