package cn.hgxsp.util;

import org.apache.commons.lang3.StringUtils;

/**
 * DESC：部门level计算工具类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/3/31
 * Time : 14:23
 */
public class LevelUtil {

    private final static String SEPARATOR = ".";

    public final static String ROOT = "0";

    public static String calculateLevel(String parentLevel , int parentId){
        if(StringUtils.isBlank(parentLevel)){
            return ROOT ;
        }else{
            return StringUtils.join(parentLevel , SEPARATOR , parentId) ;
        }

    }

}
