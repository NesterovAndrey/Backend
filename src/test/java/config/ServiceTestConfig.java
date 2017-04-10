package config;

import com.backend.service.app.IAppService;
import com.backend.service.install.IInstallService;
import com.backend.service.role.IRoleService;
import com.backend.service.user.IUserService;
import com.backend.service.userProfile.IUserProfileService;
import com.backend.service.event.IEventService;
import com.backend.service.metrics.IMetricsService;
import com.backend.service.session.ISessionService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceTestConfig {
    @Bean
    public ISessionService sessionService() {
        return Mockito.mock(ISessionService.class);
    }
    @Bean
    public IAppService appService() {
        return Mockito.mock(IAppService.class);
    }
    @Bean
    public IInstallService installService(){return Mockito.mock(IInstallService.class);}
    @Bean
    public IEventService eventService()
    {
        return Mockito.mock(IEventService.class);
    }
    @Bean
    public IUserService userService()
    {
        return Mockito.mock(IUserService.class);
    }
    @Bean
    public IUserProfileService profileService(){return Mockito.mock(IUserProfileService.class);}
    @Bean
    public IRoleService roleService(){return Mockito.mock(IRoleService.class);}
    @Bean
    public IMetricsService metricsService(){
        return Mockito.mock(IMetricsService.class);
    }

}
