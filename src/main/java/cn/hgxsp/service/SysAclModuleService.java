package cn.hgxsp.service;


import cn.hgxsp.common.RequestHolder;
import cn.hgxsp.dao.SysAclModuleMapper;
import cn.hgxsp.exception.ParamException;
import cn.hgxsp.model.SysAclModule;
import cn.hgxsp.model.SysDept;
import cn.hgxsp.param.AclModuleParam;
import cn.hgxsp.util.BeanValidator;
import cn.hgxsp.util.IpUtil;
import cn.hgxsp.util.LevelUtil;
import com.google.common.base.Preconditions;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * DESC：权限模块service
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/4/7
 * Time : 14:35
 */
@Service
public class SysAclModuleService {

    @Resource
    SysAclModuleMapper sysAclModuleMapper ;


    public void save(AclModuleParam aclModuleParam ){
        BeanValidator.check(aclModuleParam);
        if(checkExist(aclModuleParam.getParentId() , aclModuleParam.getName() ,aclModuleParam.getId()))
            throw new ParamException("同一层级下不能用相同名称的权限模块") ;

        SysAclModule sysAclModule = SysAclModule.builder()
                .name(aclModuleParam.getName())
                .parentId(aclModuleParam.getParentId())
                .seq(aclModuleParam.getSeq())
                .status(aclModuleParam.getStatus())
                .remark(aclModuleParam.getRemark()).build();

        sysAclModule.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysAclModule.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysAclModule.setOperateTime(new Date());
        sysAclModule.setLevel(LevelUtil.calculateLevel(getLevel(aclModuleParam.getParentId()), aclModuleParam.getParentId()));
        sysAclModuleMapper.insertSelective(sysAclModule) ;
    }

    public void update(AclModuleParam aclModuleParam ){
        BeanValidator.check(aclModuleParam);
        if(checkExist(aclModuleParam.getParentId() , aclModuleParam.getName() ,aclModuleParam.getId()))
            throw new ParamException("同一层级下不能用相同名称的权限模块") ;
        SysAclModule beaforeSysAclModule = sysAclModuleMapper.selectByPrimaryKey(aclModuleParam.getId());
        Preconditions.checkNotNull(beaforeSysAclModule , "待更新的权限模块不存在") ;


        SysAclModule afterSysAclModule = SysAclModule.builder()
                .id(aclModuleParam.getId())
                .name(aclModuleParam.getName())
                .parentId(aclModuleParam.getParentId())
                .seq(aclModuleParam.getSeq())
                .status(aclModuleParam.getStatus())
                .remark(aclModuleParam.getRemark()).build();
        afterSysAclModule.setLevel(LevelUtil.calculateLevel(getLevel(afterSysAclModule.getParentId()), afterSysAclModule.getParentId()));

        updateWithChild(beaforeSysAclModule ,afterSysAclModule) ;
    }

    @Transactional
    public void updateWithChild(SysAclModule beforeAclModule , SysAclModule afterAclModule){

        String newLevelPrefix = afterAclModule.getLevel();
        String oldLevePrefix= beforeAclModule.getLevel() ;
        //如果新旧部门的leve不一致，则部门进行了调动
        if(!newLevelPrefix.equals(oldLevePrefix)){
            List<SysAclModule> allAclModuleList = sysAclModuleMapper.getChildAclModuleListByLevel(beforeAclModule.getLevel());

            if(CollectionUtils.isNotEmpty(allAclModuleList)){
                for(SysAclModule sysAclModule : allAclModuleList){
                    String currLevel = sysAclModule.getLevel() ;
                    if(currLevel.indexOf(oldLevePrefix) == 0 ) {
                        currLevel = newLevelPrefix + currLevel.substring(oldLevePrefix.length()) ;
                        sysAclModule.setLevel(currLevel);

                    }
                }
                //批量更新dept
                sysAclModuleMapper.batchUpdateLevel(allAclModuleList);
            }
        }


        sysAclModuleMapper.updateByPrimaryKeySelective(afterAclModule);

    }

    //验证组织是否存在
    private boolean checkExist(Integer parentId ,String aclModuleName , Integer deptId){
        return sysAclModuleMapper.countByNameAndParentId(parentId ,aclModuleName , deptId ) > 0 ;
    }

    private String getLevel(Integer aclModuleId){
        SysAclModule sysAclModule = sysAclModuleMapper.selectByPrimaryKey(aclModuleId);
        if(sysAclModule == null ) {
            return null ;
        }
        return sysAclModule.getLevel() ;
    }



}
