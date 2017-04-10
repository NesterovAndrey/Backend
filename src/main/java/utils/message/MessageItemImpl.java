package utils.message;

public class MessageItemImpl implements MessageItem {
    private final String message;
    private final Object[] params;

    public MessageItemImpl()
    {
        message=null;
        params=new Object[]{};
    }
    public MessageItemImpl(String message)
    {
        this(message,new Object[]{});
    }
    public MessageItemImpl(String message, Object[] params)
    {
        this.message=message;
        this.params=params;
    }
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Object[] getParams() {
        return params;
    }

}
