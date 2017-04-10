package controller;


import com.backend.web.rest.controller.accounts.AccountsController;
import com.backend.web.rest.controller.application.ApplicationController;
import com.backend.web.rest.controller.event.EventPrivateController;
import com.backend.web.rest.controller.event.EventPublicController;
import com.backend.web.rest.controller.metrics.MetricsController;
import com.backend.web.rest.controller.install.InstallPublicController;
import com.backend.web.rest.controller.role.UserRolesController;
import com.backend.web.rest.controller.session.SessionPrivateController;
import com.backend.web.rest.controller.session.SessionPublicController;
import utils.message.MessageSource;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class ControllerTestConfig {

    @Bean
    public SessionPublicController publicSessionController() {
        return new SessionPublicController();
    }

    @Bean
    public SessionPrivateController privateSessionController() {
        return new SessionPrivateController();
    }

    @Bean
    public MessageSource mSource() {
        return Mockito.mock(MessageSource.class);
    }

    @Bean
    public EventPublicController publicEventController()
    {
        return new EventPublicController();
    }
    @Bean
    public EventPrivateController privateEventController()
    {
        return new EventPrivateController();
    }
    @Bean
    public InstallPublicController AppInstallController()
    {
        return new InstallPublicController();
    }

    @Bean
    public ApplicationController appController()
    {
        return new ApplicationController();
    }

    @Bean
    public AccountsController accController()
    {
        return new AccountsController();
    }
    @Bean
    public MetricsController metricsController(){return new MetricsController();}
    @Bean
    public UserRolesController userRolesController()
    {
        return new UserRolesController();
    }



}
