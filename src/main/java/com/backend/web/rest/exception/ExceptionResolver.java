package com.backend.web.rest.exception;


import org.springframework.web.context.request.ServletWebRequest;

public interface ExceptionResolver {
        RestException resolve(ServletWebRequest request, Object handler, Exception exception);
}
