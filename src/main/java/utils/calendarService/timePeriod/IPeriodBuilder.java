package utils.calendarService.timePeriod;

/**
 * Created by yalta on 10.03.2017.
 */
public interface IPeriodBuilder<T> {
    T getFrom();
    IPeriodBuilder<T> setFrom(T from);
    T getTo();
    IPeriodBuilder<T> setTo(T to);

}
