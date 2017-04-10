package utils.calendarService;

import java.time.DateTimeException;

public class Month implements Comparable<Month> {

    private final java.time.Month month;
    private final int year;
    public Month(int month,int year) throws DateTimeException
    {
        this.month= java.time.Month.of(month);
        this.year=year;
    }

    public java.time.Month getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    @Override
    public int compareTo(Month o) {
        return 0;
    }
}
