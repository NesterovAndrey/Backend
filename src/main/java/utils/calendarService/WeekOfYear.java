package utils.calendarService;


public class WeekOfYear {

    private final int weekNum;
    private final int year;
    public WeekOfYear(int weekNum, int year)
    {
       this.weekNum=weekNum;
       this.year=year;
    }

    public int getWeekNum() {
        return weekNum;
    }

    public int getYear() {
        return year;
    }
}
