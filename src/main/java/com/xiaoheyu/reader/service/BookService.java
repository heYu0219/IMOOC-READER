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

    /**
     * 根据编号查询图书对象
     * @param bookId 图书编号
     * @return 图书对象
     */
    public Book selectById(Long bookId);

    /**
     * 更新图书的评分
     */
    public void updateEvaluation();

    /**
     * 创建新的图书
     */
    public Book createBook(Book book);

    /**
     * 更新图书
     * @param book 新图书数据
     * @return 更新后的图书数据
     */
    public Book updateBook(Book book);

    /**
     * 删除图书
     * @param bookId 图书编号
     */
    public void deleteBook(Long bookId);
}
