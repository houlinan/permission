package cn.hgxsp.controller;

import cn.hgxsp.beans.PageQuery;
import cn.hgxsp.beans.PageResult;
import cn.hgxsp.common.JsonData;
import cn.hgxsp.model.SysUser;
import cn.hgxsp.param.DeptParam;
import cn.hgxsp.param.UserParam;
import cn.hgxsp.service.SysUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping("page.json")
    @ResponseBody
    public JsonData page(@RequestParam("deptId") Integer deptId , PageQuery pageQuery ){
        PageResult<SysUser> result = sysUserService.getPageByDeptId(deptId, pageQuery);
        return JsonData.success(result) ;
    }



}
