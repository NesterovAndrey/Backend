package com.backend.config;

import com.backend.service.app.AppService;
import com.backend.service.app.IAppService;
import com.backend.service.install.IInstallService;
import com.backend.service.install.InstallService;
import com.backend.service.user.UserService;
import com.backend.service.event.EventService;
import com.backend.service.event.IEventService;
import com.backend.domain.metrcis.IMetricData;
import com.backend.domain.metrcis.MetricData;
import com.backend.service.session.ISessionService;
import com.backend.service.session.SessionService;
import org.springframework.stereotype.Service;
import utils.calendarService.CalendarWrapper;
import utils.calendarService.ICalendarWrapper;
import utils.message.MessageSource;
import utils.message.ResourceBundleMessages;

import utils.precondition.IValidator;
import utils.precondition.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import utils.requestBody.DataEncoder;
import utils.requestBody.MD5Encoder;

@EnableWebMvc
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@ComponentScan("com.backend")
public class AppConfig {

    @Bean
    @Scope("prototype")
    public IMetricData metricData()
    {
        return new MetricData();
    }

    @Bean
    public PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    @Bean
    public ResourceBundleMessageSource messageSource()
    {
        ResourceBundleMessageSource messageSource=new ResourceBundleMessageSource();
        messageSource.setBasename("exception/exception_messages");
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }
    @Bean
    @Autowired
    public MessageSource mSource(ResourceBundleMessageSource messageSource)
    {
        return new ResourceBundleMessages(messageSource);
    }
    @Bean
    @Scope("prototype")
    public DataEncoder dataEncoder()
    {
        return new MD5Encoder();
    }
    @Bean
    @Scope("prototype")
    public ICalendarWrapper calendarWrapper()
    {
        return new CalendarWrapper();
    }
    @Bean
    public IAppService appService()
    {
        return new AppService();
    }
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public ISessionService sessionService()
    {
        return new SessionService();
    }
    @Bean
    public IEventService eventService()
    {
        return new EventService();
    }
    @Bean
    public IInstallService installService()
    {
        return new InstallService();
    }
    @Bean
    public UserService userService()
    {
        return new UserService();
    }
    @Bean
    public IValidator validator()
    {
        return new Validator();
    }


}
