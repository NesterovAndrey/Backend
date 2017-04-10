package controller;
import static org.hamcrest.Matchers.*;

import com.backend.config.ExceptionResolverConfig;
import com.backend.service.user.IUserService;
import com.backend.domain.authenticationUser.User;
import com.backend.domain.authenticationUser.UserDTO;
import com.backend.service.userProfile.IUserProfileService;
import com.backend.domain.authenticationUser.profile.UserProfile;
import com.backend.domain.authenticationUser.profile.UserProfileDTO;
import utils.mapping.IMapperUtil;
import com.backend.web.validation.IResourceValidator;
import com.backend.web.rest.controller.accounts.AccountsController;
import com.backend.web.rest.exception.NotFoundException;
import utils.message.MessageItemImpl;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Vector;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes ={ControllerTestConfig.class, ValidatorTestConfig.class, MapperTestConfig.class, ServiceTestConfig.class, ExceptionResolverConfig.class})
@WebAppConfiguration
public class UserControllerTest {

    private HttpMessageConverter mappingJackson2HttpMessageConverter=new MappingJackson2HttpMessageConverter();

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private AccountsController accountsController;
    @Autowired
    private HandlerExceptionResolver restExceptionHandler;
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserProfileService profileService;
    @Autowired
    private IMapperUtil mapperUtil;
    @Autowired
    private IResourceValidator<User,String> userValidator;

    UserProfile userProfile =new UserProfile("none","one", new Vector<>());
    UserProfileDTO profileDTO =new UserProfileDTO();

    private MockMvc mockMvc;

    private MediaType mediaType=new MediaType(MediaType.APPLICATION_JSON.getType(),
    MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));



    @Before
    public void setup()
    {
        profileDTO.setName("foo");

        Mockito.when(mapperUtil.map(Mockito.any(UserProfileDTO.class),Mockito.eq(UserProfile.class))).thenReturn(userProfile);
        Mockito.when(mapperUtil.map(Mockito.any(UserProfile.class),Mockito.eq(UserProfileDTO.class))).thenReturn(profileDTO);
        Mockito.when(userService.isExists("foo")).thenReturn(false);
         mockMvc=MockMvcBuilders.standaloneSetup(accountsController).setMessageConverters(new MappingJackson2HttpMessageConverter()).setHandlerExceptionResolvers(restExceptionHandler).build();

        mediaType=MediaType.APPLICATION_JSON;
    }

    @Test
    public void testGetAllUsers() throws Exception {

        UserProfileDTO userTwoDTO=new UserProfileDTO();
        userTwoDTO.setName("bar");

        UserProfile userTwo=new UserProfile("two","two", new Vector<>());

        Mockito.when(mapperUtil.map(userTwo,UserProfileDTO.class)).thenReturn(userTwoDTO);
        Mockito.when(mapperUtil.map(userTwoDTO,UserProfile.class)).thenReturn(userTwo);

        Mockito.when(mapperUtil.map(Arrays.asList(userProfile,userTwo),UserProfileDTO.class)).thenReturn(Arrays.asList(profileDTO,userTwoDTO));
        Mockito.when(mapperUtil.map(Arrays.asList(profileDTO,userTwoDTO),UserProfile.class)).thenReturn(Arrays.asList(userProfile,userTwo));

        Mockito.when(profileService.getAll()).thenReturn(Arrays.asList(userProfile,userTwo));

        MvcResult result=mockMvc.perform(get("/accounts").contentType(mediaType))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name",is("foo")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name",is("bar"))).andDo(print()).andReturn();

        Mockito.verify(profileService,Mockito.times(1)).getAll();
        Mockito.verifyNoMoreInteractions(profileService);

    }
    @Test
    public void testCreateUser() throws Exception {
        UserDTO userDTO=new UserDTO();
        userDTO.setId("123");
        userDTO.setUsername("username");
        userDTO.setPassword("username");
        userDTO.setRole("ROLE_USER");

        User user=new User();
        user.setUsername("UserOne");
        user.setId("123");
        user=Mockito.spy(user);

        Mockito.when(mapperUtil.map(Mockito.any(UserDTO.class),Mockito.eq(User.class))).thenReturn(user);
        Mockito.when(mapperUtil.map(Mockito.any(User.class),Mockito.eq(UserDTO.class))).thenReturn(userDTO);
        Mockito.when(mapperUtil.map(Mockito.any(UserProfileDTO.class),Mockito.eq(UserProfile.class))).thenReturn(userProfile);
        Mockito.when(mapperUtil.map(Mockito.any(UserProfile.class),Mockito.eq(UserProfileDTO.class))).thenReturn(profileDTO);
        Mockito.when(userService.register(user)).thenReturn(user);
        Mockito.when(profileService.save(Mockito.any(UserProfile.class))).thenReturn(userProfile);


        UserProfile usr= mapperUtil.map(profileDTO,UserProfile.class);

       mockMvc.perform(post("/accounts").contentType(mediaType).content(toJSon(userDTO))).andExpect(MockMvcResultMatchers.status().isCreated());

    }
    @Test
    public void testCreateUserEmptyBody() throws Exception {
        UserDTO userDTO=new UserDTO();
        userDTO.setId("123");
        userDTO.setUsername("username");
        userDTO.setPassword("username");
        userDTO.setRole("ROLE_USER");

        User user=new User();
        user.setUsername("UserOne");
        user.setId("123");
        user=Mockito.spy(user);

        Mockito.when(mapperUtil.map(Mockito.any(UserDTO.class),Mockito.eq(User.class))).thenReturn(user);
        MvcResult result=mockMvc.perform(post("/accounts").contentType(mediaType).content(toJSon(""))).andExpect(MockMvcResultMatchers.status().is(400)).andDo(print()).andReturn();
       //.andExpect(MockMvcResultMatchers.jsonPath("code", is(40004)));
    }
    @Test
    public void testCreateUserJsonEmpty() throws Exception {
        UserDTO userDTO=new UserDTO();
        userDTO.setId("123");
        userDTO.setUsername("username");
        userDTO.setPassword("username");
        userDTO.setRole("ROLE_USER");

        User user=new User();
        user.setUsername("UserOne");
        user.setId("123");
        user=Mockito.spy(user);

        Mockito.when(mapperUtil.map(Mockito.any(UserDTO.class),Mockito.eq(User.class))).thenReturn(user);
        mockMvc.perform(post("/accounts").contentType(mediaType).content(toJSon("{}"))).andExpect(MockMvcResultMatchers.status().is(400)).andDo(print());
                //.andExpect(MockMvcResultMatchers.jsonPath("code", is(40003)));
    }
    @Test
    public void testDeleteUser() throws Exception {
        User user=Mockito.mock(User.class);
        Mockito.when(userService.deleteUser("1")).thenReturn(user);
        mockMvc.perform(delete("/accounts/{userID}","1").contentType(mediaType))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
    }
    @Test
    public void testDeleteNotExistingUser() throws Exception {

        Mockito.doThrow(new NotFoundException(new MessageItemImpl("NotFound"))).when(userValidator).validate("notExistingResource");
        mockMvc.perform(delete("/accounts/{userID}","notExistingResource").contentType(mediaType))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    protected String toJSon(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
