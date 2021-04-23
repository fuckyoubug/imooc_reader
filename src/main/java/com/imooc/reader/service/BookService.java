package com.imooc.reader.service;

import com.imooc.reader.entity.Book;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.transaction.annotation.Transactional;

public interface BookService {
    /**
     * 分页查询图书
     * @param categoryId 分类页号
     *  @param order 排序规则
     * @param page 页号
     * @param rows 每页记录数
     *
     * @return 分页对象
     */
    public IPage<Book> paging(Long categoryId,String order,Integer page,Integer rows);

    /**
     * 根据图书编号查找图书对象
     * @param bookId
     * @return Book
     */
    public  Book slectById(Long bookId);


    /**
     * 更新图书评分/评价数量
     *
     */
    public void updateEvaluation();


    /**
     *创建新的图书
     * @param book
     * @return
     */
    public  Book createBook(Book book);

    /**
     * 更新图书
     * @param book
     * @return
     */
    public  Book updateBook(Book book);

    /**
     * 删除 图书即相关数据
     * @param bookId
     */

    public void deleteBook(Long bookId);

}
