package cn.hgxsp.filter;

import cn.hgxsp.common.RequestHolder;
import cn.hgxsp.controller.UserController;
import cn.hgxsp.model.SysUser;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/4/6
 * Time : 14:51
 */
@Slf4j
public class LoginFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)servletRequest ;
        HttpServletResponse res = (HttpServletResponse)servletResponse ;
        //判断请求地址
        String servletPath = req.getServletPath();
        SysUser reqUser = (SysUser) req.getSession().getAttribute(UserController.USER_SESSION_KEY);

        if(reqUser == null ){
            //跳转到登录页面
            String loginPage = "/signin.jsp";
            res.sendRedirect(loginPage);
            return ;
        }
        RequestHolder.add(reqUser);
        RequestHolder.add(req);
        filterChain.doFilter(servletRequest ,servletResponse );
        return ;
    }

    @Override
    public void destroy() {

    }
}
