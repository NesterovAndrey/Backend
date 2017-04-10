package config;

import com.backend.domain.query.INativeQueryBuilder;
import utils.calendarService.ICalendarWrapper;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryTestConfig {

    @Bean
    public INativeQueryBuilder nativeQueryBuilder()
    {
        return Mockito.mock(INativeQueryBuilder.class);
    }
    @Bean
    public ICalendarWrapper calendarWrapper()
    {
        return Mockito.mock(ICalendarWrapper.class);
    }
}
