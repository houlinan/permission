package cn.hgxsp.beans;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

/**
 * DESC：分页查询公共类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/4/6
 * Time : 13:41
 */
public class PageQuery {

    @Getter
    @Setter
    @Min(value = 1 , message = "当前页码不合法")
    private int pageNo = 1 ;

    @Getter
    @Setter
    @Min(value = 1 , message = "每页展示的数量不合法")
    private int pageSize = 10 ;

    @Setter
    //数据库查询limit数字
    private int offset ;

    public int getOffset(){
        return (pageNo - 1 ) * pageSize ;
    }

}
