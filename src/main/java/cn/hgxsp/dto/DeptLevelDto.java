package cn.hgxsp.dto;

import cn.hgxsp.model.SysDept;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * DESC：部门树形结构dto
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/3/31
 * Time : 15:09
 */
@Getter
@Setter
@ToString
public class DeptLevelDto extends SysDept {

    private List<DeptLevelDto> deptList = Lists.newArrayList() ;

    //适配方法，传入的值直接复制新对象返回
    public static DeptLevelDto adapt(SysDept sysDept){
        DeptLevelDto deptLevelDto = new DeptLevelDto() ;
        BeanUtils.copyProperties( sysDept , deptLevelDto);
        return deptLevelDto ;
    }

}
