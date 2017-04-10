package service;

import com.backend.domain.authenticationUser.profile.UserProfile;
import com.backend.service.userProfile.UserProfileService;
import com.backend.repository.ProfileRepository;
import utils.precondition.IValidator;
import config.MapperTestConfig;
import config.RepositoryTestConfig;
import config.ServiceTestConfig;
import config.ValidatorTestConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;
import java.util.Collections;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes ={ValidatorTestConfig.class, MapperTestConfig.class, ServiceTestConfig.class,ServiceBeanConfig.class, RepositoryTestConfig.class})
public class UserProfileServiceTest {

    @Autowired
    private IValidator validator;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    @Qualifier("profileServiceTestTarget")
    private UserProfileService userProfileService;

    UserProfile userProfile;

    @Before
    public void setup()
    {

        userProfile=new UserProfile();
        userProfile.setUsername("username");
        userProfile.setPrincipal("principal");
        userProfile.setId(1L);
        userProfile=Mockito.spy(userProfile);

        Mockito.when(profileRepository.findOne(Mockito.eq(userProfile.getId()))).thenReturn(userProfile);
        Mockito.when(profileRepository.findByPrincipal(Mockito.eq(userProfile.getPrincipal()))).thenReturn(userProfile);
        Mockito.when(profileRepository.save(Mockito.any(UserProfile.class))).thenReturn(userProfile);
        Mockito.when(profileRepository.findAll()).thenReturn(Collections.singletonList(userProfile));
        Mockito.doThrow(new IllegalArgumentException()).when(validator).isTrue(Mockito.eq(false),Mockito.anyString());
        Mockito.doThrow(new NullPointerException()).when(validator).notNull(Mockito.isNull(),Mockito.anyString());
    }
    @Test
    public void testGetProfileByID()
    {
        UserProfile userProfile=userProfileService.getProfileByID(1l);
        Assert.assertNotNull(userProfile);
        Assert.assertEquals(userProfile.getId(),this.userProfile.getId());
    }
    @Test(expected = NullPointerException.class)
    public void testGetProfileByIDNull()
    {
        userProfileService.getProfileByID(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testGetProfileByIDEmpty()
    {
        userProfileService.getProfileByID(-1L);
    }
    @Test
    public void testGetProfileByPrincipal()
    {
        UserProfile userProfile=userProfileService.getProfileByPrincipal("principal");
        Assert.assertNotNull(userProfile);
        Assert.assertEquals(userProfile.getId(),this.userProfile.getId());
        Assert.assertEquals(userProfile.getPrincipal(),this.userProfile.getPrincipal());
    }
    @Test(expected = NullPointerException.class)
    public void testGetProfileByPrincipalNull()
    {
        userProfileService.getProfileByPrincipal(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testGetProfileByPrincipalEmpty()
    {
        userProfileService.getProfileByPrincipal("");
    }
    @Test
    public void testSave()
    {
        UserProfile userProfile=userProfileService.save(this.userProfile);
        Assert.assertNotNull(userProfile);
        Assert.assertEquals(userProfile.getId(),this.userProfile.getId());
    }
    @Test(expected = NullPointerException.class)
    public void testSaveNull()
    {
        userProfileService.save(null);
    }

    @Test
    public void testGetAll()
    {
        Collection<UserProfile> profiles=userProfileService.getAll();
        Assert.assertNotNull(profiles);
        Assert.assertTrue(profiles.size()>0);
    }
}
