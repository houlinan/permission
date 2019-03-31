package cn.hgxsp.controller;

import cn.hgxsp.common.JsonData;
import cn.hgxsp.dto.DeptLevelDto;
import cn.hgxsp.param.DeptParam;
import cn.hgxsp.service.DeptServcice;
import cn.hgxsp.service.SysTreeService;
import cn.hgxsp.util.BeanValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * DESC：部门controller类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/3/31
 * Time : 14:16
 */

@Controller
@RequestMapping("sys/dept")
@Slf4j
public class SysDeptController {

    @Autowired
    DeptServcice deptServcice ;

    @Resource
    SysTreeService  sysTreeService ;

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveDept(DeptParam deptParam){
        deptServcice.save(deptParam);
        return JsonData.success() ;
    }

    @RequestMapping("/tree.json")
    @ResponseBody
    public JsonData tree(){
        List<DeptLevelDto> deptLevelDtos = sysTreeService.deptTree();

        return JsonData.success(deptLevelDtos) ;
    }


    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateDept(DeptParam deptParam){
        deptServcice.update(deptParam);
        return JsonData.success() ;
    }


    @RequestMapping("/dept.page")
    public ModelAndView page(){
        return new ModelAndView("dept") ;
    }



}
