package controller.session;

import com.backend.domain.session.Session;
import com.backend.domain.session.SessionDTO;
import com.backend.web.rest.controller.session.SessionPublicController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.HandlerExceptionResolver;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
@RunWith(SpringJUnit4ClassRunner.class)
public class SessionPublicControllerTest extends BaseSessionTest {

    @Autowired
    private SessionPublicController sessionPublicController;
    @Autowired
    private HandlerExceptionResolver restExceptionHandler;
    @Before
    public void init()
    {

        Mockito.when(sessionService.createSession(Mockito.any(Session.class))).thenReturn(session);
        Mockito.when(mapperUtil.map(Mockito.any(SessionDTO.class),Mockito.eq(Session.class))).thenReturn(session);
        Mockito.when(mapperUtil.map(Mockito.any(Session.class),Mockito.eq(SessionDTO.class))).thenReturn(sessionDTO);

        mockMvc= MockMvcBuilders.standaloneSetup(sessionPublicController).setHandlerExceptionResolvers(restExceptionHandler).build();
    }
    @Test
    public void testCreateSession() throws Exception {
        MvcResult result = mockMvc.perform(post("/public/application/{appID}/install/{installID}/session", "1","1").content(toJSon(sessionDTO)).contentType(mediaType))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("id", is(1))).andReturn();

    }
    @Test
    public void testCreateSessionEmptyBody() throws Exception {
        MvcResult result = mockMvc.perform(post("/public/application/{appID}/install/{installID}/session", "1","1").content("").contentType(mediaType))
                .andExpect(MockMvcResultMatchers.status().is(400)).andReturn();

    }

}
