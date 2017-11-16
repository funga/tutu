package cn.funga.web.pay.controller;

import cn.funga.web.BaseController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by funga on 2017/11/10.
 */
@RestController
@RequestMapping("/pay")
public class PayController extends BaseController {

    @PostMapping("/wx")
    public String wxPay(String key,String wx,double amount){
        return apiEnd("成功使用微信支付"+amount+"元。");
    }

    @PostMapping("/ali")
    public String aliPay(String key,String wx,double amount){
        return apiEnd("成功使用微信支付"+amount +"元。");
    }
}
