package service;

import com.backend.domain.application.Application;
import com.backend.domain.event.Event;
import com.backend.domain.event.EventDTO;
import com.backend.service.event.IEventService;
import com.backend.service.session.ISessionService;
import com.backend.domain.session.AppSession;
import com.backend.domain.session.Session;
import com.backend.repository.EventRepository;
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

import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes ={ValidatorTestConfig.class, MapperTestConfig.class,ServiceTestConfig.class,ServiceBeanConfig.class, RepositoryTestConfig.class})
public class EventServiceTest {


    @Autowired
    @Qualifier("eventServiceTestTarget")
    private IEventService eventService;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private ISessionService sessionService;
    @Autowired
    private IValidator validator;

    private EventDTO testEventDTO;
    private Event testEvent;
    private Session testSession;

    @Before
    public void setup()
    {
        testSession = new AppSession();
        testSession.setApp(Mockito.mock(Application.class));
        testSession.setEndTime(new Date());
        testSession.setStartTime(new Date());
        testSession.setId(123L);

        testEventDTO=new EventDTO();
        testEventDTO.setId(1L);
        testEventDTO.setSessionID(testSession.getId());
        testEventDTO.setAppID("123");
        testEventDTO.setDataList(Mockito.mock(List.class));
        testEventDTO.setName("event");

        testEvent=new Event();
        testEvent.setId(testEventDTO.getId());
        testEvent.setSession(testSession);
        testEvent.setName(testEventDTO.getName());
        testEvent.setApp(testSession.getApp());
        testEvent.setData(Mockito.mock(List.class));

        Mockito.when(sessionService.findByID(Mockito.anyLong())).thenReturn(testSession);
        Mockito.when(eventRepository.save(Mockito.any(Event.class))).thenReturn(testEvent);
        Mockito.when(eventRepository.findOne(testEvent.getId())).thenReturn(testEvent);

        Mockito.doThrow(new IllegalArgumentException()).when(validator).isTrue(Mockito.eq(false),Mockito.anyString());
        Mockito.doThrow(new NullPointerException()).when(validator).notNull(Mockito.isNull(),Mockito.anyString());
    }
    @Test
    public void testCreateEvent()
    {
        Event event=eventService.createEvent(testEvent);
        Assert.assertNotNull(event);
        Assert.assertEquals(event.getId(),testEvent.getId());
    }
    @Test(expected = NullPointerException.class)
    public void testCreateEventIsNull()
    {
        Event event=eventService.createEvent(null);
        Assert.assertNull(event);
    }
    public void testGetEventByID()
    {
        Event event=eventService.findByID(testEvent.getId());
        Assert.assertNotNull(event);
        Assert.assertEquals(event.getId(),testEvent.getId());
    }
    @Test(expected = NullPointerException.class)
    public void testGetEventByIDIsNull()
    {
        eventService.findByID(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testGetEventByIDIsEmpty()
    {
        eventService.findByID(-1l);
    }

    @Test
    public void testDeleteEvent() {
        Event event=eventService.deleteEvent(testEvent);
        Assert.assertNotNull(event);
        Assert.assertEquals(event.getId(),testEvent.getId());
    }

    @Test(expected = NullPointerException.class)
    public void testDeleteEventIsNull()
    {
        eventService.deleteEvent((Event)null);
    }
    @Test
    public void testDeleteEventsList() {
       Collection<Event> list=eventService.deleteEventsList(Collections.singletonList(testEvent));
        Assert.assertTrue(list.size()>0);
    }
    @Test(expected = NullPointerException.class)
    public void testDeleteEventsListIsNull()
    {
        Collection<Event> list=eventService.deleteEventsList(null);
    }

}
