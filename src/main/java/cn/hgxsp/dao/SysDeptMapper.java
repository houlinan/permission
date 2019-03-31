package cn.hgxsp.dao;

import cn.hgxsp.model.SysDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDeptMapper {
    int deleteByPrimaryKey(@Param("id") Integer id);

    int insert(SysDept record);

    int insertSelective(SysDept record);

    SysDept selectByPrimaryKey(@Param("id")Integer id);

    int updateByPrimaryKeySelective(SysDept record);

    int updateByPrimaryKey(SysDept record);

    //获取当前的所有部门列表
    List<SysDept> getAllDept();

    //获取当前部门的所有子部门
    List<SysDept>  getChildDeptListByLevel(@Param("level") String level) ;

    //批量更新部门level
    void batchUpdateLevel(@Param("needToUpdateSysDepts") List<SysDept> needToUpdateSysDepts);

    //验证部门是否存在
    int countByNameAndParentId( @Param("parentId") Integer parentId , @Param("deptName") String deptName ,@Param("id") Integer id );
}