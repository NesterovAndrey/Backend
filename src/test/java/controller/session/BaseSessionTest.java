package controller.session;

import com.backend.config.ExceptionResolverConfig;
import com.backend.domain.application.Application;
import com.backend.domain.event.Event;
import com.backend.domain.event.data.Data;
import com.backend.domain.session.AppSession;
import com.backend.service.session.ISessionService;
import com.backend.domain.session.Session;
import com.backend.domain.session.SessionDTO;
import utils.mapping.IMapperUtil;
import config.MapperTestConfig;
import config.ServiceTestConfig;
import config.ValidatorTestConfig;
import controller.ControllerTestConfig;
import org.junit.Before;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;

@ContextConfiguration(classes ={ControllerTestConfig.class, ValidatorTestConfig.class, MapperTestConfig.class, ServiceTestConfig.class, ExceptionResolverConfig.class})
@WebAppConfiguration
public class BaseSessionTest {
    private HttpMessageConverter mappingJackson2HttpMessageConverter=new MappingJackson2HttpMessageConverter();
    @Autowired
    protected WebApplicationContext webApplicationContext;
    @Autowired
    protected ISessionService sessionService;
    @Autowired
    protected IMapperUtil mapperUtil;

    protected MockMvc mockMvc;

    protected MediaType mediaType=new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    protected SessionDTO sessionDTO;
    protected Session session;
    protected Event event;

    @Before
    public void setup()
    {
        event=new Event();
        event.setName("event");
        event.setId(1L);
        event.setSession(Mockito.mock(Session.class));
        event.setApp(Mockito.mock(Application.class));
        event.setData(Collections.singletonList(Mockito.mock(Data.class)));

        session= new AppSession();
        session.setId(1L);
        session.setApp(Mockito.mock(Application.class));
        session=Mockito.spy(session);

        sessionDTO=new SessionDTO();
        sessionDTO.setAppID("1");
        sessionDTO.setId(1L);

        Mockito.when(mapperUtil.map(Mockito.any(SessionDTO.class),Mockito.eq(Session.class))).thenReturn(session);
        Mockito.when(mapperUtil.map(Mockito.any(Session.class),Mockito.eq(SessionDTO.class))).thenReturn(sessionDTO);

        //mockMvc= MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    protected String toJSon(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
