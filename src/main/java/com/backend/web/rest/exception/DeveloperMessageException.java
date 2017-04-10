package com.backend.web.rest.exception;

import utils.message.MessageItem;
import utils.message.MessageItemImpl;

public class DeveloperMessageException extends RuntimeException implements MessageItem{

    private final MessageItem messageItem;
    public DeveloperMessageException()
    {
        super();
        this.messageItem=new MessageItemImpl("",new Object[]{});
    }
    public DeveloperMessageException(MessageItem messageItem)
    {
        this.messageItem=messageItem;
    }

    @Override
    public String getMessage() {
        return this.messageItem.getMessage();
    }


    @Override
    public Object[] getParams() {
        return this.messageItem.getParams();
    }

}
