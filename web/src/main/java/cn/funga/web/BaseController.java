package cn.funga.web;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by funga on 2017/11/12.
 */
public class BaseController {

    protected static final int CODE_SUCCESS = 100;  //  成功
    protected static final int CODE_EXCEPTION = 101; // 代码发生异常
    protected static final int CODE_FAIL = 102;// 业务操作失败

    protected static final String MSG_SUCCESS="操作成功";
    protected static final String MSG_ERROR= "未知错误";

    protected String apiEnd(int code,String msg,Object data){
        JSONObject json = new JSONObject();
        json.put("code",code);
        json.put("msg",msg);
        json.put("data",data);
        return json.toJSONString();
    }

    protected String apiEnd(Object data){
        return apiEnd(CODE_SUCCESS,MSG_SUCCESS,data);
    }

    protected String apiEnd(int code,String msg){
        return apiEnd(code,msg,null);
    }

    protected String apiEnd(int code){
        return apiEnd(code,MSG_ERROR,null);
    }

}
