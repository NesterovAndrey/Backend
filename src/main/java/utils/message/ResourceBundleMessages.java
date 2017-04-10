package utils.message;

import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

public class ResourceBundleMessages implements MessageSource {
    private final ResourceBundleMessageSource messageSource;
    public ResourceBundleMessages(ResourceBundleMessageSource messageSource)
    {
        this.messageSource=messageSource;
    }
    @Override
    public String getMessage(MessageItem messageItem) {
        return messageSource.getMessage(messageItem.getMessage(),messageItem.getParams(), Locale.US);
    }
}
