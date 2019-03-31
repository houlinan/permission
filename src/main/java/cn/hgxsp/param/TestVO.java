package cn.hgxsp.param;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/3/24
 * Time : 14:26
 */
@Data
public class TestVO {

    @NotBlank
    private String msg;

    @NotNull
    @Max(value = 10 , message = "id 不能大于10")
    @Min(value = 0 , message =  "id不能小于 0  ")
    private Integer id;

    @NotEmpty
    private List<String > str  ;
}
