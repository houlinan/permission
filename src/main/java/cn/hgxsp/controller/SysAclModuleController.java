package cn.hgxsp.controller;

import cn.hgxsp.common.JsonData;
import cn.hgxsp.param.AclModuleParam;
import cn.hgxsp.param.DeptParam;
import cn.hgxsp.service.SysAclModuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * DESC：权限管理模块controller
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/4/7
 * Time : 13:26
 */
@RequestMapping("/sys/aclModule")
@Controller
@Slf4j
public class SysAclModuleController {

    @Resource
    SysAclModuleService sysAclModuleService ;

    @RequestMapping("/acl.page")
    public ModelAndView page(){
        return new ModelAndView("acl") ;
    }

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveAclModule(AclModuleParam aclModuleParam){

        sysAclModuleService.save(aclModuleParam);
        return JsonData.success() ;
    }


    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateAclModule(AclModuleParam aclModuleParam){

        sysAclModuleService.update(aclModuleParam);

        return JsonData.success() ;
    }

    @RequestMapping("/tree.json")
    @ResponseBody
    public JsonData tree(){

        return JsonData.success() ;
    }


}
