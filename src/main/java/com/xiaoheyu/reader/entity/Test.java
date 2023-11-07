package com.xiaoheyu.reader.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("test")//说明实体对应哪一张表
public class Test {
    @TableField("id")//说明实体对应哪个字段
    @TableId(type = IdType.AUTO)//利用数据库底层的自增组件完成数据插入工作
    private Integer id;
    //如果字段名与属性名相同或符合驼峰命名转换规则，则TableField注解可以省略
    @TableField("content")
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
