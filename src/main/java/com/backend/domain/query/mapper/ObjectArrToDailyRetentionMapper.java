package com.backend.domain.query.mapper;

import com.backend.domain.metrcis.IMetricData;
import com.backend.domain.metrcis.MetricData;
import utils.calendarService.ICalendarWrapper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class ObjectArrToDailyRetentionMapper implements IQueryResultMapper<IMetricData> {
    private final ICalendarWrapper calendarService;
    public  ObjectArrToDailyRetentionMapper(ICalendarWrapper calendarService)
    {
        this.calendarService=calendarService;
    }
    @Override
    public IMetricData map(Object[] rawColumn) {
        calendarService.setTime((Date)rawColumn[0]);
        IMetricData data=new MetricData();
        data.addValue("date",calendarService.getDayFromCalendar());
        data.addValue("active",((BigInteger)rawColumn[1]).intValue());
        data.addValue("retained",((BigInteger)rawColumn[2]).intValue());
        data.addValue("retention",((BigDecimal)rawColumn[3]).floatValue());
        return data;
    }
}
