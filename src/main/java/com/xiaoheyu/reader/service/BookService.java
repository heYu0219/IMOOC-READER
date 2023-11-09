package com.xiaoheyu.reader.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaoheyu.reader.entity.Book;

/**图书服务*/
public interface BookService {
    /**分页查询图书
     * @param categoryId 分类编号
     * @param order 排序方式
     * @param page 要查询的页号
     * @param rows 每页的记录数
     * @return 分页对象
     */
    public IPage<Book> paging(Long categoryId,String order,Integer page,int rows);
}
