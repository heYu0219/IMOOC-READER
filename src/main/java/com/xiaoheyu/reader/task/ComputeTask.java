package com.xiaoheyu.reader.task;

import com.xiaoheyu.reader.service.BookService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

//完成自动计算任务
@Component
public class ComputeTask {
    @Resource
    private BookService bookService;
    //任务调度（定时任务）
    @Scheduled(cron = "0 * * * * ?")//秒 分 时 日 月 星期(忽略)每分钟0秒时自动执行以下的定时任务
    public void updateEvaluation(){
        bookService.updateEvaluation();
    }
}
