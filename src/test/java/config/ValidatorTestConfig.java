package config;

import com.backend.domain.application.Application;
import com.backend.domain.application.applicationInstall.IApplicationInstall;
import com.backend.domain.authenticationUser.User;
import com.backend.domain.event.Event;
import com.backend.domain.session.Session;
import com.backend.web.validation.IResourceValidator;
import utils.precondition.IValidator;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidatorTestConfig {
    @Bean
    public IValidator validator()
    {
        return Mockito.mock(IValidator.class);
    }
    @Bean
    public IResourceValidator<Application,String> appResourceValidator()
    {
        return Mockito.mock(IResourceValidator.class);
    }
    @Bean
    public IResourceValidator<Event,Long> eventResourceValidator()
    {
        return Mockito.mock(IResourceValidator.class);
    }
    @Bean
    public IResourceValidator<Session,Long> sessionResourceValidator()
    {
        return Mockito.mock(IResourceValidator.class);
    }
    @Bean
    public IResourceValidator<IApplicationInstall,String> installResourceValidator()
    {
        return Mockito.mock(IResourceValidator.class);
    }
    @Bean
    public IResourceValidator<User,String> userResourceValidator()
    {
        return Mockito.mock(IResourceValidator.class);
    }
}
