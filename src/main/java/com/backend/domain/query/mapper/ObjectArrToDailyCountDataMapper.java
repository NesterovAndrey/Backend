package com.backend.domain.query.mapper;

import com.backend.domain.metrcis.IMetricData;
import com.backend.domain.metrcis.MetricData;
import utils.calendarService.ICalendarWrapper;

import java.math.BigInteger;
import java.util.Date;

public class ObjectArrToDailyCountDataMapper implements IQueryResultMapper<IMetricData> {

    private final ICalendarWrapper calendarService;
    public ObjectArrToDailyCountDataMapper(ICalendarWrapper calendarService)
    {
        this.calendarService=calendarService;
    }
    @Override
    public IMetricData map(Object[] rawColumn) {
        calendarService.setTime((Date)rawColumn[0]);
        IMetricData data=new MetricData();
        data.addValue("day",calendarService.getDayFromCalendar());
        data.addValue("value",((BigInteger)rawColumn[1]).intValue());
        return data;
    }
}
