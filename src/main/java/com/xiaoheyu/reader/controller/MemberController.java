package com.xiaoheyu.reader.controller;

import com.mysql.cj.PreparedQuery;
import com.xiaoheyu.reader.entity.Member;
import com.xiaoheyu.reader.service.MemberService;
import com.xiaoheyu.reader.service.exception.BussinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
    public ModelAndView showRegister(){
        return new ModelAndView("/register");
    }

    @GetMapping("/login.html")
    public ModelAndView showLogin(){
        return new ModelAndView("/login");
    }

    @PostMapping("/registe")
    @ResponseBody
    //如果需要请求或响应，直接将相应的对象放入参数列表中就可以了
    public Map registe(String vc, String username, String password, String nickname, HttpServletRequest request){
        Map result=new HashMap();
        //正确的验证码
        String verifyCode=(String) request.getSession().getAttribute("kaptchaVerifyCode");
        //验证码对比
        if(vc==null || verifyCode==null ||!vc.equalsIgnoreCase(verifyCode)){
            result.put("code","vc01");
            result.put("msg","验证码错误");
        }else{
            try {
                memberService.createMember(username,password,nickname);
                result.put("code","0");
                result.put("msg","success");
            } catch (BussinessException e) {
                e.printStackTrace();
                result.put("code",e.getCode());
                result.put("msg",e.getMsg());
            }
        }
        return result;
    }

    @PostMapping("/check_login")
    @ResponseBody
    public Map checkLogin(String vc, String username, String password, HttpSession session){
        Map result=new HashMap();
        //正确的验证码
        String verifyCode=(String) session.getAttribute("kaptchaVerifyCode");
        //验证码对比
        if(vc==null || verifyCode==null ||!vc.equalsIgnoreCase(verifyCode)){
            result.put("code","vc01");
            result.put("msg","验证码错误");
        }else{
            try {
                Member member=memberService.checkLogin(username,password);
                session.setAttribute("loginMember",member);
                result.put("code","0");
                result.put("msg","success");
            } catch (BussinessException e) {
                e.printStackTrace();
                result.put("code",e.getCode());
                result.put("msg",e.getMsg());

            }
        }
        return result;
    }

    @PostMapping("/update_read_state")
    @ResponseBody
    public Map updateReadState(Long memberId, Long bookId, Integer readState){
        Map result=new HashMap();
        try {
            memberService.updateMemberReadState(memberId,bookId,readState);
            result.put("code","0");
            result.put("msg","success");
        } catch (BussinessException e) {
            e.printStackTrace();
            result.put("code",e.getCode());
            result.put("msg",e.getMsg());
        }
        return result;
    }
}
