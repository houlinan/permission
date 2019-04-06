package cn.hgxsp.beans;

import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * DESC：分页查询返回值
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/4/6
 * Time : 13:41
 */
@Getter
@Setter
@ToString
@Builder
public class PageResult<T> {

    private List<T> data = Lists.newArrayList() ;

    private int total ;


}
