package com.backend.web.rest.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class CORSFilter implements Filter {

    // This is to be replaced with a list of domains allowed to access the server
    private final List<String> allowedOrigins = Arrays.asList("servlet://localhost:8080","servlet://rest.localhost", "servlet://127.0.0.1:8080");

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        if (req instanceof HttpServletRequest && res instanceof HttpServletResponse) {
            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) res;

            String origin = request.getHeader("Origin");
            response.setHeader("Access-Control-Allow-Origin",allowedOrigins.contains(origin) ? origin : "");

            response.setHeader("Vary", "Origin");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Allow-Headers",
                    "Origin, X-Requested-With, Content-Type, Accept, " + Headers.REQUEST_HEADER_NAME);
        }
        chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig) {
    }
}
