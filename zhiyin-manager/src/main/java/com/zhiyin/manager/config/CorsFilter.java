package com.zhiyin.manager.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * @author momo
 */
@Slf4j
@Component
public class CorsFilter implements Filter {

    private String allowOrigin;
    private String allowMethods;
    private String allowCredentials;
    private String allowHeaders;
    private String exposeHeaders;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        allowOrigin = filterConfig.getInitParameter("allowOrigin");
        allowMethods = filterConfig.getInitParameter("allowMethods");
        allowCredentials = filterConfig.getInitParameter("allowCredentials");
        allowHeaders = filterConfig.getInitParameter("allowHeaders");
        exposeHeaders = filterConfig.getInitParameter("exposeHeaders");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        log.info("access cors filter.");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
//        if (StringUtils.isNotEmpty(allowOrigin)) {
//            List<String> allowOriginList = Arrays.asList(allowOrigin.split(","));
//            if (CollectionUtils.isNotEmpty(allowOriginList)) {
//                String currentOrigin = request.getHeader("Origin");
//                if (allowOriginList.contains(currentOrigin)) {
//                    response.setHeader("Access-Control-Allow-Origin", currentOrigin);
//                }
//            }
//        }
//        if (StringUtils.isNotEmpty(allowMethods)) {
//            response.setHeader("Access-Control-Allow-Methods", allowMethods);
//        }
//        if (StringUtils.isNotEmpty(allowCredentials)) {
//            response.setHeader("Access-Control-Allow-Credentials", allowCredentials);
//        }
//        if (StringUtils.isNotEmpty(allowHeaders)) {
//            response.setHeader("Access-Control-Allow-Headers", allowHeaders);
//        }
//        if (StringUtils.isNotEmpty(exposeHeaders)) {
//            response.setHeader("Access-Control-Expose-Headers", exposeHeaders);
//        }
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET,PUT, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
    }
}