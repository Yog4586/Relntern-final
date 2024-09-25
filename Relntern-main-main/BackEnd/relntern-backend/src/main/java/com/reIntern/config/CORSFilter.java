package com.reIntern.config;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class CORSFilter implements Filter {
@Autowired
public Environment env;

private static final Logger LOGGER = LogManager.getLogger(CORSFilter.class);

public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
throws IOException, ServletException {

try {
HttpServletResponse response = (HttpServletResponse) res;

// response.setHeader("Access-Control-Allow-Origin", env.getProperty("dns").toString());
// response.setHeader("Access-Control-Allow-Credentials", "true");
// response.setHeader("Access-Control-Allow-Methods", "POST, GET");
// response.setHeader("Access-Control-Max-Age", "3600");
// response.setHeader("Access-Control-Allow-Headers", "jwttoken,usertoken");

   response.setHeader("Access-Control-Allow-Origin","*");
   response.setHeader("Access-Control-Allow-Credentials", "true");
   response.setHeader("Access-Control-Allow-Methods", "POST, GET");
   response.setHeader("Access-Control-Max-Age", "3600");
   response.setHeader("Access-Control-Allow-Headers", "*");

chain.doFilter(req, res);
} catch (ServletException e) {
LOGGER.error(e.toString());
}

}

@Override
public void init(FilterConfig filterConfig) {
}

@Override
public void destroy() {
}

}
