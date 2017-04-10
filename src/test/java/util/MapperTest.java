package util;

import com.backend.config.MappingsConfig;
import com.backend.domain.application.Application;
import com.backend.domain.application.ApplicationDTO;
import com.backend.domain.application.ApplicationImpl;
import com.backend.domain.application.roles.Role;
import com.backend.domain.application.roles.UserRole;
import com.backend.domain.application.roles.UserRoleDTO;
import com.backend.domain.event.Event;
import com.backend.domain.event.EventDTO;
import com.backend.domain.event.data.Data;
import com.backend.domain.event.data.DataDTO;
import com.backend.service.app.IAppService;
import com.backend.service.event.IEventService;
import com.backend.service.session.ISessionService;
import com.backend.domain.session.AppSession;
import com.backend.domain.session.Session;
import com.backend.domain.session.SessionDTO;
import com.backend.service.user.IUserService;
import com.backend.domain.authenticationUser.User;
import com.backend.domain.authenticationUser.UserDTO;
import com.backend.service.userProfile.IUserProfileService;
import com.backend.domain.authenticationUser.profile.UserProfile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.Date;

/**
 * Created by MooT on 01.10.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MappingsConfig.class,UtilTestConfig.class})
public class MapperTest {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IUserService userService;
    @Autowired
    private IUserProfileService profileService;
    @Autowired
    private IAppService appService;
    @Autowired
    private IEventService eventService;
    @Autowired
    private ISessionService sessionService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    UserProfile userProfile =Mockito.mock(UserProfile.class);
    User user=Mockito.mock(User.class);
    Application app=new ApplicationImpl();
    ApplicationDTO appDTO=Mockito.mock(ApplicationDTO.class);
    Event event=Mockito.mock(Event.class);
    Session session=Mockito.mock(AppSession.class);
    @Before
    public void init()
    {
        Mockito.when(appDTO.getId()).thenReturn("1a");

        Mockito.when(userProfile.getId()).thenReturn(1L);

        Mockito.when(user.getId()).thenReturn("1");
        Mockito.when(user.getPassword()).thenReturn("asd");
        Mockito.when(user.getUsername()).thenReturn("userProfile");
        Mockito.when(user.getRole()).thenReturn("ROLE_USER");

        //Mockito.when(userProfile.getApps()).thenReturn(Arrays.asList(application));
        app.setId("1a");
        app.setName("first");
        app.setDateAdded(new Date());
        app.setPrivateKey("asd");
        app=Mockito.spy(app);

        Mockito.when(event.getId()).thenReturn(1L);
        Mockito.when(session.getId()).thenReturn(1L);
        Mockito.when(session.getApp()).thenReturn(app);
        Mockito.when(profileService.getProfileByID(userProfile.getId())).thenReturn(userProfile);
        Mockito.when(userService.findByID("1")).thenReturn(user);
        Mockito.when(appService.findByID(Mockito.anyString())).thenReturn(app);
        Mockito.when(eventService.findByID(Mockito.anyLong())).thenReturn(event);
        Mockito.when(sessionService.findByID(Mockito.anyLong())).thenReturn(session);
    }

    @Test
    public void testAppFromDTO()
    {
        ApplicationDTO appDTO=Mockito.mock(ApplicationDTO.class);
        Mockito.when(appDTO.getId()).thenReturn("1a");
        Mockito.when(appDTO.getName()).thenReturn("application");
        Mockito.when(appDTO.getOwnerID()).thenReturn(1L);

        Application app=modelMapper.map(appDTO,Application.class);

        Assert.assertNotNull(app);
        Assert.assertNotNull(app.getOwner());

        Assert.assertEquals(appDTO.getId(),app.getId());
        Assert.assertEquals(appDTO.getName(),app.getName());
    }
    @Test
    public void testAppToDTO()
    {
        Application app=Mockito.mock(Application.class);
        Mockito.when(app.getId()).thenReturn("1a");
        Mockito.when(app.getName()).thenReturn("FirstApp");
        ApplicationDTO appDTO=modelMapper.map(app,ApplicationDTO.class);

        Assert.assertNotNull(appDTO);

        Assert.assertEquals(app.getId(),appDTO.getId());
        Assert.assertEquals(app.getName(),appDTO.getName());
    }

    @Test
    public void testSessionToDTO()
    {
        SessionDTO sessionDTO=modelMapper.map(session,SessionDTO.class);

        Assert.assertNotNull(sessionDTO);
        Assert.assertEquals(session.getId(),sessionDTO.getId());
        Assert.assertEquals(session.getApp().getId(),sessionDTO.getAppID());
    }
    @Test
    public void testSessionFromDTO()
    {
        SessionDTO sessionDTO=Mockito.mock(SessionDTO.class);
        Mockito.when(sessionDTO.getId()).thenReturn(123L);
        Mockito.when(sessionDTO.getAppID()).thenReturn("1a");

        Session session=modelMapper.map(sessionDTO,Session.class);

        Assert.assertNotNull(session);
        Assert.assertNotNull(session.getApp());

        Assert.assertEquals(sessionDTO.getId(),session.getId());
        Assert.assertEquals(session.getApp().getId(),sessionDTO.getAppID());
    }

    @Test
    public void testEventFromDTO()
    {

        EventDTO eventDTO=Mockito.mock(EventDTO.class);
        Mockito.when(eventDTO.getId()).thenReturn(1L);
        Mockito.when(eventDTO.getSessionID()).thenReturn(1L);
        Mockito.when(eventDTO.getAppID()).thenReturn("1a");
        Mockito.when(eventDTO.getName()).thenReturn("eventName");
        Mockito.when(eventDTO.getDataList()).thenReturn(Collections.singletonList(Mockito.mock(DataDTO.class)));

        Event event=modelMapper.map(eventDTO,Event.class);

        Assert.assertNotNull(event);
        Assert.assertNotNull(event.getApp());
        Assert.assertNotNull(event.getSession());
        Assert.assertNotNull(event.getData());

        Assert.assertEquals(event.getId(),eventDTO.getId());
        Assert.assertEquals(event.getApp().getId(),eventDTO.getAppID());
        Assert.assertEquals(event.getSession().getId(),eventDTO.getSessionID());
        Assert.assertEquals(event.getData().size(),1);
    }

    @Test
    public void testEventToDTO()
    {
        Data data=Mockito.mock(Data.class);
        Mockito.when(data.getName()).thenReturn("data");
        Mockito.when(data.getValue()).thenReturn("value");

        Mockito.when(event.getApp()).thenReturn(app);
        Mockito.when(event.getSession()).thenReturn(session);
        Mockito.when(event.getData()).thenReturn(Collections.singletonList(data));
        EventDTO eventDTO=modelMapper.map(event,EventDTO.class);

        Assert.assertNotNull(eventDTO);
        Assert.assertNotNull(eventDTO.getDataList());

        Assert.assertEquals(event.getId(),eventDTO.getId());
        Assert.assertEquals(event.getApp().getId(),eventDTO.getAppID());
        Assert.assertEquals(event.getName(),eventDTO.getName());
        Assert.assertEquals(event.getData().size(),eventDTO.getDataList().size());

    }

    @Test
    public void testDataFromDTO()
    {
        DataDTO dataDTO=Mockito.mock(DataDTO.class);
        Mockito.when(dataDTO.getName()).thenReturn("data");
        Mockito.when(dataDTO.getValue()).thenReturn("value");

        Data data=modelMapper.map(dataDTO,Data.class);

        Assert.assertNotNull(data);
        Assert.assertEquals(data.getName(),dataDTO.getName());
        Assert.assertEquals(data.getValue(),dataDTO.getValue());
    }
    @Test
    public void testDataToDTO()
    {
        Data data=Mockito.mock(Data.class);
        Mockito.when(data.getName()).thenReturn("data");
        Mockito.when(data.getValue()).thenReturn("value");

        DataDTO dataDTO=modelMapper.map(data,DataDTO.class);

        Assert.assertNotNull(dataDTO);
        Assert.assertEquals(data.getValue(),dataDTO.getValue());
        Assert.assertEquals(data.getName(),dataDTO.getName());

    }

    @Test
    public void testUserFromDTO()
    {
        UserDTO userDTO=new UserDTO();
        userDTO.setId("1");
        userDTO.setPassword("asd");
        userDTO.setUsername("userProfile");
        userDTO.setRole("ROLE_USER");
        userDTO=Mockito.spy(userDTO);

        String password=userDTO.getPassword();
        Mockito.when(passwordEncoder.encode(Mockito.eq(password))).thenReturn(password);
        User user=modelMapper.map(userDTO,User.class);

        Assert.assertNotNull(user);

        Assert.assertEquals(userDTO.getId(),user.getId());
        Assert.assertEquals(userDTO.getPassword(),user.getPassword());
        Assert.assertEquals(userDTO.getUsername(),user.getUsername());
    }
    @Test
    public void testUserAppsNul()
    {
        User user=Mockito.mock(User.class);

        Mockito.when(user.getId()).thenReturn("1");

        UserDTO userDTO=Mockito.mock(UserDTO.class);
        Mockito.when(userDTO.getId()).thenReturn("1");
        Mockito.when(userDTO.getPassword()).thenReturn("asd");
        Mockito.when(userDTO.getUsername()).thenReturn("userProfile");
        Mockito.when(userDTO.getRole()).thenReturn("ROLE_USER");
        Mockito.when(userService.findByID(user.getId())).thenReturn(user);
        User userResult=modelMapper.map(userDTO,User.class);

        Assert.assertNotNull(userResult);
    }
    @Test
    public void testUserAppsIDNul()
    {
        User user=Mockito.mock(User.class);

        Mockito.when(user.getId()).thenReturn("1");

        UserDTO userDTO=Mockito.mock(UserDTO.class);
        Mockito.when(userDTO.getId()).thenReturn(null);
        Mockito.when(userDTO.getPassword()).thenReturn("asd");
        Mockito.when(userDTO.getUsername()).thenReturn("userProfile");
        Mockito.when(userDTO.getRole()).thenReturn("ROLE_USER");
        Mockito.when(userService.findByID(user.getId())).thenReturn(user);
        User userResult=modelMapper.map(userDTO,User.class);

        Assert.assertNotNull(userResult);
    }

    @Test
    public void testUserRoleToDTO()
    {
        UserRole userRole=new UserRole();
        userRole.setProfile(userProfile);
        userRole.setApp(app);
        userRole.setId(1l);
        userRole.setRole(Role.OWNER);

        UserRoleDTO dto=modelMapper.map(userRole,UserRoleDTO.class);
        Logger logger= LoggerFactory.getLogger(this.getClass());
        logger.info("-------------------------ROLE "+dto.getRole());
        Assert.assertNotNull(dto);
        Assert.assertNotNull(dto.getProfile());
        Assert.assertNotNull(dto.getApp());
        Assert.assertNotNull(dto.getRole());
        Assert.assertEquals(userRole.getProfile().getPrincipal(),dto.getProfile().getName());
    }
}
