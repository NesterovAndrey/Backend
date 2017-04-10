package utils.calendarService;

public class Year{

    private final int year;
    public Year(int year)
    {
        this.year=year;
    }

    public boolean isLeapYear()
    {
        return 0f==year%4;
    }
    public int getYear() {
        return year;
    }
}
