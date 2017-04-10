package utils.calendarService.timePeriod;

public class PeriodBuilder<T> implements IPeriodBuilder<T> {

    private T from;
    private T to;
    @Override
    public T getFrom() {
        return from;
    }

    @Override
    public IPeriodBuilder<T> setFrom(T from) {
        this.from=from;
        return this;
    }

    @Override
    public T getTo() {
        return this.to;
    }

    @Override
    public IPeriodBuilder<T> setTo(T to) {
        this.to=to;
        return this;
    }


}
