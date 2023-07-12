package com.example.springCase.util;

import com.example.springCase.bean.constant.error.ErrorEnum;
import com.example.springCase.bean.entity.UserDO;
import com.example.springCase.exception.BizException;
import com.rjhy.base.error.ServiceException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.PrivateKey;
import java.util.*;

/**
 * @author tao.wu
 * @date 2022/4/16
 */
@Slf4j
@Component
public class JwtUtil {

    private static final String PAYLOAD_ID = "id";
    private static final String PAYLOAD_USERNAME = "userName";
    private static final String PAYLOAD_EXPIREIN = "expireIn";
    private static final String PAYLOAD_DEVICE_ID = "deviceId";


    public JwtUtil() {
    }

    /**
     * 加载Properties
     *
     * @return Properties
     */
    private static Properties getProperties() {
        Properties properties = new Properties();
        try {
            properties.load(JwtUtil.class.getResourceAsStream("/bootstrap.yml"));
        } catch (IOException e) {
            log.error("getProperties IOException", e);
        }
        return properties;
    }

    /**
     * 创建jwt
     *
     * @param id  (JWT ID)：是JWT的唯一标识
     * @param subject   WT的主体
     * @param ttlMillIn 过期的时间戳
     * @return String String
     */
    public static String createJWT(String id, UserDO userDo, String subject, long ttlMillIn) {
        //指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.RS256;
        //生成JWT的时间
        Date iat = new Date();
        //创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        Map<String, Object> claims = new HashMap<>(16);
        claims.put(PAYLOAD_ID, userDo.getId());
        claims.put(PAYLOAD_USERNAME, userDo.getUsername());
        claims.put(PAYLOAD_EXPIREIN, ttlMillIn);
        //生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取。它就是你服务端的私钥。
        PrivateKey key = RSACryptographyUtil.getPrivateKey(getProperties().get("privateKey").toString());
        //下面就是在为payload添加各种标准声明和私有声明了
        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", signatureAlgorithm.getValue())
                //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(claims)
                //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                //.setId(id)
                //iat: jwt的签发时间
                .setIssuedAt(iat)
                //sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roleid之类的，作为用户的唯一标志。
                //.setSubject(subject)
                //设置签名使用的签名算法和签名使用的秘钥
                .signWith(signatureAlgorithm, key);
        if (ttlMillIn >= 0) {
            //设置过期时间Expiration(默认24小时)
            builder.setExpiration(new Date(iat.getTime() + 24 * 60 * 60 * 1000));
        }
        //压缩为xxxxxxxxxxxxxx.xxxxxxxxxxxxxxx.xxxxxxxxxxxxx这样的jwt
        return builder.compact();
    }

    /**
     * 解析jwt
     *
     * @param jsonWebToken jsonWebToken
     * @return Claims
     */
    public static Claims parseJwt(String jsonWebToken) {
        if (jsonWebToken != null && jsonWebToken.startsWith("Bearer ")) {
            Claims claims;
            try {
                claims = Jwts.parser().setSigningKey(RSACryptographyUtil.getPublicKey(getProperties().get("publicKey").toString())).parseClaimsJws(jsonWebToken.substring(7)).getBody();
                return claims;
            } catch (Exception e) {
                log.error("ParseJwt have a exception ",e);
                throw  new BizException(ErrorEnum.ERROR_TOKEN_PARSE.code(), ErrorEnum.ERROR_TOKEN_PARSE.message());
            }
        } else {
            log.error("JsonWebToken is not conform to the regulation");
            throw  new BizException(ErrorEnum.ERROR_TOKEN_PARSE.code(), ErrorEnum.ERROR_TOKEN_PARSE.message());
        }
    }

    public static Integer getId(String token) {
        Claims claims = parseJwt(token);
        return (Integer) claims.get(PAYLOAD_ID);
    }

    public static String getUserName(String token) {
        Claims claims = parseJwt(token);
        return (String) claims.get(PAYLOAD_USERNAME);
    }

    public static String getDeviceId(String token) {
        Claims claims = parseJwt(token);
        return (String) claims.get(PAYLOAD_DEVICE_ID);
    }

    public static long getExpireIn(String token) {
        Claims claims = parseJwt(token);
        return (long) claims.get(PAYLOAD_EXPIREIN);
    }

    /**
     * 测试
     * Bearer :票据
     */
    public static void main(String[] args) {
//        System.out.println("this is:" + tokenPublicKey);
//        String jwtToken = "Bearer " + createJWT("1", new UserDo(), "{\"uId\":\"123\"}", System.currentTimeMillis() + 1000);
//        log.info("jwt={}", jwtToken);
//
//        String uId = getUid(jwtToken);
//        log.info("uId={}", uId);
    }


}
