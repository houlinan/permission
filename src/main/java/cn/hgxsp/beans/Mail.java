package cn.hgxsp.beans;

import lombok.*;

import java.util.Set;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/4/6
 * Time : 15:26
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mail {

    private String subject;
    private String message ;
    private Set<String> receivers ;
}
