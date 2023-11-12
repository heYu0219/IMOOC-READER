package com.xiaoheyu.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoheyu.reader.entity.Book;
import com.xiaoheyu.reader.entity.Evaluation;
import com.xiaoheyu.reader.entity.MemberReadState;
import com.xiaoheyu.reader.mapper.BookMapper;
import com.xiaoheyu.reader.mapper.EvaluationMapper;
import com.xiaoheyu.reader.mapper.MemberReadStateMapper;
import com.xiaoheyu.reader.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Service("bookService")
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
public class BookServiceImpl implements BookService {
    @Resource
    private BookMapper bookMapper;
    @Resource
    private EvaluationMapper evaluationMapper;
    @Resource
    private MemberReadStateMapper memberReadStateMapper;
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

    @Override
    /**
     * 更新图书的评分
     */
    @Transactional
    public void updateEvaluation() {
        bookMapper.updateEvaluation();
    }

    /**
     * 创建新的图书
     *
     * @param book
     */
    @Override
    @Transactional
    public Book createBook(Book book) {
        bookMapper.insert(book);
        return book;//mybatis-plus会自动对图书编号进行回填
    }

    /**
     * 更新图书
     *
     * @param book 新图书数据
     * @return 更新后的图书数据
     */
    @Override
    @Transactional
    public Book updateBook(Book book) {
        bookMapper.updateById(book);
        return book;
    }

    /**
     * 删除图书:包括评论 阅读状态 图书表
     *
     * @param bookId 图书编号
     */
    @Override
    @Transactional
    public void deleteBook(Long bookId) {
        bookMapper.deleteById(bookId);
        QueryWrapper<Evaluation> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("book_id",bookId);
        evaluationMapper.delete(queryWrapper);
        QueryWrapper<MemberReadState> queryWrapper1=new QueryWrapper<>();
        queryWrapper1.eq("book_id",bookId);
        memberReadStateMapper.delete(queryWrapper1);
    }
}
