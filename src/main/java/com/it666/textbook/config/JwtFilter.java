package com.it666.textbook.config;


import com.it666.textbook.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Abouerp
 */
@Component
@Log4j2
public class JwtFilter extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        final String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token == null) {
            return false;
        }
        Claims claims = JwtUtils.parseJWT(token);
        return claims != null;
    }
}
