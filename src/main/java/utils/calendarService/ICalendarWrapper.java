package utils.calendarService;

import java.util.Date;


public interface ICalendarWrapper {

    void setTime(int year, int month, int day);
    void setTime(Date date);
    void setTime(long milliseconds);

    Day getDayFromCalendar();
    WeekOfYear getWeekFromCalendar();
    Month getMonthFromCalendar();
    Year getYearFromCalendar();


}
