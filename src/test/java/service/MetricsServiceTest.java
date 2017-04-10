package service;

import com.backend.domain.metrcis.IMetricData;
import com.backend.service.metrics.IMetricsService;
import com.backend.domain.metrcis.MetricData;
import com.backend.domain.query.INativeQueryBuilder;
import com.backend.domain.query.IQuery;
import com.backend.domain.query.NativeQueryEnum;
import utils.calendarService.ICalendarWrapper;
import utils.precondition.IValidator;
import config.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes ={ QueryTestConfig.class,ValidatorTestConfig.class, MapperTestConfig.class,ServiceTestConfig.class,ServiceBeanConfig.class, RepositoryTestConfig.class})
public class MetricsServiceTest {

    @Autowired
    @Qualifier("metricsServiceTestTarget")
    private IMetricsService metricsService;
    @Autowired
    private INativeQueryBuilder nativeQueryBuilder;
    @Autowired
    private ICalendarWrapper calendarWrapper;
    @Autowired
    private IValidator validator;

    private IQuery query;
    @Before
    public void setUp()
    {

        query=Mockito.mock(IQuery.class);

        Mockito.when(nativeQueryBuilder.build()).thenReturn(query);
        Mockito.when(nativeQueryBuilder.setSqlQueryFromRegisteredString(Mockito.any(NativeQueryEnum.class))).thenReturn(nativeQueryBuilder);
        Mockito.when(nativeQueryBuilder.addParam(Mockito.anyString(),Mockito.any())).thenReturn(nativeQueryBuilder);

        Mockito.doThrow(new IllegalArgumentException()).when(validator).isTrue(Mockito.eq(false),Mockito.anyString());
        Mockito.doThrow(new NullPointerException()).when(validator).notNull(Mockito.isNull(),Mockito.anyString());
    }

    @Test
    public void testGetAU()
    {
        IMetricData metricData=new MetricData();
        metricData.addValue("value",1);
        Collection<IMetricData> obs=new ArrayList<>();
        obs.add(metricData);
        obs=Mockito.spy(obs);

        Mockito.when(query.<IMetricData>getData(Mockito.any())).thenReturn(obs);

        Collection<IMetricData> metricDataList=metricsService.getActiveUsers(NativeQueryEnum.DAU,new Date(),new Date());
        //Mockito.verify(query,Mockito.times(1)).getRawData();
        Assert.assertNotNull("------Metrics data must be not null",metricDataList);
        Assert.assertTrue("-------Metrics data size must be greater then 0",metricDataList.size()>0);
    }
    @Test(expected = NullPointerException.class)
    public void testGetAuNullDate()
    {
        Collection<IMetricData> metricDataList=metricsService.getActiveUsers(NativeQueryEnum.DAU,null,new Date());
        metricDataList=metricsService.getActiveUsers(NativeQueryEnum.DAU,new Date(),null);
    }
    @Test
    public void testGetRetention()
    {
        IMetricData metricData=new MetricData();
        metricData.addValue("day",1);
        metricData.addValue("active",1);
        metricData.addValue("retained",1);
        metricData.addValue("retention",1);
        Collection<IMetricData> obs=new ArrayList<>();
        obs.add(metricData);
        obs=Mockito.spy(obs);

        Mockito.when(query.<IMetricData>getData(Mockito.any())).thenReturn(obs);

        Collection<IMetricData> metricDataList=metricsService.getRetention(NativeQueryEnum.RETENTION,1);
        //Mockito.verify(query,Mockito.times(1)).getRawData();
        Assert.assertNotNull("------Metrics data must be not null",metricDataList);
        Assert.assertTrue("-------Metrics data size must be greater then 0",metricDataList.size()>0);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testGetRetentionDay0()
    {
        Collection<IMetricData> metricDataList=metricsService.getRetention(NativeQueryEnum.RETENTION,0);
        metricDataList=metricsService.getRetention(NativeQueryEnum.RETENTION,0);
    }

}
