package com.backend.web.rest.exception;


import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;

public interface IExceptionHandler {
    void handle(ServletWebRequest webRequest,Exception exception) throws IOException;
}
