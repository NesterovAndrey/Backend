package utils.calendarService;

import java.time.DateTimeException;
import java.time.Month;
import java.util.Formatter;

public final class Day {
    private final int year;
    private final Month month;
    private final int day;

    public Day(int year, int month, int day) throws DateTimeException
    {
        this.year=year;
        this.month=Month.of(month);
        this.day=validateDay(day,this.month);
    }
    private int validateDay(int day,Month month) throws DateTimeException
    {
        if(day<1 || day>month.length(((float)year/4)==0))
        {
            Formatter formatter=new Formatter();
            throw new DateTimeException(formatter.format("Day $d is not valid for month $s",day,month.name()).toString());
        }
        return day;
    }

    public int getYear() {
        return year;
    }

    public Month getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

}
