package cn.funga.web.client.controller;

import cn.funga.tutu.common.tool.HttpMan;
import cn.funga.web.BaseController;
import cn.funga.web.client.common.RestScanner;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Controller
public class ClientController extends BaseController{

    Logger logger = LoggerFactory.getLogger(ClientController.class);

    @GetMapping("/client")
    public String client(Model model)throws IOException,ClassNotFoundException{
        model.addAttribute("api", RestScanner.getRestApi());
        return "client/index";
    }

    @PostMapping("/execute")
    @ResponseBody
    public String execute(String url,String method,String params){
        if(StringUtils.isEmptyOrWhitespace(url)){
           return apiEnd(CODE_FAIL,"目标服务不存在");
        }
        Map<String,Object> map = Maps.newHashMap();

        if(!StringUtils.isEmptyOrWhitespace(params)){
            JSONObject json = JSONObject.parseObject(params);
            Set<String> keySet = json.keySet();
            for (String key : keySet){
                map.put(key,json.get(key));
            }
        }
        if(HttpMan.METHOD_POST.equals(method)){
            return apiEnd(HttpMan.doPost(url,map));
        }else if(HttpMan.METHOD_GET.equals(method)){
            return apiEnd(HttpMan.doGet(url,map));
        }else {
            return apiEnd(CODE_FAIL,"请求方式不支持");
        }
    }

}
