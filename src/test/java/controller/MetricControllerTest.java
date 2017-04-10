package controller;

import com.backend.config.ExceptionResolverConfig;
import com.backend.domain.application.Application;
import com.backend.domain.authenticationUser.User;
import com.backend.domain.metrcis.IMetricData;
import com.backend.service.metrics.IMetricsService;
import com.backend.domain.metrcis.MetricData;
import com.backend.domain.query.NativeQueryEnum;
import utils.mapping.IMapperUtil;
import com.backend.web.validation.IResourceValidator;
import com.backend.web.rest.controller.metrics.MetricsController;
import config.MapperTestConfig;
import config.QueryTestConfig;
import config.ServiceTestConfig;
import config.ValidatorTestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes ={ControllerTestConfig.class, ValidatorTestConfig.class, MapperTestConfig.class, QueryTestConfig.class, ServiceTestConfig.class, ExceptionResolverConfig.class})
@WebAppConfiguration
public class MetricControllerTest {

    private HttpMessageConverter mappingJackson2HttpMessageConverter=new MappingJackson2HttpMessageConverter();

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MetricsController metricsController;
    @Autowired
    private HandlerExceptionResolver restExceptionHandler;
    @Autowired
    private IMetricsService metricsService;

    @Autowired
    private IMapperUtil mapperUtil;
    @Autowired
    private IResourceValidator<User,String> userValidator;
    @Autowired
    private IResourceValidator<Application,String> appValidator;

    private MockMvc mockMvc;

    private MediaType mediaType=new MediaType(MediaType.APPLICATION_JSON.getType(),
    MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Before
    public void setup()
    {
        mockMvc=MockMvcBuilders.standaloneSetup(metricsController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setHandlerExceptionResolvers(restExceptionHandler).build();
        mediaType=MediaType.APPLICATION_JSON;
    }
    @Test
    public void testGetDAU() throws Exception {
        Calendar calendar=Calendar.getInstance();
        calendar.set(2017,12,31);
        Date dateTo=calendar.getTime();
        dateTo=Mockito.spy(dateTo);
        calendar.set(2017,01,01);
        Date dateFrom=calendar.getTime();
        dateFrom=Mockito.spy(dateFrom);
        Collection<IMetricData> datas=new ArrayList<>();
        MetricData data=new MetricData();
        data.addValue("day",dateFrom);
        data.addValue("count",5);
        datas.add(data);
        data=new MetricData();
        data.addValue("day",dateFrom);
        data.addValue("count",5);
        datas.add(data);
        datas=Mockito.spy(datas);

        Mockito.when(metricsService.getActiveUsers(Mockito.eq(NativeQueryEnum.DAU),Mockito.any(Date.class),Mockito.any(Date.class))).thenReturn(datas);
        mockMvc.perform(post("/private/application/{appID}/metrics/dau","1").param("from","01-01-2017").param("to","12-31-2017").contentType(mediaType))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(2)));;
    }
    @Test
    public void testGetRetention() throws Exception {
        Calendar calendar=Calendar.getInstance();
        calendar.set(2017,12,31);
        Date dateTo=calendar.getTime();
        dateTo=Mockito.spy(dateTo);
        calendar.set(2017,01,01);
        Date dateFrom=calendar.getTime();
        dateFrom=Mockito.spy(dateFrom);
        Collection<IMetricData> datas=new ArrayList<>();
        MetricData data=new MetricData();
        data.addValue("date",dateFrom);
        data.addValue("active",5);
        data.addValue("retained",1);
        data.addValue("retention",0.2);
        datas.add(data);
        data=new MetricData();
        data.addValue("date",dateTo);
        data.addValue("active",10);
        data.addValue("retained",9);
        data.addValue("retention",0.9);
        datas.add(data);
        datas=Mockito.spy(datas);

        Mockito.when(metricsService.getRetention(Mockito.eq(NativeQueryEnum.RETENTION),Mockito.eq(1))).thenReturn(datas);
        mockMvc.perform(post("/private/application/{appID}/metrics/retention","1").param("day","1").contentType(mediaType))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(2)));;
    }
    protected String toJSon(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
