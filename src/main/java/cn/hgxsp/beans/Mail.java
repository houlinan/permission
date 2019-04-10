package cn.hgxsp.beans;

import lombok.*;
import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.util.Set;
import java.util.UUID;

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


    public static void main(String[] args) {

        getToken()
                ;
    }

    public static String[] getToken() {
        String requestId = UUID.randomUUID().toString();
        long timestamp = System.currentTimeMillis() / 1000l; // 时间戳

        StringBuffer strbuf = new StringBuffer("").append("requestId:")
                .append(requestId).append("timeStamp:").append(timestamp)
                .append("salt:").append("LRmHj8P2Dk");
        String token = encodeMD5Hex(strbuf.toString());
        String[] tokens = new String[3];
        tokens[0] = requestId;
        tokens[1] = String.valueOf(timestamp);
        tokens[2] = token;
        System.out.println("requestId = " + requestId + " ;  String.valueOf(timestamp)= " +  String.valueOf(timestamp) + " ;token = " + token);
        return tokens;
    }

    public static final String encodeMD5Hex(String source) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source.getBytes("UTF-8"));

            return Hex.encodeHexString(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
