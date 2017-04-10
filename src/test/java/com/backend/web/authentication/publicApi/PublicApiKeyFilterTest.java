package com.backend.web.authentication.publicApi;

import com.backend.domain.application.Application;
import com.backend.domain.application.ApplicationImpl;
import com.backend.service.app.IAppService;
import com.backend.domain.authenticationUser.profile.UserProfile;
import com.backend.web.rest.exception.NotFoundException;
import com.backend.web.rest.filter.ApiKeyFilter;
import config.MapperTestConfig;
import config.ServiceTestConfig;
import config.ValidatorTestConfig;
import controller.ControllerTestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.nio.charset.Charset;
import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ContextConfiguration(classes ={ControllerTestConfig.class,PublicApiTestConfig.class, ValidatorTestConfig.class, MapperTestConfig.class, ServiceTestConfig.class})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class PublicApiKeyFilterTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private HandlerExceptionResolver restExceptionHandler;
    @Autowired
    private IAppService appService;
    @Autowired
    private ApiKeyFilter apiKeyFilter;

    private Application testApp;
    private UserProfile userProfile;
    private MockMvc mockMvc;
    protected MediaType mediaType=new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
    @Before
    public void setup()
    {
        userProfile= new UserProfile();
        userProfile.setUsername("user");
        userProfile.setPrincipal("ROLE_USER");
        userProfile.setId(1l);
        userProfile=Mockito.spy(userProfile);

        testApp=new ApplicationImpl();
        testApp.setId("1");
        testApp.setPrivateKey("28124a21-bc8c-3cad-9138-313c62118529");
        testApp.setName("application");
        testApp.setDateAdded(new Date());
        testApp.setOwner(userProfile);
        testApp=Mockito.spy(testApp);

        Mockito.when(appService.findByID(Mockito.eq("4305ffce-5a41-3371-95b0-f46be1ee5f3f"))).thenReturn(testApp);
        mockMvc= MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter(apiKeyFilter).build();
    }

    @Test
    public void testPublicAuthentication() throws Exception {
        mockMvc.perform(post("/public/application/{appID}/install","4305ffce-5a41-3371-95b0-f46be1ee5f3f")
                .header("Authorization","4305ffce-5a41-3371-95b0-f46be1ee5f3f:03f0dbf7340013413d2a113d382419b092bef776bbb4f4a40d897ad007d2f4ee")
                .contentType(mediaType).content("{\n" +
                        "\t \"clientOs\":\"Windows\",\n" +
                        "\t \"clientOsVersion\":\"10\",\n" +
                        "\t \"clientLocale\":\"en_US\"\n" +
                        "}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
    @Test
    public void testPublicAuthenticationEmptyBody() throws Exception {
        mockMvc.perform(post("/public/application/{appID}/install","4305ffce-5a41-3371-95b0-f46be1ee5f3f")
                .header("Authorization","4305ffce-5a41-3371-95b0-f46be1ee5f3f:19767e9795bb3aee9b5ca19083c3bca6628057cb1bc6146575adb8cc5378fda0")
                .contentType(mediaType).content(""))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }
    @Test(expected = NotFoundException.class)
    public void testPublicAuthenticationNotFoundID() throws Exception {
        mockMvc.perform(post("/public/application/{appID}/install","11111111")
                .header("Authorization","11111111:19767e9795bb3aee9b5ca19083c3bca6628057cb1bc6146575adb8cc5378fda0")
                .contentType(mediaType).content(""))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }
}
