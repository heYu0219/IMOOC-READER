package com.xiaoheyu.reader.entity;

import java.util.Date;

public class EBMDTO {
    private Date createTime;
    private String content;
    private String bookName;
    private String username;
    private String state;
    private Long evaluationId;

    public Long getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(Long evaluationId) {
        this.evaluationId = evaluationId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "EvaBookMember{" +
                "createTime=" + createTime +
                ", content='" + content + '\'' +
                ", bookName='" + bookName + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
