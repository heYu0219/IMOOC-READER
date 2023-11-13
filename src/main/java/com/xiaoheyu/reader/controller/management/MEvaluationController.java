package com.xiaoheyu.reader.controller.management;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoheyu.reader.entity.EBMDTO;
import com.xiaoheyu.reader.entity.Evaluation;
import com.xiaoheyu.reader.mapper.EvaluationMapper;
import com.xiaoheyu.reader.service.EvaluationService;
import com.xiaoheyu.reader.service.exception.BussinessException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/management/evaluation")
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
public class MEvaluationController {
    @Resource
    private EvaluationMapper evaluationMapper;
    @Resource
    private EvaluationService evaluationService;

    @GetMapping("/eva.html")
    public ModelAndView showEvaluation(){
        return new ModelAndView("/management/evaluation");
    }
    @GetMapping("/disable")
    @ResponseBody
    @Transactional
    public Map disableEvaluation(Long evaluationId,String reason){
        Map result=new HashMap();
        try {
            Evaluation evaluation=evaluationMapper.selectById(evaluationId);
            evaluation.setState("disable");
            evaluation.setDisableReason(reason);
            evaluationMapper.updateById(evaluation);
            result.put("code",0);
            result.put("msg","success");
        } catch (BussinessException e) {
            e.printStackTrace();
            result.put("code",e.getCode());
            result.put("msg",e.getMsg());
        }
        return result;
    }

    @GetMapping("/list")
    @ResponseBody
    public Map list(Integer page,Integer limit){
        if(page==null){
            page=2;
        }
        if(limit==null){
            limit=20;
        }
        //后台查看图书列表时不需要分类和排序
        IPage<EBMDTO> ebmdtoiPage = evaluationService.getEvaluationPage(page, limit);
        //按照layui的数据返回格式返回
        Map result=new HashMap();
        result.put("code",0);
        result.put("msg","success");
        result.put("data",ebmdtoiPage.getRecords());//当前分页的数据
        result.put("count",ebmdtoiPage.getTotal());//未分页时记录总数
        return result;
    }

}
