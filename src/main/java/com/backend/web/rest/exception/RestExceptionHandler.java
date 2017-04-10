package com.backend.web.rest.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestExceptionHandler extends AbstractHandlerExceptionResolver  {

    //private ExceptionResolver exceptionResolver;
   // private RestResponseHandler responseHandler;
    private final IExceptionHandler exceptionHandler;
    public RestExceptionHandler(IExceptionHandler exceptionHandler)//ExceptionResolver exceptionResolver,RestResponseHandler responseHandler)
    {
        super();
        setOrder(0);
        //this.exceptionResolver=exceptionResolver;
        //this.responseHandler=responseHandler;
        this.exceptionHandler=exceptionHandler;
    }


    @Override
    protected ModelAndView doResolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception exception) {
       Logger log= LoggerFactory.getLogger(RestExceptionHandler.class);
        ServletWebRequest webRequest=new ServletWebRequest(httpServletRequest,httpServletResponse);
        //RestException restException=exceptionResolver.resolve(webRequest,handler,exception);
        try {
            this.exceptionHandler.handle(webRequest,exception);
            //responseHandler.handleResponse(restException,webRequest);
        } catch (IOException e) {
            return null;
        }
        return new ModelAndView();
    }

/*
    public ExceptionResolver getExceptionResolver() {
        return exceptionResolver;
    }

    public void setExceptionResolver(ExceptionResolver exceptionResolver) {
        this.exceptionResolver = exceptionResolver;
    }

    public RestResponseHandler getResponseHandler() {
        return responseHandler;
    }

    public void setResponseHandler(RestResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
    }*/
}
