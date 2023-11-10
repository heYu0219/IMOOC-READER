package com.xiaoheyu.reader.controller;

import com.google.code.kaptcha.Producer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class KaptchaController {
    @Resource
    private Producer kaptchaProducer;
    @GetMapping("verify_code")
    public void createVerifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //响应立即过期
        response.setDateHeader("Expires",0);//设置过期时间
        //不缓存任何图片 通知浏览器不对本次验证码的图片做任何缓存
        response.setHeader("Cache-Control","no-store,no-cache,must-revalidate");
        response.setHeader("Cache-Control","post-check=0,pre-check=0");
        response.setHeader("Pragma","no-cache");
        response.setContentType("image/png");//设置返回的数据为图片
        //生成验证码字符文本
        String verifyCode=kaptchaProducer.createText();
        request.getSession().setAttribute("kaptchaVerifyCode",verifyCode);//将验证码存储到session中
        System.out.println(request.getSession().getAttribute("kaptchaVerifyCode"));
        BufferedImage image=kaptchaProducer.createImage(verifyCode);//创建验证码图片，返回一个缓存图片
        ServletOutputStream out= response.getOutputStream();//输出二进制图片
        ImageIO.write(image,"png",out);//将图片从服务器端通过响应发送给 客户端浏览器
        out.flush();
        out.close();
    }
}
