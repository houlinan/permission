package cn.hgxsp.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * DESC：权限点前端参数
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/4/13
 * Time : 13:20
 */
@Getter
@Setter
@ToString
public class AclParam {


    private Integer id;

    @NotBlank(message = "权限点名称不能为空")
    @Length(min = 2 , max = 20 ,message = "权限点名称必须在2-64个字符之间")
    private String name;

    @NotNull(message = "必须指定权限模块")
    private Integer aclModuleId;

    @Length(min = 6 ,max = 100 ,message = "权限点url必须在6-256字符之间")
    private String url;

    @NotNull(message = "必须指定权限点的类型")
    @Min(value = 1 ,message = "权限点类型不合法")
    @Max(value = 3 ,message = "权限点类型不合法")
    private Integer type;

    @NotNull(message = "必须指定权限点的状态")
    @Min(value = 0 ,message = "权限点状态不合法")
    @Max(value = 1 ,message = "权限点状态不合法")
    private Integer status;

    @NotNull(message = "权限点必须设置展示顺序")
    private Integer seq;

    @Length(min = 0 ,max = 256 , message = "备注必须在0-256字符之间")
    private String remark;

}
