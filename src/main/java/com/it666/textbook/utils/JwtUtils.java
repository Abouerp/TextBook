package com.it666.textbook.utils;

import io.jsonwebtoken.*;

import java.util.Date;

/**
 * @author Abouerp
 */
public class JwtUtils {

    private JwtUtils() {
        throw new IllegalStateException("Utility class");
    }

    private static final String KEY = "itcast";
    private static final long TTL = 1000000;

    /**
     * 创建token
     * @param id
     * @param userName
     * @param roles
     * @return
     */

    public static String createJWT(String id, String userName, String roles) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, KEY)
                .claim("userName", userName)
                .claim("roles", roles);
        if (TTL > 0L) {
            builder.setExpiration(new Date(nowMillis + TTL));
        }
        return builder.compact();
    }

    /**
     * 解析token
     * @param jwtStr
     * @return
     */
    public static Claims parseJWT(String jwtStr) {
//        Claims body = null;
//        try {
//            body = Jwts.parser().setSigningKey(KEY).parseClaimsJws(jwtStr).getBody();
//        }catch (ExpiredJwtException e) {
//
//        }

        return Jwts.parser().setSigningKey(KEY).parseClaimsJws(jwtStr).getBody();
    }

}
