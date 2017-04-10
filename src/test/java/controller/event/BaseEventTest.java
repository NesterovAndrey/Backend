package controller.event;

import com.backend.service.event.IEventService;
import utils.mapping.IMapperUtil;
import config.MapperTestConfig;
import config.ServiceTestConfig;
import config.ValidatorTestConfig;
import controller.ControllerTestConfig;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;

@ContextConfiguration(classes ={ControllerTestConfig.class, ValidatorTestConfig.class, MapperTestConfig.class, ServiceTestConfig.class})
@WebAppConfiguration
public class BaseEventTest {
    private HttpMessageConverter mappingJackson2HttpMessageConverter=new MappingJackson2HttpMessageConverter();
    @Autowired
    protected WebApplicationContext webApplicationContext;
    @Autowired
    protected IEventService eventService;
    @Autowired
    protected IMapperUtil mapperUtil;
    protected MockMvc mockMvc;

    protected MediaType mediaType=new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Before
    public void setup()
    {
        mockMvc= MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    protected String toJSon(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
