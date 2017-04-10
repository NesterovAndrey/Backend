package service;

import com.backend.domain.application.ApplicationImpl;
import com.backend.domain.application.Application;
import com.backend.domain.session.*;
import com.backend.web.rest.exception.NotFoundException;
import com.backend.service.app.IAppService;
import com.backend.repository.SessionRepository;
import com.backend.service.session.ISessionService;
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
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes ={ValidatorTestConfig.class, MapperTestConfig.class,ServiceTestConfig.class,ServiceBeanConfig.class,RepositoryTestConfig.class})
public class SessionServiceTest {

    @Autowired
    @Qualifier("sessionServiceTestTarget")
    private ISessionService sessionService;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private IAppService appService;
    @Autowired
    private IValidator validator;

    Application testApp;
    SessionDTO sessionDTO;
    Session testSession;

    @Before
    public void setup() {
        testApp = new ApplicationImpl();
        testApp.setName("test");
        testApp.setPrivateKey("2acf8190-8e32-11e6-bdf4-0800200c9a66");
        testApp.setId("123");
       // testApp.setOwner(Mockito.mock(User.class));
        testApp.setDateAdded(new Date());


        sessionDTO = new SessionDTO();
        sessionDTO.setAppID(testApp.getId());
        sessionDTO.setId(123L);

        testSession = new AppSession();
        testSession.setApp(testApp);
        testSession.setEndTime(new Date());
        testSession.setStartTime(new Date());
        testSession.setId(sessionDTO.getId());

        Mockito.when(appService.findByID(Mockito.anyString())).thenReturn(testApp);
        Mockito.when(appService.findByID(null)).thenThrow(NotFoundException.class);
        Mockito.when(sessionRepository.save(Mockito.any(Session.class))).thenReturn(testSession);
        Mockito.when(sessionRepository.findOne(testSession.getId())).thenReturn(testSession);
        Mockito.when(sessionRepository.findAllByApp(Mockito.any(Application.class))).thenReturn(Collections.singletonList(testSession));
        Mockito.when(sessionRepository.findAll()).thenReturn(Collections.singletonList(testSession));

        Mockito.doThrow(new IllegalArgumentException()).when(validator).isTrue(Mockito.eq(false),Mockito.anyString());
        Mockito.doThrow(new NullPointerException()).when(validator).notNull(Mockito.isNull(),Mockito.anyString());
    }

    @Test
    public void testCreateSession() {
        Session session = sessionService.createSession(testSession);
        Assert.assertNotNull(session);
        Assert.assertEquals(session.getId(), testSession.getId());
    }
    @Test(expected = NullPointerException.class)
    public void testCreateSessionIsNull()
    {
        sessionService.createSession(null);
    }
    @Test
    public void testFinByID() {
        Session session = sessionService.findByID(sessionDTO.getId());
        Assert.assertNotNull(session);
        Assert.assertEquals(session.getId(), testSession.getId());
    }
    @Test(expected = IllegalArgumentException.class)
    public void testFindByIdIsNegative()
    {
        sessionService.findByID(-1l);
    }
    @Test(expected = NullPointerException.class)
    public void testFindByIdIsNull()
    {
        sessionService.findByID(null);
    }
    @Test
    public void testDeleteByID() {
        Session session = sessionService.deleteSession(sessionDTO.getId());
        Assert.assertNotNull(session);
        Assert.assertEquals(session.getId(), testSession.getId());
    }
    @Test
    public void testDeleteNotExisting() {
        SessionDTO sessionDTO=new SessionDTO();
        sessionDTO.setId(2L);
        Session session = sessionService.deleteSession(sessionDTO.getId());
        Assert.assertNull(session);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testDeleteByIdIsNegative()
    {
        sessionService.findByID(-1l);
    }
    @Test(expected = NullPointerException.class)
    public void testDeleteByIdIsNull()
    {
        sessionService.findByID(null);
    }
    @Test
    public void testDeleteSessionsList() {
        Collection<Session> list = sessionService.deleteSessionList(Collections.singletonList(testSession));
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size()>0);
    }

    @Test(expected = NullPointerException.class)
    public void testDeleteNullList()
    {
        sessionService.deleteSessionList(null);
    }

}

