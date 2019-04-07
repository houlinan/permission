package cn.hgxsp.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/4/7
 * Time : 13:29
 */
@Getter
@Setter
@ToString
public class AclModuleParam {

    private Integer id;

    @NotBlank(message = "权限模块名称不能为空")
    @Length(min = 1 ,max = 20 ,message = "权限模块名称在1-20个字符以内")
    private String name;

    private Integer parentId = 0 ;

    @NotNull(message = "权限模块展示顺序不能为空")
    private Integer seq;

    @NotNull(message = "权限模块状态不能为空")
    @Min(value =0 ,message = "状态不合法")
    @Max(value = 1 , message =  "状态不合法")
    private Integer status ;

    @Length(max = 200 ,message = "权限模块备注在200个字符以内")
    private String remark;
}
