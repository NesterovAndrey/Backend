package controller.session;

import com.backend.domain.application.Application;
import com.backend.service.app.IAppService;
import com.backend.service.session.ISessionService;
import com.backend.domain.session.SessionDTO;
import com.backend.web.validation.IResourceValidator;
import com.backend.web.rest.controller.session.SessionPrivateController;
import com.backend.web.authentication.JpaUserDetails;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringJUnit4ClassRunner.class)
public class SessionPrivateControllerTest extends BaseSessionTest {


    @Autowired
    private ISessionService sessionService;
    @Autowired
    private IAppService appService;
    @Autowired
    private IResourceValidator<Application,String> appValidator;
    @Autowired
    private SessionPrivateController sessionPrivateController;
    @Autowired
    private HandlerExceptionResolver restExceptionHandler;

    @Before
    public void init()
    {
        mockMvc= MockMvcBuilders.standaloneSetup(sessionPrivateController).setHandlerExceptionResolvers(restExceptionHandler).build();
    }
    @Test
    public void testGetAllSessions() throws Exception {
        Application app=Mockito.mock(Application.class);
        Mockito.when(sessionService.findByApp(Mockito.any(Application.class))).thenReturn(Collections.singletonList(session));
        Mockito.when(mapperUtil.map(Collections.singletonList(session),SessionDTO.class)).thenReturn(Collections.singletonList(sessionDTO));
        JpaUserDetails jpaUserDetails=new JpaUserDetails();
        TestingAuthenticationToken testingAuthenticationToken=new TestingAuthenticationToken(jpaUserDetails,null);
        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);

        mockMvc.perform(get("/private/application/{appID}/session","1").contentType(mediaType).principal(testingAuthenticationToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(1)));


    }
    @Test
    public void testGetSession() throws Exception {
        JpaUserDetails jpaUserDetails=new JpaUserDetails();
        TestingAuthenticationToken testingAuthenticationToken=new TestingAuthenticationToken(jpaUserDetails,null);
        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);

        Mockito.when(sessionService.findByID(1L)).thenReturn(session);

        mockMvc.perform(get("/private/application/{appID}/session/{sessionID}","1","1").contentType(mediaType).principal(testingAuthenticationToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testDeleteSession() throws Exception {
        JpaUserDetails jpaUserDetails=new JpaUserDetails();
        TestingAuthenticationToken testingAuthenticationToken=new TestingAuthenticationToken(jpaUserDetails,null);
        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);

        Mockito.when(sessionService.findByID(1L)).thenReturn(session);

        mockMvc.perform(delete("/private/application/{appID}/session/{sessionID}","1","1").contentType(mediaType).principal(testingAuthenticationToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
