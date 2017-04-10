package com.backend.web.rest.exception;


import com.backend.web.rest.exception.response.RestResponseHandler;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;

public class ExceptionHandler implements IExceptionHandler {

    private ExceptionResolver exceptionResolver;
    private RestResponseHandler responseHandler;

    public ExceptionHandler(ExceptionResolver exceptionResolver, RestResponseHandler responseHandler) {
        this.exceptionResolver = exceptionResolver;
        this.responseHandler = responseHandler;
    }

    @Override
    public void handle(ServletWebRequest webRequest, Exception exception) throws IOException {
        RestException restException = exceptionResolver.resolve(webRequest, null, exception);
        responseHandler.handleResponse(restException, webRequest);

    }
}
