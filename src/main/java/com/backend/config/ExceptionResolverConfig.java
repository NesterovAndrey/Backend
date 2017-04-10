package com.backend.config;

import com.backend.web.rest.exception.*;
import com.backend.web.rest.exception.ValidationException;
import com.backend.web.rest.exception.converter.ExceptionConverter;
import com.backend.web.rest.exception.converter.MapExceptionConverter;
import com.backend.web.rest.exception.response.RestResponseHandler;
import com.backend.web.rest.exception.response.RestResponseHandlerImpl;
import utils.message.MessageItemImpl;
import utils.message.MessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ExceptionResolverConfig {
    @Bean
    @Autowired
    public RestExceptionHandler restExceptionHandler(MessageSource messageSource) {
        return new RestExceptionHandler(exceptionHandler(messageSource));
    }
    @Bean
    @Autowired
    public IExceptionHandler exceptionHandler(MessageSource messageSource)
    {
        return new ExceptionHandler(exceptionResolver(messageSource),responseHandler());
    }

    @Bean
    public ExceptionResolver exceptionResolver(MessageSource messageSource) {
        Map<String, RestExceptionBuilder> exceptionMappings = new HashMap<>();
        ///400///
        exceptionMappings.put(WrongRequestException.class.toString(),getBuilder(HttpStatus.BAD_REQUEST.value(),40001,getMessage(messageSource,"exception.wrong_request")));
        exceptionMappings.put(ValidationException.class.toString(),getBuilder(HttpStatus.BAD_REQUEST.value(),40002,getMessage(messageSource,"exception.wrong_data_input")));
        exceptionMappings.put(javax.validation.ValidationException.class.toString(),getBuilder(HttpStatus.BAD_REQUEST.value(),40003,getMessage(messageSource,"exception.wrong_data_input")));
        exceptionMappings.put(HttpMessageNotReadableException.class.toString(),getBuilder(HttpStatus.BAD_REQUEST.value(),40004,getMessage(messageSource,"exception.request_body_is_empty")));

        ///401///
        exceptionMappings.put(NotAuthorizedException.class.toString(),getBuilder(HttpStatus.UNAUTHORIZED.value(),40101,getMessage(messageSource,"exception.authorization.not_authorized")));

        ///403///
        exceptionMappings.put(AuthenticationFailed.class.toString(),getBuilder(HttpStatus.FORBIDDEN.value(),40301,getMessage(messageSource,"exception.authorization.failed")));
        exceptionMappings.put(BadCredentialsException.class.toString(),getBuilder(HttpStatus.FORBIDDEN.value(),40302,getMessage(messageSource,"exception.authorization.bad_credentials")));
        ///404///

        exceptionMappings.put(NoHandlerFoundException.class.toString(), getBuilder(HttpStatus.NOT_FOUND.value(),40400,getMessage(messageSource,"exception.url_not_found")));
        exceptionMappings.put(MethodNotSupportedException.class.toString(), getBuilder(HttpStatus.NOT_FOUND.value(),40401,getMessage(messageSource,"exception.not_supported")));
        exceptionMappings.put(NotFoundException.class.toString(),getBuilder(HttpStatus.NOT_FOUND.value(),40402,getMessage(messageSource, "exception.resource.not_found")));
        exceptionMappings.put(RuntimeException.class.toString(), getBuilder(HttpStatus.NOT_FOUND.value(),40499,getMessage(messageSource,"exception.error")));

        return new DefaultExceptionResolver(exceptionMappings,messageSource);
    }

    private RestExceptionBuilder getBuilder(int status,int code,String message)
    {
        RestExceptionBuilder builder=new RestExceptionBuilder();
        builder.setStatus(status);
        builder.setCode(code);
        builder.setMessage(message);
        return builder;
    }
    private String getMessage(MessageSource messageSource,String messageName)
    {
        return messageSource.getMessage(new MessageItemImpl(messageName));
    }
    @Bean
    public RestResponseHandler responseHandler(){
        List<HttpMessageConverter<?>> messageConverters= new ArrayList<>();
        putConverters(messageConverters);
        return new RestResponseHandlerImpl<>(messageConverters, exceptionConverter());
    }
    private void putConverters(List<HttpMessageConverter<?>> messageConverters)
    {
        messageConverters.add(jacksonConverter());
    }
    private MappingJackson2HttpMessageConverter jacksonConverter()
    {
        return new MappingJackson2HttpMessageConverter();
    }
    @Bean
    public ExceptionConverter<Map<String,String>,RestException> exceptionConverter()
    {
        return new MapExceptionConverter();
    }
}
