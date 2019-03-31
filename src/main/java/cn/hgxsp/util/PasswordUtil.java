package cn.hgxsp.util;

import java.util.Date;
import java.util.Random;

/**
 * DESC：生成password
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/3/31
 * Time : 22:02
 */
public class PasswordUtil {


    public final static String[] word = {
            "a" , "b" , "c" , "d" , "e" , "f" , "g" , "h" , "i" , "j" , "k" , "m" , "n" , "p" , "t"} ;

    public final static String[] num = {
            "2" , "3" , "4" , "5" , "6" , "7" , "8" , "9" , "0"  } ;

    public static String randowPassword(){
        StringBuffer sb = new StringBuffer( );
        Random ran = new Random(new Date().getTime()) ;
        boolean flag = false ;
        int length = ran.nextInt(3) + 8  ;
        for(int a = 0 ; a < length ; a ++ ){
            if(flag){
                sb.append(num[ran.nextInt(num.length)]) ;
            }else{
                sb.append(word[ran.nextInt(word.length)]) ;
            }
            flag = !flag ;
        }
        return sb.toString() ;
    }

    public static void main(String[] args) {
        System.out.println(randowPassword());
    }
}
