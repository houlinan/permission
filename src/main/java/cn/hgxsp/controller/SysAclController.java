package cn.hgxsp.controller;

import cn.hgxsp.common.JsonData;
import cn.hgxsp.service.SysAclService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * DESC：权限点管理controller
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/4/7
 * Time : 13:27
 */
@Controller
@Slf4j
@RequestMapping("/sys/acl")
public class SysAclController {

    @Resource
    SysAclService sysAclService ;

//    public JsonData save(sysacl)

}
