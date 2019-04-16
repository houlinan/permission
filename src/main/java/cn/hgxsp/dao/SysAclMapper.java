package cn.hgxsp.dao;

import cn.hgxsp.beans.PageQuery;
import cn.hgxsp.model.SysAcl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAclMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(SysAcl record);

    int insertSelective(SysAcl record);

    SysAcl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAcl record);

    int updateByPrimaryKey(SysAcl record);

    int countByAclModuleid(@Param("aclModuleId")Integer aclModuleId) ;

    List<SysAcl> getPageByAclModuleId(@Param("aclModuleId")Integer aclModuleId ,@Param("page") PageQuery pageQuery ) ;
}