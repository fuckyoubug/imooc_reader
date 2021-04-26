package com.imooc.reader.controller;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.reader.entity.*;
import com.imooc.reader.service.BookService;
import com.imooc.reader.service.CategoryService;
import com.imooc.reader.service.EvaluationService;
import com.imooc.reader.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class BookController {


    @Resource
    private CategoryService categoryService;


    @Resource
    private BookService bookService;

    @Resource
    private EvaluationService evaluationService;

    @Resource
    private MemberService memberService;

    /**
     * 显示页面
     * @return
     */
    @GetMapping("/")//显示页面通常Get方法
    public ModelAndView showIndex(){
 
     ModelAndView mav =new ModelAndView("/index");
        List<Category> categoryList= categoryService.selectAll();
       mav.addObject("categoryList", categoryList);
        return  mav;
    }

    /**
     * 分页查询图书列表
     * @param p 页号
     * @return  分页对象
     */
    @GetMapping("/books")
    @ResponseBody   // 自动Json 序列化输出结果
    public IPage<Book> selectBook(Long categoryId,String order,Integer p)
    {
            if(p==null){
                p =  1;
            }
        IPage<Book> pageObject = bookService.paging(categoryId,order,p, 10);
        return pageObject;
    }

    @GetMapping("/book/{id}")
    public  ModelAndView showDetail(@PathVariable("id") Long id, HttpSession session)
    {
        Book book = bookService.slectById(id);
        List<Evaluation> evaluationslist = evaluationService.selectByBookId(id);

        ModelAndView mav = new ModelAndView("/detail");
        mav.addObject("book",book);
        mav.addObject("evaluationList",evaluationslist);

        Member loginMember = (Member) session.getAttribute("loginMember");

                if(loginMember!=null)
                {
                    MemberReadState memberReadState = memberService.selectMemberReadState(loginMember.getMemberId(), id);
                        mav.addObject("memberReadState",memberReadState);
                }

        return mav;
    }



}
