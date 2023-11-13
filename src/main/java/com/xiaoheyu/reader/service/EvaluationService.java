package com.xiaoheyu.reader.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaoheyu.reader.entity.EBMDTO;
import com.xiaoheyu.reader.entity.Evaluation;

import java.util.List;

public interface EvaluationService {
    /**
     * 按图书编号查询有效短评
     * @param bookId 图书编号
     * @return 评论列表
     */
    public List<Evaluation> selectByBookId(Long bookId);

    public IPage<EBMDTO> getEvaluationPage(Integer page,Integer row);



}
