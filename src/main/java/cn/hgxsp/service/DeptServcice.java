package cn.hgxsp.service;

import cn.hgxsp.common.RequestHolder;
import cn.hgxsp.dao.SysDeptMapper;
import cn.hgxsp.exception.HgxspException;
import cn.hgxsp.exception.ParamException;
import cn.hgxsp.model.SysDept;
import cn.hgxsp.param.DeptParam;
import cn.hgxsp.util.BeanValidator;
import cn.hgxsp.util.IpUtil;
import cn.hgxsp.util.LevelUtil;
import com.google.common.base.Preconditions;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/3/31
 * Time : 14:19
 */
@Service
public class DeptServcice {

    @Autowired
    private SysDeptMapper sysDeptMapper ;


    public void save(DeptParam deptParam ){
        BeanValidator.check(deptParam);
        if(checkExist(deptParam.getParentId() , deptParam.getName() , deptParam.getId()))
            throw new ParamException("同级不能创建相同组织名称的两个部门") ;
        SysDept sysDept = SysDept.builder()
                .name(deptParam.getName())
                .parentId(deptParam.getParentId())
                .seq(deptParam.getSeq())
                .remark(deptParam.getRemark()).build() ;

        sysDept.setLevel(LevelUtil.calculateLevel(getLevel(deptParam.getParentId())  , deptParam.getParentId()));
        sysDept.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysDept.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysDept.setOperateTime(new Date());
        //insertSelective 这里只插入有值的值，而OBject.insert是插入所有值
        sysDeptMapper.insertSelective(sysDept) ;
    }

    public void update(DeptParam deptParam ){
        BeanValidator.check(deptParam);
        if(checkExist(deptParam.getParentId() , deptParam.getName() , deptParam.getId()))
            throw new ParamException("同级不能创建相同组织名称的两个部门") ;

        //判断原来的部门是否存在
        SysDept beforeDept = sysDeptMapper.selectByPrimaryKey(deptParam.getId());
        Preconditions.checkNotNull(beforeDept  , "待更新的部门不存在") ;
        if(checkExist(deptParam.getParentId() , deptParam.getName() , deptParam.getId()))
            throw new ParamException("同级不能创建相同组织名称的两个部门") ;

        //更新数据
        SysDept afterDept = SysDept.builder()
                .id(deptParam.getId())
                .name(deptParam.getName())
                .parentId(deptParam.getParentId())
                .seq(deptParam.getSeq())
                .remark(deptParam.getRemark()).build() ;

        afterDept.setLevel(LevelUtil.calculateLevel(getLevel(deptParam.getParentId()), deptParam.getParentId()));
        afterDept.setOperator(RequestHolder.getCurrentUser().getUsername());
        afterDept.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        afterDept.setOperateTime(new Date());

        //更新部门和子部门
        updateWithChild(beforeDept , afterDept );

    }

    @Transactional
    public void updateWithChild(SysDept before , SysDept after){

        String newLevelPrefix = after.getLevel() ;
        String oldLevePrefix= before.getLevel() ;
        //如果新旧部门的leve不一致，则部门进行了调动
        if(!newLevelPrefix.equals(oldLevePrefix)){
            List<SysDept> allDeptlist = sysDeptMapper.getChildDeptListByLevel(before.getLevel());

            if(CollectionUtils.isNotEmpty(allDeptlist)){
                for(SysDept sysDept : allDeptlist){
                    String currLevel = sysDept.getLevel() ;
                    if(currLevel.indexOf(oldLevePrefix) == 0 ) {
                        currLevel = newLevelPrefix + currLevel.substring(oldLevePrefix.length()) ;
                        sysDept.setLevel(currLevel);

                    }
                }
                //批量更新dept
                sysDeptMapper.batchUpdateLevel(allDeptlist);
            }
        }
        sysDeptMapper.updateByPrimaryKeySelective(after);

    }


    //验证组织是否存在
    private boolean checkExist(Integer parentId ,String deptName , Integer deptId){
        return sysDeptMapper.countByNameAndParentId (parentId,deptName ,  deptId) > 0 ;
    }

    private String getLevel(Integer deptid){
        SysDept dept = sysDeptMapper.selectByPrimaryKey(deptid);
        if(dept == null ) {
            return null ;
        }
        return dept.getLevel() ;
    }

}
