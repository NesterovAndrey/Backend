package utils.precondition;


import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class Validator implements IValidator {
    @Override
    public <T> T notNull(T object) {
        return Validate.notNull(object);
    }

    @Override
    public <T> T notNull(T object, String message, Object... values) {
        return Validate.notNull(object,message,values);
    }

    @Override
    public <T> T notNull(T object, RuntimeException e) {
        try
        {
            return notNull(object);
        }
        catch (final NullPointerException exception)
        {
            throw e;
        }
    }

    @Override
    public void isTrue(boolean expression) {
        Validate.isTrue(expression);
    }

    @Override
    public void isTrue(boolean expression, String message, Object... values) {
        Logger logger= LoggerFactory.getLogger(this.getClass());
        logger.info("VALUE "+expression);
        Validate.isTrue(expression,message,values);
    }

    @Override
    public void isTrue(boolean expression, RuntimeException e) {
        try
        {
            isTrue(expression);
        }
        catch (final IllegalArgumentException exception)
        {
            throw e;
        }
    }

    @Override
    public <T extends Collection<?>> T notEmpty(T collection) {
        return Validate.notEmpty(collection);
    }

    @Override
    public <T extends Collection<?>> T notEmpty(T collection, String message, Object... values) {
        return Validate.notEmpty(collection,message,values);
    }
}
