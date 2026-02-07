package com.ftk.pg.config;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.util.*;

public class CustomHeaderRequestWrapper extends HttpServletRequestWrapper {

    private final Map<String, String> customHeaders = new HashMap<>();

    public CustomHeaderRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    public void addHeader(String name, String value) {
        customHeaders.put(name, value);
    }

    @Override
    public String getHeader(String name) {
        String headerValue = customHeaders.get(name);
        return (headerValue != null) ? headerValue : super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        Set<String> headerNames = new HashSet<>(customHeaders.keySet());
        Enumeration<String> parentHeaderNames = super.getHeaderNames();
        while (parentHeaderNames.hasMoreElements()) {
            headerNames.add(parentHeaderNames.nextElement());
        }
        return Collections.enumeration(headerNames);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        if (customHeaders.containsKey(name)) {
            return Collections.enumeration(Collections.singletonList(customHeaders.get(name)));
        }
        return super.getHeaders(name);
    }
}