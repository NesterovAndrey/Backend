package service;

import com.backend.domain.application.Application;
import com.backend.domain.application.ApplicationImpl;
import com.backend.service.user.IUserService;
import com.backend.service.userProfile.IUserProfileService;
import com.backend.domain.authenticationUser.profile.UserProfile;
import com.backend.domain.application.ApplicationDTO;
import com.backend.service.app.IAppService;
import com.backend.repository.AppRepository;

import utils.precondition.IValidator;
import config.MapperTestConfig;
import config.RepositoryTestConfig;
import config.ServiceTestConfig;
import config.ValidatorTestConfig;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes ={ValidatorTestConfig.class, MapperTestConfig.class,ServiceTestConfig.class,ServiceBeanConfig.class, RepositoryTestConfig.class})
public class ApplicationServiceTest {

    @Autowired
    private AppRepository appRepository;
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserProfileService profileService;
    @Autowired
    private IValidator validator;
    @Autowired
    @Qualifier("appServiceTestTarget")
    private IAppService appService;

    private ApplicationDTO testAppDTO =null;
    private Application testAPP;
    private UserProfile user;
    @Before
    public void setup()
    {
        user=new UserProfile();
        //authenticationUser.setRole("USER");
        //authenticationUser.setUsername("foo");
        //authenticationUser.setPassword("bar");
        user.setId(1L);

        testAppDTO =new ApplicationDTO();
        testAppDTO.setOwnerID(user.getId());
        testAppDTO.setName("test");

        testAPP=new ApplicationImpl();
        testAPP.setName(testAppDTO.getName());
        testAPP.setPrivateKey("2acf8190-8e32-11e6-bdf4-0800200c9a66");
        testAPP.setId("123");
        testAPP.setDateAdded(new Date());
        testAPP.setOwner(user);

        //authenticationUser.seApps(Arrays.asList(testAPP));

        Mockito.when(profileService.getProfileByID(testAppDTO.getOwnerID())).thenReturn(user);
        Mockito.when(appRepository.save(Mockito.any(Application.class))).thenReturn(testAPP);
        Mockito.when(appRepository.findOne(testAPP.getId())).thenReturn(testAPP);
        Mockito.when(appRepository.findAllByOwner(Mockito.any(UserProfile.class))).thenReturn(Collections.singletonList(testAPP));
        Mockito.when(appRepository.findAll()).thenReturn(Collections.singletonList(testAPP));
        Mockito.doThrow(new IllegalArgumentException()).when(validator).isTrue(Mockito.eq(false),Mockito.anyString());
        Mockito.doThrow(new NullPointerException()).when(validator).notNull(Mockito.isNull(),Mockito.anyString());
    }
    @Test
    public void testAppCreation()
    {
        Application app=appService.createApp(testAPP);
        Assert.assertTrue(app!=null);
        Assert.assertNotNull(app.getId());
        Assert.assertEquals(app.getOwner().getId(), testAPP.getOwner().getId());
        Assert.assertEquals(app.getName(),testAPP.getName());
        Assert.assertNotNull(app.getPrivateKey());
    }

    @Test(expected = NullPointerException.class)
    public void testNullAppCreation()
    {
       appService.createApp(null);
    }

    @Test
    public void testGetApp()
    {
        Application app=appService.findByID(testAPP.getId());
        Assert.assertNotNull(app);
        Assert.assertEquals(testAPP.getId(),app.getId());
        Assert.assertEquals(testAPP.getName(),app.getName());
        Assert.assertEquals(testAPP.getPrivateKey(),app.getPrivateKey());
        Assert.assertEquals(testAPP.getDateAdded(),app.getDateAdded());
        Assert.assertEquals(testAPP.getOwner().getId(),app.getOwner().getId());
    }
    @Test(expected = NullPointerException.class)
    public void testGetAppNull()
    {
        appService.findByID(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testGetAppEmpty()
    {
        appService.findByID("");
    }
    @Test
    public void testDeleteApp()
    {
        Application app=appService.deleteApp(testAPP.getId());
        Assert.assertNotNull(app);
        Assert.assertEquals(testAPP.getId(),app.getId());
        Assert.assertEquals(testAPP.getName(),app.getName());
        Assert.assertEquals(testAPP.getPrivateKey(),app.getPrivateKey());
        Assert.assertEquals(testAPP.getDateAdded(),app.getDateAdded());
        Assert.assertEquals(testAPP.getOwner().getId(),app.getOwner().getId());
    }
    @Test(expected = NullPointerException.class)
    public void testDeleteAppNull()
    {
        appService.deleteApp(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testDeleteAppEmpty()
    {
        appService.deleteApp("");
    }
    @Test
    public void testGetAll()
    {
        Collection<Application> apps=appService.getAll();
        Assert.assertNotNull(apps);
        Assert.assertTrue(apps.size()!=0);

    }
    @Test
    public void testDelete()
    {
        Application app=appService.findByID("123");
        appService.deleteApp(app.getId());
        Mockito.verify(appRepository,Mockito.times(1)).delete(app);
    }
    @Test
    public void testGetAppByOwner()
    {
        Collection<Application> apps=appService.findAllByUser(user);
        Assert.assertNotNull(apps);
        Assert.assertTrue(apps.size()!=0);
    }
    @Test(expected = NullPointerException.class)
    public void testGetAppByOwnerIsNull()
    {
        appService.findAllByUser(null);
    }
}
