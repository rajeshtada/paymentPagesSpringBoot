//package com.ftk.pg.config;
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.stereotype.Component;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ftk.pg.exception.GlobalExceptionHandler;
//import com.ftk.pg.exception.InvalidAuthKeyException;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//public class AuthHeaderInjectFilter implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
////        HttpServletResponse httpResponse = (HttpServletResponse) response;
////        ObjectMapper objectMapper = new ObjectMapper();
////        String authHeader = httpRequest.getHeader("Authorization");
////
////        
////        Map<String, Object> body = new HashMap<>();
////        body.put("error", "Unauthorized");
////        body.put("message", "Invalid or missing Authorization header");
////        body.put("status", 500);
////        body.put("path", httpRequest.getRequestURI());
////        
////        if (authHeader == null ||authHeader.trim().equalsIgnoreCase("") || !authHeader.startsWith("Bearer ")) {
//////            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//////            response.getWriter().write("InvalidAuthKeyException");
//////            return;
//////        	throw new InvalidAuthKeyException("InvalidAuthKeyException");
//////        	throw new RuntimeException("InvalidAuthKeyException");
////        	
////            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
////            httpResponse.setContentType("application/json");
////
////            httpResponse.getWriter().write(objectMapper.writeValueAsString(body));
////            return;
////            
////        }
////
//////        String token = authHeader.substring(7); // Remove "Bearer "
////        try {
////			String token = authHeader.substring(7);
////	        CustomHeaderRequestWrapper wrappedRequest = new CustomHeaderRequestWrapper(httpRequest);
////	        wrappedRequest.addHeader("Authorization", token);
////	        chain.doFilter(wrappedRequest, response);
////		} catch (Exception e) {
////			new GlobalExceptionHandler().customException(e);
//////			throw new InvalidAuthKeyException("InvalidAuthKeyException");
//////            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//////            response.getWriter().write("InvalidAuthKeyException");
////	        httpResponse.getWriter().write(objectMapper.writeValueAsString(body));
////            return;
////		}
//        
//        CustomHeaderRequestWrapper wrappedRequest = new CustomHeaderRequestWrapper(httpRequest);
//        chain.doFilter(wrappedRequest, response);
//
//    }
//}