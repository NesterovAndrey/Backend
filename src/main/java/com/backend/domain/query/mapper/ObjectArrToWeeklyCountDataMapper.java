package com.backend.domain.query.mapper;

import com.backend.domain.metrcis.IMetricData;
import com.backend.domain.metrcis.MetricData;
import utils.calendarService.WeekOfYear;

import java.math.BigInteger;

public class ObjectArrToWeeklyCountDataMapper implements IQueryResultMapper<IMetricData> {

    @Override
    public IMetricData map(Object[] rawColumn) {
        IMetricData metricData=new MetricData();
        metricData.addValue("week",new WeekOfYear(((BigInteger)rawColumn[0]).intValue(),((BigInteger)rawColumn[1]).intValue()));
        metricData.addValue("value",((BigInteger)rawColumn[2]).intValue());
        return metricData;
    }
}
