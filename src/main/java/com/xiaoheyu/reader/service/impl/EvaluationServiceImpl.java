package com.xiaoheyu.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoheyu.reader.entity.Book;
import com.xiaoheyu.reader.entity.Evaluation;
import com.xiaoheyu.reader.entity.Member;
import com.xiaoheyu.reader.mapper.BookMapper;
import com.xiaoheyu.reader.mapper.EvaluationMapper;
import com.xiaoheyu.reader.mapper.MemberMapper;
import com.xiaoheyu.reader.service.EvaluationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
@Service("evaluationService")
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
public class EvaluationServiceImpl implements EvaluationService {
    @Resource
    private EvaluationMapper evaluationMapper;
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private BookMapper bookMapper;
    @Override
    public List<Evaluation> selectByBookId(Long bookId) {
        QueryWrapper<Evaluation> queryWrapper=new QueryWrapper<>();
        Book book=bookMapper.selectById(bookId);
        queryWrapper.eq("book_id",bookId);//根据图书编号筛选
        queryWrapper.eq("state","enable");//有效短评，未被禁用的
        queryWrapper.orderByDesc("create_time");//根据创建时间降序排列
        List<Evaluation> evaluationList = evaluationMapper.selectList(queryWrapper);
        for (Evaluation eva:evaluationList){//获取与评论关联的图书和会员的信息
            Member member=memberMapper.selectById(eva.getMemberId());//查询与该条评论关联的会员信息
            eva.setMember(member);
            eva.setBook(book);
        }
        return evaluationList;
    }
}
