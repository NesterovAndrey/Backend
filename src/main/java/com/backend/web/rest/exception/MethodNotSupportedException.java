package com.backend.web.rest.exception;


import utils.message.MessageItem;

public class MethodNotSupportedException extends DeveloperMessageException{
    MethodNotSupportedException(MessageItem messageItem)
    {
        super(messageItem);
    }
}
