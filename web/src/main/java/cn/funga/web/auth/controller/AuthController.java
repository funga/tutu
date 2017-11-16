package cn.funga.web.auth.controller;

import cn.funga.web.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController{

    @PostMapping("/login")
    public String login(String username,String passwd){
        return apiEnd(username);
    }

    @RequestMapping("/logout")
    public String login(){
        return apiEnd("登出成功");
    }

}
