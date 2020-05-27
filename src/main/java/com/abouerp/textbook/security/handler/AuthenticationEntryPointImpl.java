package com.abouerp.textbook.security.handler;//package com.Abouerp.textbook.security.handler;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import UnauthorizedException;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.MediaType;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * @author Abouerp
// */
//@Configuration
//public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
//    private final ObjectMapper objectMapper;
//
//    public AuthenticationEntryPointImpl(ObjectMapper objectMapper) {
//        this.objectMapper = objectMapper;
//    }
//
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
//        UnauthorizedException e = new UnauthorizedException();
//        response.setStatus(e.getCode());
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.getWriter().write(objectMapper.writeValueAsString(e.getResultBean()));
//    }
//}
