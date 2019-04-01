package cn.hgxsp.controller;

import cn.hgxsp.model.SysUser;
import cn.hgxsp.service.SysUserService;
import cn.hgxsp.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * DESC：用户相关controller
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/4/1
 * Time : 19:59
 */
@RequestMapping("/user")
public class UserController {

    public final static String USER_SESSION_KEY = "user";

    @Resource
    SysUserService sysUserService ;

    @RequestMapping("/login.page")
    public void login(HttpServletRequest req , HttpServletResponse res)throws IOException , ServletException {
        String userName = req.getParameter("username") ;
        String password = req.getParameter("password") ;

        SysUser user = sysUserService.findByKeyWord(userName);
        String errorMsg = "";
        String retAddress = req.getParameter("ret") ;

        //判断校验逻辑

        if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)){
            errorMsg = "传入的用户名称或密码为空";
        }else if(StringUtils.isBlank(password)){
            errorMsg = "传入的密码格式不正确或为空";
        }else if(ObjectUtils.isEmpty(user)){
            errorMsg = "该用户没有找到";
        }else if(!user.getPassword().equals(MD5Util.encrypt(password))){
            errorMsg = "用户名或密码错误";
        }else if(user.getStatus() != 1 ){
            errorMsg = "该用户已经停用，请联系管理员";
        } else {
            //login success
            req.getSession().setAttribute(USER_SESSION_KEY ,user );
            if(StringUtils.isNotBlank(retAddress)){
                res.sendRedirect(retAddress);
            }else{
                res.sendRedirect("/admin/index.page");
            }
        }

        //失败情况下
        req.setAttribute("error" , errorMsg);
        req.setAttribute("username" , userName);
        if(StringUtils.isNotBlank(retAddress)){
            req.setAttribute("ret" ,retAddress);
        }
        String path = "signin.jsp";
        req.getRequestDispatcher(path).forward(req,res);
    }


}
