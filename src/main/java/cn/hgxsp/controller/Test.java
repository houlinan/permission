package cn.hgxsp.controller;


import com.sun.deploy.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/3/29
 * Time : 12:19
 */
public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) {
//        String htmlStr = "<img style=\"cursor: pointer\" videoid=\"10000855\" docid=\"0\" filename=\"Avi.avi\" controls=\"controls\" src=\"W020190329345676565755.png\" filesize=\"25818356\" resourcetype=\"video/mp4\" ignore=\"1&#39;&#39;\" id=\"video_8037\" oldsrc=\"W020190329345676565755.png\" />";
        //        System.out.println(match(htmlStr , "audioid"));
        String htmlStr = "<p>231艾达王 大大sad videoid=\"10000855\" ad阿斯顿as<img style=\"cursor: pointer\" videoid=\"10000855\" docid=\"0\" filename=\"Avi.avi\" controls=\"controls\" src=\"W020190329345676565755.png\" filesize=\"25818356\" resourcetype=\"video/mp4\" ignore=\"1&#39;&#39;\" id=\"video_8037\" oldsrc=\"W020190329345676565755.png\" />二位请问请问请问委屈恶趣味请问请问qwe<img style=\"cursor: pointer\" videoid=\"10000797\" docid=\"0\" filename=\"12321\" controls=\"controls\" src=\"W020190329345712542882.png\" filesize=\"1094214\" resourcetype=\"video/mp4\" ignore=\"1&#39;&#39;\" id=\"video_8413\" oldsrc=\"W020190329345712542882.png\" />额请问请问请问请问请问qw大大&nbsp;<img width=\"550px\" audioid=\"20000097\" docid=\"3467\" filename=\"WAV - Sunk.wav\" controls=\"controls\" src=\"W020190329349915935379.png\" filesize=\"9897621\" resourcetype=\"audio/mp3\" oldsrc=\"W020190329349915935379.png\" /></p><p style=\"text-align:center;\"><img width=\"550px\" audioid=\"20000074\" docid=\"3467\" filename=\"最后的莫西干人原声.mp3\" controls=\"controls\" src=\"W020190329349918118991.png\" filesize=\"11621107\" resourcetype=\"audio/mp3\" oldsrc=\"W020190329349918118991.png\" /></p><div></div>\n" +
                "\n";
        System.out.println(updateHtmlTag(htmlStr ,"img" ));
    }

    public static String match(String source, String attr) {
        String result = "";
        String reg = "[^<>]*?\\s" + attr
                + "=['\"]?(.*?)['\"]?(\\s.*?)";
        Matcher m = Pattern.compile(reg).matcher(source);
        while (m.find()) {
            return m.group(1);
        }
        return result;
    }

    /**
     * @param htmlStr  html文本
     * @param searchTag  要修改的目标标签
     */
    public static String updateHtmlTag(String htmlStr, String searchTag) {
        String regxpForTag = "<\\s*" + searchTag + "\\s+([^>]*)\\s*>";
        Pattern patternForTag = Pattern.compile(regxpForTag);
        Matcher matcherForTag = patternForTag.matcher(htmlStr);
        StringBuffer sb = new StringBuffer();
        boolean result = matcherForTag.find();
        while (result) {
            StringBuffer sbreplace = new StringBuffer("<" + searchTag + " ");
            String currTagStr = matcherForTag.group(1);
            System.out.println(currTagStr);


            String searchVideoId = match(currTagStr  , "videoid" ) ;
            if(!StringUtils.isEmpty(searchVideoId) ){
                matcherForTag.appendReplacement(sb, "<img " + currTagStr.replace(searchVideoId, "11111") + "/>");
            }
            String searchAutoId = match(currTagStr  , "audioid" ) ;
            if(!StringUtils.isEmpty(searchAutoId) ){
                matcherForTag.appendReplacement(sb, "<img " + currTagStr.replace(searchAutoId, "22222") + "/>");
            }
            result = matcherForTag.find();
            continue;
        }
        matcherForTag.appendTail(sb);
        return sb.toString();
    }



}
