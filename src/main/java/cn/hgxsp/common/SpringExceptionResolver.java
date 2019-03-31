package cn.hgxsp.common;

import cn.hgxsp.exception.HgxspException;
import cn.hgxsp.exception.ParamException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * DESC：接口请求全局异常处理类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/3/24
 * Time : 11:34
 */
@Slf4j
public class SpringExceptionResolver implements HandlerExceptionResolver {


    @Override
    public ModelAndView resolveException(HttpServletRequest req, HttpServletResponse res, Object o, Exception e) {

        String url = req.getRequestURL().toString() ;
        ModelAndView mv ;

        String defauleMsg = "System error " ;

        //这里设定，所有数据返回请求都以.json结尾。 所有返回页面请求都以.page结尾
        JsonData jsonData = null ;
        if(url.endsWith(".json")){
            if(e instanceof HgxspException || e instanceof ParamException){
                jsonData =  JsonData.fail(e.getMessage());
            }else{
                log.error("unknow errer ;url = " + url , e);
                jsonData =  JsonData.fail(defauleMsg);
            }
            //jsonView 和 spring-servlet中的<bean id="jsonView" class="org.springframework.web.servlet.view.json.MappingJackson2JsonView" />对应
            mv = new ModelAndView("jsonView" ,jsonData.toMap() );
        }else if(url.endsWith(".page")){
            log.error("unknow errer pageException ;url = " + url , e);
            jsonData = JsonData.fail(defauleMsg);
            mv = new ModelAndView("exception" ,jsonData.toMap() );
        }else{
            log.error("unknow errer ;url = " + url , e);
            jsonData =  JsonData.fail(defauleMsg);
            mv = new ModelAndView("jsonView" ,jsonData.toMap() );
        }

        return mv;
    }
}
