package cn.funga.web.client.common;

import cn.funga.tutu.common.tool.ReflectSnake;
import com.alibaba.fastjson.JSONObject;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 扫描所有的Controller
 */
public class RestScanner {

    public static  String getRestApi()throws IOException,ClassNotFoundException{
        ResourcePatternResolver rpr = new PathMatchingResourcePatternResolver();
        Resource[] res = rpr.getResources("classpath:cn/funga/web/*/controller/*"); // classpath*:带*号会找jar中的class
        JSONObject json = new JSONObject();
        if(res != null && res.length > 0){
            for (Resource re : res){
                String className = re.getURL().getPath();
                className = className.split("(classes/)|(!/)")[1];
                className = className.replace("/", ".").replace(".class", "");
                Class c = Class.forName(className);
                if(c.isAnnotationPresent(RestController.class)){
                    String uri = "";
                    // class
                    if(c.isAnnotationPresent(RequestMapping.class)){
                        RequestMapping requestMapping = (RequestMapping)c.getAnnotation(RequestMapping.class);
                        String[] value = requestMapping.value();
                        if(value.length > 0){
                            uri = requestMapping.value()[0];
                        }
                    }
                    JSONObject module = new JSONObject();
                    // method
                    Method[] methods = c.getMethods();
                    if( methods!= null && methods.length > 0){
                        for(Method method : methods){
                            JSONObject api = new JSONObject();
                            if(method.isAnnotationPresent(PostMapping.class)){
                                PostMapping postMapping = method.getAnnotation(PostMapping.class);
                                api.put("method","POST");
                                String[] value = postMapping.value();
                                String curi = uri;
                                if(value.length > 0){
                                    curi = curi + postMapping.value()[0];
                                }
                                api.put("param",ReflectSnake.getMethodParamNames(method));
                                module.put(curi,api);

                            }else if(method.isAnnotationPresent(GetMapping.class)){
                                GetMapping getMapping = method.getAnnotation(GetMapping.class);
                                api.put("method","GET");
                                String[] value = getMapping.value();
                                String curi = uri;
                                if(value.length > 0){
                                    curi = curi + getMapping.value()[0];
                                }
                                api.put("param",ReflectSnake.getMethodParamNames(method));
                                module.put(curi,api);

                            }else if(method.isAnnotationPresent(RequestMapping.class)){
                                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                                api.put("method","");
                                String[] value = requestMapping.value();
                                String curi = uri;
                                if(value.length > 0){
                                    curi = curi + requestMapping.value()[0];
                                }
                                api.put("param", ReflectSnake.getMethodParamNames(method));
                                module.put(curi,api);
                            }else {
                                continue;
                            }
                        }
                    }

                    json.put(uri,module);
                }

            }
        }
        return json.toJSONString();
    }


}
