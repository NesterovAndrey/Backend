package com.backend.web.rest.exception.response;


import com.backend.web.rest.exception.converter.ExceptionConverter;
import com.backend.web.rest.exception.RestException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RestResponseHandlerImpl<T> implements RestResponseHandler{
    private List<HttpMessageConverter<?>> messageConverters= new ArrayList<>();
    private final ExceptionConverter<T,RestException> exceptionConverter;

    public RestResponseHandlerImpl(List<HttpMessageConverter<?>> messageConverters,ExceptionConverter<T,RestException> exceptionConverter)
    {
        this.messageConverters=messageConverters;
        this.exceptionConverter=exceptionConverter;
    }

    @Override
    public void handleResponse(RestException restException, ServletWebRequest webRequest) throws IOException {

        T preparedData=exceptionConverter.convert(restException);
        List<MediaType> mediaTypes=getMediaTypes(webRequest);
        webRequest.getResponse().setStatus(restException.getStatus());
        tryToWrite(preparedData,mediaTypes,webRequest);
    }


    private void tryToWrite(T preparedData, List<MediaType> mediaTypes, ServletWebRequest webRequest) throws IOException {
        for(MediaType mediaType:mediaTypes) {
            HttpMessageConverter converter=getConverter(preparedData.getClass(),mediaType);

            if(converter!=null)
            {
                converter.write(preparedData,mediaType,new ServletServerHttpResponse(webRequest.getResponse()));
                return;
            }
        }
    }
    private HttpMessageConverter getConverter(Class clazz,MediaType mediaType)
    {
        for (HttpMessageConverter converter : getMessageConverters()) {
            if(converter.canWrite(clazz,mediaType))
            {
                return converter;
            }
        }
        return null;
    }
    private List<MediaType> getMediaTypes(ServletWebRequest webRequest)
    {
        return new ServletServerHttpRequest(webRequest.getRequest()).getHeaders().getAccept();
    }
    public List<HttpMessageConverter<?>> getMessageConverters() {
        return messageConverters;
    }



    public ExceptionConverter<T, RestException> getExceptionConverter() {
        return exceptionConverter;
    }




}
