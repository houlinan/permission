package cn.hgxsp.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * DESC：部门类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/3/31
 * Time : 14:09
 */
@Data
public class DeptParam {

    private Integer id;

    @NotBlank(message = "部门名称不能为空")
    @Length(max =15 , min = 2, message = "部门名称必须在2-15个字符之间")
    private String name;

    private Integer parentId = 0 ;
    //顺序
    @NotNull(message = "显示顺序不能为空")
    private Integer seq;
    //备注
    @Length(max = 150 ,  message = "备注长度不能超过150个字符")
    private String remark;




}
