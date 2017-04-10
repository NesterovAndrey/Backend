package config;

import com.backend.repository.*;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
public class RepositoryTestConfig {
    @Bean
    public SessionRepository sessionRepository()
    {
        return Mockito.mock(SessionRepository.class);
    }
    @Bean
    public AppRepository appRepository()
    {
        return Mockito.mock(AppRepository.class);
    }
    @Bean
    public EventRepository eventRepository()
    {
        return Mockito.mock(EventRepository.class);
    }
    @Bean
    public InstallRepository installRepository()
    {
        return Mockito.mock(InstallRepository.class);
    }
    @Bean
    public ProfileRepository profileRepository()
    {
        return Mockito.mock(ProfileRepository.class);
    }
    @Bean
    public RoleRepository roleRepository()
    {
        return Mockito.mock(RoleRepository.class);
    }
    @Bean
    public UserRepository userRepository()
    {
        return Mockito.mock(UserRepository.class);
    }
}
