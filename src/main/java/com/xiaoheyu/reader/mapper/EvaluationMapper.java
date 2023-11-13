package com.xiaoheyu.reader.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaoheyu.reader.entity.EBMDTO;
import com.xiaoheyu.reader.entity.Evaluation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EvaluationMapper extends BaseMapper<Evaluation> {
    List<EBMDTO> selectEvaluationPage();
}
