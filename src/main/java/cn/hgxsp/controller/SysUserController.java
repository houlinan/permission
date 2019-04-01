package cn.hgxsp.controller;

import cn.hgxsp.common.JsonData;
import cn.hgxsp.param.DeptParam;
import cn.hgxsp.param.UserParam;
import cn.hgxsp.service.SysUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/3/31
 * Time : 21:39
 */
@Controller
@RequestMapping("/sys/user")
public class SysUserController {

    @Resource
    SysUserService sysUserService ;


    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveUser(UserParam userParam){
        sysUserService.save(userParam);
        return JsonData.success() ;
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateUser(UserParam userParam){
        sysUserService.update(userParam);
        return JsonData.success() ;
    }



}
