package com.xiaoheyu.reader.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoheyu.reader.entity.Book;
/*图书类mapper*/
public interface BookMapper extends BaseMapper<Book> {
    /**
     * 更新图书评分
     */
    public void updateEvaluation();
}

