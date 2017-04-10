package com.backend.web.rest.exception;

import utils.message.MessageItem;
import utils.message.MessageItemImpl;
import utils.message.MessageSource;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Formatter;
import java.util.Map;

/**
 *
 * Converts RuntimeException to JSON exception message
 */
public class DefaultExceptionResolver implements ExceptionResolver {

    @Autowired
    private MessageSource messageSource;
    private final Map<String,RestExceptionBuilder> exceptionBindings;

    public DefaultExceptionResolver(Map<String,RestExceptionBuilder> bindings,MessageSource messageSource)
    {
        this.exceptionBindings=bindings;
        this.messageSource=messageSource;
    }

    @Override
    public RestException resolve(ServletWebRequest request, Object handler, Exception exception) {
        return new RestException(getBindingForException(exception));
    }

    private RestExceptionBuilder getBindingForException(Exception exception)
    {
        assert exceptionBindings!=null;
        Logger log= LoggerFactory.getLogger(this.getClass());
        log.info("MESSAGE SOURCE "+(messageSource==null));
        RestExceptionBuilder builder=exceptionBindings.get(exception.getClass().toString());
        if(builder == null)
        {
            //Добавить дефолтные значения
            builder=new RestExceptionBuilder();
            builder.setCode(99);
            builder.setStatus(404);
            StringWriter writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter( writer );
            exception.printStackTrace( printWriter );
            printWriter.flush();

            builder.setMessage( writer.toString());
            builder.setDeveloperMessage(messageSource.getMessage(new MessageItemImpl("exception.internal"))+exception.getClass().toString());

        }
        Logger logger=LoggerFactory.getLogger(this.getClass());
        logger.info(new Formatter().format("-------- ERROR MESSAGE: %s \nSTACKTRACE: %s",exception.getMessage(), ExceptionUtils.getStackTrace(exception)).toString());
        MessageItem messageItem=exception instanceof MessageItem?(MessageItem)exception:new MessageItemImpl("exception.exception");
        builder.setDeveloperMessage(messageSource.getMessage(messageItem));
        return builder;
    }
}
