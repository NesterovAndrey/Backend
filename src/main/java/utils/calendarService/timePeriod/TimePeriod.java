package utils.calendarService.timePeriod;

public final class TimePeriod<T extends Comparable<T>> {
    private final T from;
    private final T to;
    public TimePeriod()
    {
        this.from=this.to=null;
    }
    public TimePeriod(IPeriodBuilder<T> builder)
    {
        this.from=builder.getFrom();
        this.to=builder.getTo();
    }

    public T getFrom() {
        return from;
    }

    public T getTo() {
        return to;
    }
}
