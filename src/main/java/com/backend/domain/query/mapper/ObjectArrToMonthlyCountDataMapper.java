package com.backend.domain.query.mapper;

import com.backend.domain.metrcis.IMetricData;
import com.backend.domain.metrcis.MetricData;
import utils.calendarService.ICalendarWrapper;
import utils.calendarService.Month;

import java.math.BigInteger;

public class ObjectArrToMonthlyCountDataMapper implements IQueryResultMapper<IMetricData> {

    private final ICalendarWrapper calendarService;
    public ObjectArrToMonthlyCountDataMapper(ICalendarWrapper calendarService)
    {
        this.calendarService=calendarService;
    }
    @Override
    public IMetricData map(Object[] rawColumn) {
        IMetricData data=new MetricData();
        data.addValue("month",new Month(((BigInteger)rawColumn[0]).intValue()-1,((BigInteger)rawColumn[1]).intValue()));
        data.addValue("value",((BigInteger)rawColumn[2]).intValue());
        return data;
    }
}
