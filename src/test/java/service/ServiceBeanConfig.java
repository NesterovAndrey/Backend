package service;

import com.backend.service.app.AppService;
import com.backend.service.app.IAppService;
import com.backend.service.install.IInstallService;
import com.backend.service.install.InstallService;
import com.backend.service.role.RoleService;
import com.backend.service.user.IUserService;
import com.backend.service.user.UserService;
import com.backend.service.userProfile.UserProfileService;
import com.backend.service.event.EventService;
import com.backend.service.event.IEventService;
import com.backend.service.metrics.IMetricsService;
import com.backend.service.metrics.MetricsService;
import com.backend.domain.query.INativeQueryBuilder;
import com.backend.service.session.ISessionService;
import com.backend.service.session.SessionService;
import utils.calendarService.ICalendarWrapper;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceBeanConfig {
    @Bean
    @Qualifier("appServiceTestTarget")
    public IAppService appServiceBean()
    {
        return new AppService();
    }
    @Bean
    @Qualifier("eventServiceTestTarget")
    public IEventService eventServiceBean()
    {
        return new EventService();
    }
    @Bean
    @Qualifier("sessionServiceTestTarget")
    public ISessionService sessionServiceBean()
    {
        return new SessionService();
    }
    @Bean
    @Qualifier("installServiceTestTarget")
    public IInstallService installServiceServiceBean()
    {
        return new InstallService();
    }
    @Bean
    @Qualifier("userServiceTestTarget")
    public IUserService userServiceBean()
    {
        return new UserService();
    }
    @Bean
    @Qualifier("roleServiceTestTarget")
    public RoleService userRoleService(){return new RoleService();}
    @Bean
    @Qualifier("profileServiceTestTarget")
    public UserProfileService userProfileService()
    {
        return new UserProfileService();
    }
    @Bean
    @Qualifier("metricsServiceTestTarget")
    public IMetricsService metricsService(){
        return new MetricsService();
    }
    @Bean
    public ICalendarWrapper calendarWrapper()
    {
        return Mockito.mock(ICalendarWrapper.class);
    }
    @Bean
    public INativeQueryBuilder nativeQueryBuilder()
    {
        return Mockito.mock(INativeQueryBuilder.class);
    }
}
