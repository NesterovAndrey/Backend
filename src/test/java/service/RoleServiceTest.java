package service;

import com.backend.domain.application.Application;
import com.backend.domain.application.ApplicationImpl;
import com.backend.service.role.IRoleService;
import com.backend.domain.application.roles.Role;
import com.backend.domain.application.roles.UserRole;
import com.backend.domain.authenticationUser.profile.UserProfile;
import com.backend.repository.RoleRepository;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes ={ValidatorTestConfig.class, MapperTestConfig.class, ServiceTestConfig.class,ServiceBeanConfig.class, RepositoryTestConfig.class})
public class RoleServiceTest {

    @Autowired
    private IValidator validator;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    @Qualifier("roleServiceTestTarget")
    private IRoleService roleService;


    private UserRole userRole;
    private UserProfile userProfile;
    private Application app;
    @Before
    public void setUp()
    {
        userRole=new UserRole();
        userRole.setApp(app);
        userRole.setId(1L);
        userRole.setProfile(userProfile);
        userRole.setRole(Role.ADMIN);
        userRole=Mockito.spy(userRole);

        userProfile=new UserProfile();
        userProfile.setId(1L);
        userProfile.setPrincipal("principal");
        userProfile.setUsername("username");
        userProfile=Mockito.spy(userProfile);

        app=new ApplicationImpl();
        app.setId("app_id");
        app.setOwner(userProfile);
        app.setDateAdded(new Date());
        app.setName("username");
        app.setPrivateKey("private_key");
        app=Mockito.spy(app);


        Mockito.when(roleRepository.findByProfileAndApp(userProfile,app)).thenReturn(userRole);
        Mockito.when(roleRepository.findAll()).thenReturn(Collections.singletonList(userRole));
        Mockito.when(roleRepository.save(Mockito.any(UserRole.class))).thenReturn(userRole);
        Mockito.doThrow(new IllegalArgumentException()).when(validator).isTrue(Mockito.eq(false),Mockito.anyString());
        Mockito.doThrow(new NullPointerException()).when(validator).notNull(Mockito.isNull(),Mockito.anyString());
    }


    @Test
    public void testFindByProfileAndApp()
    {
        UserRole role=roleService.findByProfileAndApp(this.userProfile,this.app);
        Assert.assertNotNull(role);
        Assert.assertEquals(role,this.userRole);
    }
    @Test(expected = NullPointerException.class)
    public void testFindByProfileAnddAppNullParam()
    {
        roleService.findByProfileAndApp(null,this.app);
        roleService.findByProfileAndApp(this.userProfile,null);
    }
    @Test
    public void testCreateRole()
    {
        UserRole role=roleService.createRole(userProfile,app,Role.ADMIN);
        Logger logger= LoggerFactory.getLogger(this.getClass());
        Assert.assertNotNull(role);
        Assert.assertEquals(role.getId(),this.userRole.getId());
    }
    @Test(expected = NullPointerException.class)
    public void testCreateWithNullParam()
    {
        roleService.createRole(null,app,Role.ADMIN);
        roleService.createRole(userProfile,null,Role.ADMIN);
        roleService.createRole(userProfile,app,null);
    }
}
