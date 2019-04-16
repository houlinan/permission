package cn.hgxsp.service;

import cn.hgxsp.beans.PageQuery;
import cn.hgxsp.beans.PageResult;
import cn.hgxsp.common.RequestHolder;
import cn.hgxsp.dao.SysAclMapper;
import cn.hgxsp.exception.ParamException;
import cn.hgxsp.model.SysAcl;
import cn.hgxsp.param.AclParam;
import cn.hgxsp.util.BeanValidator;
import cn.hgxsp.util.IpUtil;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * DESC：权限点service
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/4/13
 * Time : 14:04
 */
@Service
public class SysAclService {

    @Resource
    SysAclMapper sysAclMapper;

    public void save(AclParam aclParam){
        BeanValidator.check(aclParam);
        if(checkExist(aclParam.getAclModuleId() , aclParam.getName() , aclParam.getId())) throw new ParamException("相同权限模块下不能设置相同名称的权限点");

        SysAcl sysAcl = SysAcl.builder()
                .aclModuleId(aclParam.getAclModuleId())
                .name(aclParam.getName())
                .aclModuleId(aclParam.getAclModuleId())
                .url(aclParam.getUrl())
                .type(aclParam.getType())
                .status(aclParam.getStatus())
                .seq(aclParam.getSeq())
                .remark(aclParam.getRemark()).build();
        sysAcl.setCode(UUID.randomUUID().toString().replace("-",""));
        sysAcl.setOperateIp(RequestHolder.getCurrentUser().getUsername());
        sysAcl.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysAcl.setOperateTime(new Date());

        sysAclMapper.insertSelective(sysAcl) ;

    }

    public void update(AclParam aclParam){

        BeanValidator.check(aclParam);
        if(checkExist(aclParam.getAclModuleId() , aclParam.getName() , aclParam.getId())) throw new ParamException("相同权限模块下不能设置相同名称的权限点");

        SysAcl before = sysAclMapper.selectByPrimaryKey(aclParam.getId()) ;

        Preconditions.checkNotNull(before , "没有找到要更新的对象") ;

        SysAcl after = SysAcl.builder()
                .id(aclParam.getId())
                .aclModuleId(aclParam.getAclModuleId())
                .name(aclParam.getName())
                .aclModuleId(aclParam.getAclModuleId())
                .url(aclParam.getUrl())
                .type(aclParam.getType())
                .status(aclParam.getStatus())
                .seq(aclParam.getSeq())
                .remark(aclParam.getRemark()).build();
        after.setOperateIp(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());

        sysAclMapper.updateByPrimaryKeySelective(after) ;

    }

    public boolean checkExist(int aclModuleId , String name , Integer id ) {
        return false ;
    }

    public PageResult<SysAcl> getPageByAclModuleId(Integer aclModuleId , PageQuery pageQuery){
        BeanValidator.check(pageQuery);
        int count = sysAclMapper.countByAclModuleid(aclModuleId) ;
        if(count > 0 ){
            List<SysAcl> result = sysAclMapper.getPageByAclModuleId(aclModuleId, pageQuery);
            return PageResult.builder().data(result).total(count).build();
        }
        return PageResult.<SysAcl>builder().build();
    }



}
