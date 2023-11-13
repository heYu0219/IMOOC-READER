package com.xiaoheyu.reader.controller.management;

import com.xiaoheyu.reader.entity.User;
import com.xiaoheyu.reader.service.UserService;
import com.xiaoheyu.reader.service.exception.BussinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 后台管理系统控制器
 */
@Controller
@RequestMapping("/management")
public class ManagementController {
    @Resource
    private UserService userService;
    @GetMapping("/index.html")
    public ModelAndView showIndex(){
        return new ModelAndView("/management/index");
    }

    @GetMapping("/login.html")
    public ModelAndView showLogin(){
        return new ModelAndView("/management/login");
    }

    @PostMapping("/check_login")
    @ResponseBody
    public Map checkLogin(String username, String password, HttpSession session){
        Map map=new HashMap();
        try {
            User user=userService.checkLogin(username,password);
            session.setAttribute("login_user",user);
            map.put("code",0);
            map.put("msg","success");
            map.put("redirect_url","/management/index.html");
        } catch (BussinessException e) {
            e.printStackTrace();
            map.put("code",e.getCode());
            map.put("msg",e.getMsg());
        }
        return map;
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        response.sendRedirect("/management/login.html");
    }
}
