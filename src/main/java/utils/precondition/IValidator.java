package utils.precondition;

import java.util.Collection;

public interface IValidator {
     <T> T	notNull(T object);
     <T> T	notNull(T object, String message, Object... values);
     <T> T	notNull(T object, RuntimeException e);
    void	isTrue(boolean expression);
    void	isTrue(boolean expression, String message, Object... values);
    void	isTrue(boolean expression, RuntimeException e);
    <T extends Collection<?>> T	notEmpty(T collection);
    <T extends Collection<?>> T	notEmpty(T collection, String message, Object... values);
}
