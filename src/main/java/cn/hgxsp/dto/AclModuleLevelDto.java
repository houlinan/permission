package cn.hgxsp.dto;

import cn.hgxsp.model.SysAclModule;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * DESC：权限模块树处理DTo
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/4/7
 * Time : 22:31
 */
public class AclModuleLevelDto extends SysAclModule {


    private List<SysAclModule> aclModuleList = Lists.newArrayList() ;

    public static AclModuleLevelDto adapt(SysAclModule  sysAclModule){
        AclModuleLevelDto dto = new AclModuleLevelDto();
        BeanUtils.copyProperties(sysAclModule , dto );
        return dto ;
    }

}
