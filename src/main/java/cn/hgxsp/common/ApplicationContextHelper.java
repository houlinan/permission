package cn.hgxsp.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * DESC：获取上下文工具类(从spring的上下文中取出一个bean)
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/3/25
 * Time : 22:09
 */
@Component("applicationContextHelper")
public class ApplicationContextHelper implements ApplicationContextAware {

    //创建全局的applicationcontext
    private static ApplicationContext applicationContext;

    //初始化赋值
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context ;

    }

    public static <T> T popBean(Class<T> clazz){
        if(ObjectUtils.isEmpty(clazz)) return null ;

        return applicationContext.getBean(clazz) ;
    }

    public static <T> T popBean(String name , Class<T> clazz){
        if(ObjectUtils.isEmpty(clazz)) return null ;
        return applicationContext.getBean(name , clazz) ;
    }



}
