package com.backend.web.rest.exception.response;

import com.backend.web.rest.exception.RestException;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;
import java.util.List;

public interface RestResponseHandler {
   void handleResponse(RestException restException, ServletWebRequest webRequest) throws IOException;
    List<HttpMessageConverter<?>> getMessageConverters();
}
