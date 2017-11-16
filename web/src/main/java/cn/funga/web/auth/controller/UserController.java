package cn.funga.web.auth.controller;

import cn.funga.web.BaseController;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by funga on 2017/11/15.
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController{

    @RequestMapping("/register")
    public String register(String nickname,String username,String mobile,String addr){
        String aaa="hhhhh";
        JSONObject user = new JSONObject();
        user.put("昵称",nickname);
        user.put("登录名",username);
        user.put("电话",mobile);
        user.put("游戏",addr);
        return apiEnd(user);
    }

}
