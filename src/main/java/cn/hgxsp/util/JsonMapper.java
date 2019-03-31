package cn.hgxsp.util;

import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.util.ObjectUtils;

/**
 * DESC：将json转成对象，将对象转成json
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/3/25
 * Time : 21:54
 */
@Slf4j
public class JsonMapper {

    private static ObjectMapper objectMapper = new ObjectMapper( ) ;

    static {
        //config 设置不认识的字段应该怎么处理。排除掉为空的字段
        objectMapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS ,false);
        objectMapper.setFilters(new SimpleFilterProvider().setFailOnUnknownId(false));
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY) ;
    }

    //核心转换方法 ，将对象转成字符串
    public static <T> String obj2String (T src) {
        if(ObjectUtils.isEmpty(src)) return null ;
        try {
            return src instanceof String ? (String) src : objectMapper.writeValueAsString(src);
        }catch (Exception e ){
            log.warn("param  Object to Stirng Error e = {}" ,e);
            return null ;
        }
    }

    //将String 转成Object
    public static <T> T string2Obj(String str , TypeReference typeReference){
        if(ObjectUtils.isEmpty(str) || ObjectUtils.isEmpty(typeReference))  return null ;

        try {
            return (T) (typeReference.getType().equals(String.class) ? str : objectMapper.readValue(str ,typeReference));
        }catch (Exception e ){
            log.warn("param String to Object Error ;String : {} , typeReference :{} . error :{}" ,str , typeReference , e  ) ;
            return null ;
        }

    }


}
