package cn.hgxsp.dto;

import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Data;

import java.awt.*;
import java.util.List;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/4/10
 * Time : 21:35
 */
@Data
@Builder
public class POIDto {

    private String xingshi  ;

    private String biaoti ;

    private String shichang ;

    private String jizhe  ;

    private String tongxunyuan ;


    public static List<POIDto> getAllDto(){
        List<POIDto> result = Lists.newArrayList() ;
        POIDto dto1 = POIDto.builder().xingshi("编、导").biaoti("第一条标题").shichang("00:00:09").jizhe("贺佳").tongxunyuan("哈哈哈 ").build();
        result.add(dto1) ;

        POIDto dto2 = POIDto.builder().xingshi("编、导").biaoti("第2条标题").shichang("00:00:19").jizhe("贺佳").tongxunyuan("哈哈哈 ").build();
        result.add(dto2) ;

        POIDto dto3 = POIDto.builder().xingshi("编、导").biaoti("第3条标题").shichang("00:00:29").jizhe("贺佳").tongxunyuan("哈哈哈 ").build();
        result.add(dto3) ;

        POIDto dto4 = POIDto.builder().xingshi("编、导").biaoti("第4条标题").shichang("00:00:39").jizhe("贺佳").tongxunyuan("哈哈哈 ").build();
        result.add(dto4) ;

        POIDto dto5 = POIDto.builder().xingshi("编、导").biaoti("第5条标题").shichang("00:00:49").jizhe("贺佳").tongxunyuan("哈哈哈 ").build();
        result.add(dto5) ;

        return result ;




    }

}
