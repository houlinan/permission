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
 * DESC：用户类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/3/31
 * Time : 21:39
 */
@Getter
@Setter
@ToString
public class UserParam  {

    private Integer id;

    @NotNull(message = "用户名称不能为空")
    @Length(min = 1 , max = 20 ,message = "用户名称长度在1-20字符之间")
    private String username;

    @NotBlank(message = "电话不可以为空")
    @Length(max = 13 )
    private String telephone;

    @NotBlank(message = "邮箱不可以为空")
    @Length(min = 5 , max = 50 ,message = "邮箱长度在5 - 50字符之间")
    private String mail;

    @NotNull(message = "必须提供用户所在的部门")
    private Integer deptId;

    @NotNull(message = "用户状态不能为空")
    @Min(value= 0 ,message = "用户状态不合法")
    @Max(value= 2 ,message = "用户状态不合法")
    private Integer status;

    @Length(max = 200 , message = "备注长度需要在200个字符以内")
    private String remark;
}
