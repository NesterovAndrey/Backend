package utils.calendarService;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;


public class CalendarWrapper implements ICalendarWrapper {

    private final Calendar calendar;


    public CalendarWrapper()
    {
        calendar=Calendar.getInstance();
    }

    public void setTime(int year, int month, int day)
    {
        calendar.set(year,month,day);
    }
    public void setTime(Date date)
    {
        calendar.setTime(date);
    }
    public void setTime(long milliseconds)
    {
        calendar.setTimeInMillis(milliseconds);
    }

    public Day getDayFromCalendar()
    {
        return new Day(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.DAY_OF_MONTH));
    }
    public WeekOfYear getWeekFromCalendar()
    {
        return new WeekOfYear(calendar.get(Calendar.WEEK_OF_YEAR),calendar.get(Calendar.YEAR));
    }
    public Month getMonthFromCalendar()
    {
        return new Month(calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.YEAR));
    }

    @Override
    public Year getYearFromCalendar() {
        return new Year(calendar.get(Calendar.YEAR));
    }
}
