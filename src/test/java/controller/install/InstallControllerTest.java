package controller.install;

import com.backend.service.app.IAppService;
import com.backend.domain.application.applicationInstall.*;
import utils.mapping.IMapperUtil;
import com.backend.web.rest.controller.install.InstallPublicController;
import com.backend.service.install.IInstallService;
import config.MapperTestConfig;
import config.ServiceTestConfig;
import controller.ControllerTestConfig;
import config.ValidatorTestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes ={ControllerTestConfig.class, ValidatorTestConfig.class, MapperTestConfig.class, ServiceTestConfig.class})
@WebAppConfiguration
public class InstallControllerTest {
    private final HttpMessageConverter mappingJackson2HttpMessageConverter=new MappingJackson2HttpMessageConverter();

    @Autowired
    private IInstallService installService;
    @Autowired
    private IAppService appService;
    @Autowired
    private IMapperUtil mapperUtil;
    @Autowired
    private InstallPublicController installController;
    @Autowired
    private HandlerExceptionResolver restExceptionHandler;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    protected MockMvc mockMvc;
    protected MediaType mediaType=new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Before
    public void setUp()
    {
        mockMvc= MockMvcBuilders.standaloneSetup(installController).setHandlerExceptionResolvers(restExceptionHandler).build();
    }

    @Test
    public void testCreateInstall() throws Exception
    {
        ApplicationInstallData installData=new ApplicationInstallData();
        installData.setClientLocale("en_US");
        installData.setClientOS("Android");
        installData.setClientOSVersion("5.0");
       mockMvc.perform(post("/public/application/{appID}/install","1").content(toJSon(installData)).contentType(mediaType))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
    @Test
    public void testCreateInstallEmptyBody() throws Exception
    {
        ApplicationInstallData installData=new ApplicationInstallData();
        installData.setClientLocale("en_US");
        installData.setClientOS("Android");
        installData.setClientOSVersion("5.0");
        mockMvc.perform(post("/public/application/{appID}/install","1").contentType(mediaType))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }
    protected String toJSon(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
