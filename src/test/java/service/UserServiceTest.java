package service;

import com.backend.service.user.IUserService;
import com.backend.domain.authenticationUser.User;
import com.backend.domain.authenticationUser.UserDTO;
import com.backend.repository.UserRepository;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes ={ValidatorTestConfig.class, MapperTestConfig.class,ServiceTestConfig.class,ServiceBeanConfig.class, RepositoryTestConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserServiceTest {

    @Autowired
    @Qualifier("userServiceTestTarget")
    private IUserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IValidator validator;

    private UserDTO testUserDTO;
    private User testUser;
    @Before
    public void setup()
    {
        testUserDTO=new UserDTO();
        testUserDTO.setUsername("foo");
        testUserDTO.setPassword("bar");
        testUserDTO.setId("1");
        testUserDTO.setRole("ROLE_USER");

        testUser=new User();
        testUser.setId(testUserDTO.getId());
        testUser.setUsername(testUserDTO.getUsername());
        testUser.setPassword(testUserDTO.getPassword());
        testUser.setRole(testUserDTO.getRole());

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(testUser);
        Mockito.when(userRepository.findOne("1")).thenReturn(testUser);
        Mockito.when(userRepository.findUserByUsername("foo")).thenReturn(testUser);
        Mockito.doThrow(new IllegalArgumentException()).when(validator).isTrue(Mockito.eq(false),Mockito.anyString());
    }
    @Test
    public void testCreateUser()
    {

        User user=userService.register(testUser);
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getUsername(),testUser.getUsername());
        Assert.assertEquals(user.getPassword(),testUser.getPassword());
        Assert.assertEquals(user.getRole(),testUser.getRole());
    }
    @Test(expected = NullPointerException.class)
    public void testCreateNullUser()
    {
        Mockito.doThrow(new NullPointerException()).when(validator).notNull(Mockito.isNull(User.class),Mockito.anyString());
        userService.register(null);
    }
    @Test
    public void testGetUserByID()
    {
        User user=userService.findByID("1");
        Assert.assertNotNull(user);
    }
    @Test(expected = NullPointerException.class)
    public void testGetUserWithNullID()
    {
        Mockito.doThrow(new NullPointerException()).when(validator).notNull(Mockito.isNull(String.class),Mockito.anyString());
        userService.findByID(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testGetUserWithEmptyID()
    {
        userService.findByID("");
    }
    @Test
    public void testDeleteUser()
    {
        User user=userService.findByID("1");
        userService.deleteUser(user.getId());

        Mockito.verify(userRepository,Mockito.times(1)).delete(user);
    }
    @Test(expected = NullPointerException.class)
    public void testDeleteNullUser()
    {
        Mockito.doThrow(new NullPointerException()).when(validator).notNull(Mockito.isNull(User.class),Mockito.anyString());
        userService.deleteUser(null);
    }
    public void testFindByUserName()
    {
        User user1=userService.getUserByName("foo");
        User user2=userService.getUserByName("bar");
        Assert.assertNotNull(user1);
        Assert.assertNull(user2);
        Assert.assertEquals(user1.getUsername(),"foo");
    }
    @Test(expected = NullPointerException.class)
    public void testFindByUserNameIsNull()
    {
        Mockito.doThrow(new NullPointerException()).when(validator).notNull(Mockito.isNull(String.class),Mockito.anyString());
        userService.getUserByName(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testFindByUserNameIsEmpty()
    {
        userService.getUserByName("");
    }
    @Test
    public void testIsExists()
    {
        Assert.assertTrue(userService.isExists("foo"));
        Assert.assertFalse(userService.isExists("bar"));
    }
    @Test(expected = NullPointerException.class)
    public void testIsExistsNullID()
    {
        Mockito.doThrow(new NullPointerException()).when(validator).notNull(Mockito.isNull(String.class),Mockito.anyString());
        userService.isExists(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testIsExistsEmpty()
    {

       userService.isExists("");
    }
}
