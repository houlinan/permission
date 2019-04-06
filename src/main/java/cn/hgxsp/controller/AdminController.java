package cn.hgxsp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/4/1
 * Time : 20:27
 */
@Controller
@RequestMapping("/admin")
public class AdminController {


    @RequestMapping("index.page")
    public ModelAndView index(){
        return new ModelAndView("admin") ;
    }



}
