package com.backend.web.rest.filter;


import com.backend.web.rest.exception.IExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Autowired
    private IExceptionHandler exceptionHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
            try
            {
                doFilter(httpServletRequest,httpServletResponse,filterChain);
            }
            catch (Exception e)
            {
                exceptionHandler.handle(new ServletWebRequest(httpServletRequest,httpServletResponse),e);
            }
    }
}
