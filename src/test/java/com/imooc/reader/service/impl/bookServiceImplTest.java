package com.imooc.reader.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.reader.entity.Book;
import com.imooc.reader.entity.Evaluation;
import com.imooc.reader.service.BookService;
import com.imooc.reader.service.EvaluationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext1.xml"})
public class bookServiceImplTest {
    @Resource
    private BookService bookService;

    @Resource
    private EvaluationService evaluationService;

    @Test
    public void paging() {
        IPage<Book> pageObject = bookService.paging(2l,"quantity",2, 10);

        List<Book> records = pageObject.getRecords();// list 集合 具体数据
        for(Book b:records)
        {
            System.out.println(b.getBookId()+":"+b.getBookName()+b.getCategoryId());
        }
        System.out.println("总页数"+pageObject.getPages());
        System.out.println("总记录数"+pageObject.getTotal());
    }


    @Test
    public  void bookSlect()
    {
        Book book = bookService.slectById(10l);
        System.out.println(book);
    }

    @Test
    public  void evaluationSelect()
    {
        List<Evaluation> evaluations = evaluationService.selectByBookId(10l);
        System.out.println(evaluations);
    }


}