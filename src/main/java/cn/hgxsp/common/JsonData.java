package cn.hgxsp.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * DESC：返回信息结果封装类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/3/24
 * Time : 11:17
 */
@Data
public class JsonData {

    //返回结果
    private boolean ret ;

    //信息
    private String msg ;

    private Object data ;

    public JsonData(boolean ret) {
        this.ret = ret ;
    }

    public static JsonData success(Object object  , String msg ) {
        JsonData jsonData = new JsonData(true);
        jsonData.setMsg(msg);
        jsonData.setData(object);
        return jsonData;
    }

    public static JsonData success (  ) {
        JsonData jsonData = new JsonData(true);
        return jsonData;
    }

    public static JsonData success(Object object  ) {
        JsonData jsonData = new JsonData(true);
        jsonData.setData(object);
        return jsonData;
    }


    public static JsonData fail(Object object  ) {
        JsonData jsonData = new JsonData(false);
        jsonData.setData(object);
        return jsonData;
    }

    public static JsonData fail ( ) {
        JsonData jsonData = new JsonData(false);
        return jsonData;
    }


    public static JsonData fail(Object object  , String msg ) {
        JsonData jsonData = new JsonData(false);
        jsonData.setMsg(msg);
        jsonData.setData(object);
        return jsonData;
    }

    public static JsonData success(String msg ){
        JsonData jsonData = new JsonData(true) ;
        jsonData.setMsg(msg);
        return jsonData;
    }

    public static JsonData fail(String msg ){
        JsonData jsonData = new JsonData(false) ;
        jsonData.setMsg(msg);
        return jsonData;
    }

    //处理异常  modleandview 允许返回一个map类，这里将JSONData对象转成map对象
    public Map<String , Object> toMap(){
        HashMap<String , Object> result = new HashMap<String, Object>( ) ;
        result.put("ret" , ret) ;
        result.put("msg", msg) ;
        result.put("data" ,data );
        return result ;
    }


}
