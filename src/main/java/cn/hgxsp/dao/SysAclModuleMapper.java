package cn.hgxsp.dao;

import cn.hgxsp.model.SysAclModule;
import cn.hgxsp.model.SysDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAclModuleMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(SysAclModule record);

    int insertSelective(SysAclModule record);

    SysAclModule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAclModule record);

    int updateByPrimaryKey(SysAclModule record);

    //获取当前的所有部门列表
    List<SysAclModule> getAllAclModule();

    //获取当前部门的所有子部门
    List<SysAclModule>  getChildAclModuleListByLevel(@Param("level") String level) ;

    //批量更新部门level
    void batchUpdateLevel(@Param("needToUpdateSysAclModules") List<SysAclModule> needToUpdateSysAclModules);

    //验证部门是否存在
    int countByNameAndParentId( @Param("parentId") Integer parentId , @Param("aclModuleName") String aclModuleName ,@Param("id") Integer id );

}