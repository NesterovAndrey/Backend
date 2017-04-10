package controller;
import static org.hamcrest.Matchers.*;

import com.backend.domain.application.Application;
import com.backend.domain.application.ApplicationDTO;
import com.backend.domain.application.ApplicationImpl;
import com.backend.service.app.IAppService;
import com.backend.service.role.IRoleService;
import com.backend.service.user.IUserService;
import com.backend.service.userProfile.IUserProfileService;
import com.backend.domain.authenticationUser.profile.UserProfile;
import utils.mapping.IMapperUtil;
import com.backend.web.rest.controller.application.ApplicationController;
import com.backend.web.authentication.JpaUserDetails;
import config.MapperTestConfig;
import config.ServiceTestConfig;
import config.ValidatorTestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes ={ControllerTestConfig.class, ValidatorTestConfig.class, MapperTestConfig.class, ServiceTestConfig.class})
@WebAppConfiguration
public class ApplicationControllerTest {
    private HttpMessageConverter mappingJackson2HttpMessageConverter=new MappingJackson2HttpMessageConverter();

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private IAppService appService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserProfileService profileService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IMapperUtil mapperUtil;
    @Autowired
    private ApplicationController applicationController;
    @Autowired
    private HandlerExceptionResolver restExceptionHandler;
    private MockMvc mockMvc;

    private MediaType mediaType=new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));


    @Before
    public void setup()
    {
        mockMvc=MockMvcBuilders.standaloneSetup(applicationController).setMessageConverters(new MappingJackson2HttpMessageConverter()).setHandlerExceptionResolvers(restExceptionHandler).build();
    }

    @Test
    public void testGetAllApps() throws Exception {
        UserProfile userOne=Mockito.mock(UserProfile.class);
        userOne.setId(1L);

        ApplicationDTO dtoOne=new ApplicationDTO();
        dtoOne.setId("2acf8190-8e32-11e6-bdf4-0800200c9a66");
        dtoOne.setName("appOne");
        dtoOne.setOwnerID(userOne.getId());

        ApplicationDTO dtoTwo=new ApplicationDTO();
        dtoTwo.setId("1acf8190-8e32-11e6-bdf4-0800200c9a66");
        dtoTwo.setName("appTwo");
        dtoTwo.setOwnerID(userOne.getId());

        Application appOne=new ApplicationImpl();
        appOne.setName("appOne");
        appOne.setPrivateKey("2acf8190-8e32-11e6-bdf4-0800200c9a66");
        appOne.setDateAdded(new Date());
        appOne.setOwner(userOne);
        appOne.setId("2acf8190-8e32-11e6-bdf4-0800200c9a66");

        Application appTwo=new ApplicationImpl();
        appTwo.setName("appTwo");
        appTwo.setPrivateKey("1acf8190-8e32-11e6-bdf4-0800200c9a66");
        appTwo.setDateAdded(new Date());
        appTwo.setOwner(userOne);
        appTwo.setId("1acf8190-8e32-11e6-bdf4-0800200c9a66");


        //Mockito.when(userOne.getApps()).thenReturn(Arrays.asList(appOne,appTwo));
        Mockito.when(appService.getAll()).thenReturn(Arrays.asList(appOne,appTwo));
        Mockito.when(profileService.getProfileByID(Mockito.anyLong())).thenReturn(userOne);
        Mockito.when(mapperUtil.map(Mockito.anyList(),Mockito.eq(Application.class))).thenReturn(Arrays.asList(appOne,appTwo));
        Mockito.when(mapperUtil.map(Mockito.anyList(),Mockito.eq(ApplicationDTO.class))).thenReturn(Arrays.asList(dtoOne,dtoTwo));
        JpaUserDetails jpaUserDetails=new JpaUserDetails();
        TestingAuthenticationToken testingAuthenticationToken=new TestingAuthenticationToken(jpaUserDetails,null);
        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);

        mockMvc.perform(get("/private/application").contentType(mediaType).principal(testingAuthenticationToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(2)));

    }
    @Test
     public void testCreateApp() throws Exception {
        UserProfile profile=Mockito.mock(UserProfile.class);
         ApplicationDTO appDTO=new ApplicationDTO();
         appDTO.setName("application");
         appDTO.setOwnerID(1l);
         appDTO.setId("2acf8190-8e32-11e6-bdf4-0800200c9a66");

         Application appOne=new ApplicationImpl();
         appOne.setName("application");
         appOne.setPrivateKey("2acf8190-8e32-11e6-bdf4-0800200c9a66");
         appOne.setDateAdded(new Date());
         appOne.setOwner(profile);
         appOne.getOwner().setId(appDTO.getOwnerID());
         appOne.setId("2acf8190-8e32-11e6-bdf4-0800200c9a66");

        Mockito.when(appService.createApp(Mockito.any(Application.class))).thenReturn(appOne);
        Mockito.when(mapperUtil.map(Mockito.any(ApplicationDTO.class),Mockito.eq(Application.class))).thenReturn(appOne);
        Mockito.when(mapperUtil.map(Mockito.any(Application.class),Mockito.eq(ApplicationDTO.class))).thenReturn(appDTO);

        OAuth2Authentication principal=Mockito.mock(OAuth2Authentication.class);
        Mockito.when(principal.getPrincipal()).thenReturn(profile);
        MvcResult result= mockMvc.perform(post("/private/application").principal(principal).contentType(mediaType).content(toJSon(appDTO)))
                 .andExpect(MockMvcResultMatchers.status().isCreated())
                 .andExpect(MockMvcResultMatchers.jsonPath("id",is(appOne.getId()))).andReturn();

     }
     @Test
     public void testGetApp() throws Exception {
         ApplicationDTO appDTO=new ApplicationDTO();
         appDTO.setName("application");
         appDTO.setOwnerID(1L);
         appDTO.setId("2acf8190-8e32-11e6-bdf4-0800200c9a66");

         Application appOne=new ApplicationImpl();
         appOne.setName("application");
         appOne.setPrivateKey("2acf8190-8e32-11e6-bdf4-0800200c9a66");
         appOne.setDateAdded(new Date());
         appOne.setOwner(Mockito.mock(UserProfile.class));
         appOne.setId("2acf8190-8e32-11e6-bdf4-0800200c9a66");

         Mockito.when(appService.findByID(appOne.getId())).thenReturn(appOne);
         Mockito.when(mapperUtil.map(Mockito.any(ApplicationDTO.class),Mockito.eq(Application.class))).thenReturn(appOne);
         Mockito.when(mapperUtil.map(Mockito.any(Application.class),Mockito.eq(ApplicationDTO.class))).thenReturn(appDTO);
         MvcResult result=mockMvc.perform(get("/private/application/{appID}",appOne.getId()).contentType(mediaType))
                 .andExpect(MockMvcResultMatchers.status().isOk())
                 .andExpect(MockMvcResultMatchers.jsonPath("id",is(appOne.getId()))).andReturn();


     }
     @Test
     public void testDeleteApp() throws Exception {
         mockMvc.perform(delete("/private/application/{appID}","2acf8190-8e32-11e6-bdf4-0800200c9a66").contentType(mediaType))
                 .andExpect(MockMvcResultMatchers.status().isOk());
     }

    protected String toJSon(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
