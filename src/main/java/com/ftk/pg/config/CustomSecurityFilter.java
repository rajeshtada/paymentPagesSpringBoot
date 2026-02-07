//package com.ftk.getepaymentpages.config;
//
//import java.io.IOException;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.ftk.getepaymentpages.encryption.AESGCM;
//import com.ftk.getepaymentpages.encryption.EncryptedRequest;
//import com.ftk.getepaymentpages.exception.EncDecException;
//import com.ftk.getepaymentpages.modal.Properties;
//import com.ftk.getepaymentpages.repo.PropertiesRepo;
//import com.ftk.getepaymentpages.util.FilterUtils;
//import com.google.gson.Gson;
//
//import jakarta.servlet.Filter;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.FilterConfig;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//
//@Component
//@WebFilter(urlPatterns = "/*") // Adjust the URL pattern as needed
//public class CustomSecurityFilter implements Filter {
//
//    public static final Gson gson = new Gson();
//
//    @Autowired
//    private PropertiesRepo propertiesRepo;
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        // Optional filter initialization
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        EncryptedRequest requestWrapper = new EncryptedRequest(httpServletRequest);
//
//        String requestBody = requestWrapper.getBody();
//
//        if (requestBody == null || requestBody.isEmpty()) {
//            throw new EncDecException("Request body is empty");
//        }
//
//        Properties properties = Optional.ofNullable(propertiesRepo.findByPropertykey(FilterUtils.AES_GCM_MASTER_KEY))
//                .orElseThrow(() -> new EncDecException("Encryption key not found"));
//
//        try {
//            EncryptedRequest encRequest = gson.fromJson(requestBody, EncryptedRequest.class);
//
//            String decryptedData = Optional.ofNullable(AESGCM.decrypt(encRequest.getData(), properties.getPropertyValue()))
//                    .orElseThrow(() -> new EncDecException("Decryption failed or returned null"));
//
//            System.out.println("Decrypted Data: " + decryptedData);
//
//            // Continue the filter chain
//            chain.doFilter(request, response);
//        } catch (EncDecException e) {
//            throw new ServletException("Encryption/Decryption Error: " + e.getMessage(), e);
//        } catch (Exception e) {
//            throw new ServletException("Unexpected Error: " + e.getMessage(), e);
//        }
//    }
//
//    @Override
//    public void destroy() {
//        // Optional cleanup logic
//    }
//}


