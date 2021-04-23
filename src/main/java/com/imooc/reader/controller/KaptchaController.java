package com.imooc.reader.controller;

import com.google.code.kaptcha.Producer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class KaptchaController {

@Resource
    private Producer kaptchaProducer;
    @GetMapping("/verify_code")
    public  void createVerifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //相应立即过期
        response.setDateHeader("Expires",0);
        //不缓存任何图片数据
        response.setHeader("Cache-Control","no-store,no-cache,must-revalidate");
        response.setHeader("Cache-Control","post-check=0,pre-check=0");
        response.setHeader("Pragma","no-cache");
        response.setContentType("image/png");

        //生成验证码字符文本
        String verifyCode = kaptchaProducer.createText();
        //保存到当前回话当中
        request.getSession().setAttribute("kaptchaVerifyCode",verifyCode);

        System.out.println(request.getSession().getAttribute("kaptchaVerifyCode"));

        BufferedImage image = kaptchaProducer.createImage(verifyCode);//创建验证码图片

        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image,"png",outputStream); //放入输出流中   ImageIO 图片输入输出功能
        outputStream.flush();//立即输出
        outputStream.close();//关闭输出流
    }

}
