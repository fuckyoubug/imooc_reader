package com.imooc.reader.controller;

import com.imooc.reader.entity.Evaluation;
import com.imooc.reader.entity.Member;
import com.imooc.reader.service.MemberService;
import com.imooc.reader.service.exception.BussinessException;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MemberController {

    @Resource
    private MemberService memberService;

    @GetMapping("/register.html")
    public ModelAndView showRegister()
    {
       // memberService.createMember();

        return new ModelAndView("/register");
    }


    @GetMapping("/login.html")
    public  ModelAndView showLogin()
    {
        return new ModelAndView("/login");
    }


    @PostMapping("/registe")
    @ResponseBody
    public Map registe(String vc, String username, String password, String nickname, HttpServletRequest request)
    {

        // session 存储的正确的验证码
        String VerifyCode = (String) request.getSession().getAttribute("kaptchaVerifyCode");

        Map<String,String> result = new HashMap<String,String>();

        //验证码比对
        if(vc==null || VerifyCode==null || !vc.equalsIgnoreCase(VerifyCode))
        {

            result.put("code","VC01");
            result.put("msg","验证码错误！");

        }
        else
        {
            try {
                Member member = memberService.createMember(username, password, nickname);
                result.put("code","0");
                result.put("msg","success");
            }
            catch (BussinessException ex)
            {
                ex.printStackTrace();
                result.put("code",ex.getCode());
                result.put("msg",ex.getMsg());

            }
        }
        return result;
    }

    @PostMapping("/check_login")
    @ResponseBody
    public Map checkLogin(String username, String password, String vc, HttpSession session){
        // session 存储的正确的验证码

        String VerifyCode = (String) session.getAttribute("kaptchaVerifyCode");

        Map<String,String> result = new HashMap<String,String>();

        //验证码比对
        if(vc==null || VerifyCode==null || !vc.equalsIgnoreCase(VerifyCode))
        {

            result.put("code","VC01");
            result.put("msg","验证码错误！");

        }
        else
        {
            try {
                Member member = memberService.checkLogin(username,password);
                session.setAttribute("loginMember",member);
                result.put("code","0");
                result.put("msg","success");

            }
            catch (BussinessException ex)
            {
                ex.printStackTrace();
                result.put("code",ex.getCode());
                result.put("msg",ex.getMsg());
            }
        }
        return result;
    }

    /**
     *
     * @param memberId
     * @param bookId
     * @param readState
     * @return
     */

    @PostMapping("/update_read_state")
    @ResponseBody // 返回 json 序列化字符
    public  Map updateReadState(Long memberId,Long bookId,Integer readState) {
        Map result = new HashMap();
        try {
            memberService.updateMemberReadState(memberId, bookId, readState);
            result.put("code","0");
            result.put("msg","success");

        } catch (BussinessException ex)
        {
            ex.printStackTrace();
            result.put("code",ex.getCode());
            result.put("msg",ex.getMsg());
        }
        return result;
    }

    @PostMapping("/evaluate")
    @ResponseBody
    public Map evaluate(Long memberId,Long bookId,Integer score,String content)
    {
        Map result = new HashMap();
        try {
            Evaluation evaluate = memberService.evaluate(memberId, bookId, score, content);
            result.put("code","0");
            result.put("msg","success");
            result.put("eva",evaluate);

        } catch (BussinessException ex)
        {
            ex.printStackTrace();
            result.put("code",ex.getCode());
            result.put("msg",ex.getMsg());
        }
        return result;
    }


    @PostMapping("/enjoy")
    @ResponseBody
    public Map enjoy(Long evaluationId)
    {
        Map result = new HashMap();
        try {
            Evaluation evaluate = memberService.enjoy(evaluationId);
            result.put("code","0");
            result.put("msg","success");
            result.put("evaluation",evaluate);

        } catch (BussinessException ex)
        {
            ex.printStackTrace();
            result.put("code",ex.getCode());
            result.put("msg",ex.getMsg());
        }
        return result;
    }
}
