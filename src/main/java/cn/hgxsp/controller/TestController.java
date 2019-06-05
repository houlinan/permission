package cn.hgxsp.controller;

import cn.hgxsp.common.ApplicationContextHelper;
import cn.hgxsp.common.JsonData;
import cn.hgxsp.dao.SysAclModuleMapper;
import cn.hgxsp.exception.ParamException;
import cn.hgxsp.model.SysAclModule;
import cn.hgxsp.param.TestVO;
import cn.hgxsp.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/test")
@Slf4j
public class TestController {


    @RequestMapping("/hello.json")
    @ResponseBody
    public JsonData hello() {
        log.info("hello");
//        throw new PermissionException("test exception");
        // return JsonData.success("hello, permission");
//        JsonData jsonData = ;
        return JsonData.success("成功");
    }


    @RequestMapping("/validate.json")
    @ResponseBody
    public JsonData validate(TestVO testVO) throws ParamException {
        log.info("validate");
//        try{
//            Map<String ,String > map = BeanValidator.validateObject(testVO) ;
//            if(MapUtils.isNotEmpty(map)){
//                for(Map.Entry<String , String > entry : map.entrySet()){
//                    log.info("{}->{}" , entry.getKey() , entry.getValue() );
//                }
//            }
//        }catch(Exception e){
//
//        }

        //获取springbean
        SysAclModuleMapper moduleMapper = ApplicationContextHelper.popBean(SysAclModuleMapper.class);
        SysAclModule module = moduleMapper.selectByPrimaryKey(1);
        log.info(JsonMapper.obj2String(module));

        //校验参数
//        BeanValidator.check(testVO);


        return JsonData.success("成功");
    }

    public static void main(String[] args) {
//        String str = "\"10000850\" 10000851";
//        System.out.println(str.replace("\"10000850\"" , "123123"));
        String str = "<p><img style=\"cursor: pointer\" videoid=\"10000850\" docid=\"0\" filename=\"39f5c7d131a255c5f4ef49d7f9cd0e37.mp4\" controls=\"controls\" src=\"W020190327366998793012.png\" filesize=\"1015276\" resourcetype=\"video/mp4\" ignore=\"1&#39;&#39;\" id=\"video_28240\" OLDSRC=\"W020190327366998793012.png\" /><img style=\"cursor: pointer\" videoid=\"10000855\" docid=\"0\" filename=\"Avi.avi\" controls=\"controls\" src=\"W020190327367001617487.png\" filesize=\"25818356\" resourcetype=\"video/mp4\" ignore=\"1&#39;&#39;\" id=\"video_10206\" OLDSRC=\"W020190327367001617487.png\" />khjkhjkhj<img style=\"cursor: pointer\" videoid=\"10000845\" docid=\"0\" filename=\"58cb50c42779defdd33b2b025c0a6a6f.mp4\" controls=\"controls\" src=\"W020190327367002375230.png\" filesize=\"1094214\" resourcetype=\"video/mp4\" ignore=\"1&#39;&#39;\" id=\"video_67197\" OLDSRC=\"W020190327367002375230.png\" /><img style=\"cursor: pointer\" videoid=\"10000797\" docid=\"0\" filename=\"12321\" controls=\"controls\" src=\"W020190327367002986483.png\" filesize=\"1094214\" resourcetype=\"video/mp4\" ignore=\"1&#39;&#39;\" id=\"video_88132\" OLDSRC=\"W020190327367002986483.png\" /><img style=\"cursor: pointer\" videoid=\"10000844\" docid=\"0\" filename=\"11111.mp4\" controls=\"controls\" src=\"W020190327367003596551.png\" filesize=\"836742\" resourcetype=\"video/mp4\" ignore=\"1&#39;&#39;\" id=\"video_5198\" OLDSRC=\"W020190327367003596551.png\" /></p>";

        replaceStr(null , str);
    }

    public static String replaceStr(Map meiziResultMap  , String str ){

        Map<String, Integer> map = new HashMap<String, Integer>();

        map.put("type", 1 );
        map.put("code", 0 );
        map.put("10000850", 111111);
        map.put("10000855",22222);
        map.put("10000845", 333333);
        map.put("10000797", 45444444);
        map.put("10000844", 5555555);
        String videoIds = "10000850,10000855,10000845,10000797,10000844";

        String[] split = videoIds.split(",");

        try{
            //成功
            map.remove("type") ;

            if(map.get("code") != null && map.get("code") == 0 ) {
                map.remove("code") ;

                for (int a = 0; a < split.length; a++) {
                    String videoidTag = " videoid=\"" + split[a] + "\" ";
                    String newVideoIdTag = " videoid=\"" + map.get(split[a])  + "\" " ;

                    str = str.replace(videoidTag ,newVideoIdTag );
                }
            }
        }catch(NullPointerException e ){
            e.printStackTrace();
        }

        System.out.println(str);
        return str;
         }




}
