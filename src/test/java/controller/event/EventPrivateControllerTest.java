package controller.event;
import static org.hamcrest.Matchers.*;
import com.backend.domain.application.Application;
import com.backend.domain.event.Event;
import com.backend.domain.event.EventDTO;
import com.backend.domain.event.data.Data;
import com.backend.domain.event.data.DataDTO;
import com.backend.domain.event.data.DataImpl;
import com.backend.service.session.ISessionService;
import com.backend.domain.session.Session;
import com.backend.web.authentication.JpaUserDetails;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringJUnit4ClassRunner.class)
public class EventPrivateControllerTest extends BaseEventTest {
    @Autowired
    private ISessionService sessionService;

    DataDTO dataDTO;
    Data data;
    EventDTO eventDTO;
    Event event;
    @Before
    public void init()
    {
        dataDTO=new DataDTO();
        dataDTO.setName("x");
        dataDTO.setValue("100");

        data=new DataImpl();
        data.setName("x");
        data.setValue("100");
        data.setId(1L);

        eventDTO=new EventDTO();
        eventDTO.setAppID("1");
        eventDTO.setId(1L);
        eventDTO.setSessionID(1L);
        eventDTO.setName("event");
        eventDTO.setDataList(Collections.singletonList(dataDTO));

        event=new Event();
        event.setName("event");
        event.setId(1L);
        event.setSession(Mockito.mock(Session.class));
        event.setApp(Mockito.mock(Application.class));
        event.setData(Collections.singletonList(data));
    }


    @Test
    public void testGetAllEvents() throws Exception {


        Session session=Mockito.mock(Session.class);
        session.setId(1L);

        Mockito.when(sessionService.findByID(1L)).thenReturn(session);
        Mockito.when(eventService.getAllEvents()).thenReturn(Collections.singletonList(event));
        Mockito.when(mapperUtil.map(Mockito.anyList(),Mockito.eq(Event.class))).thenReturn(Collections.singletonList(event));
        Mockito.when(mapperUtil.map(Mockito.anyList(),Mockito.eq(EventDTO.class))).thenReturn(Collections.singletonList(eventDTO));

        JpaUserDetails jpaUserDetails=Mockito.mock(JpaUserDetails.class);
        Mockito.when(jpaUserDetails.getAuthorities()).thenReturn(Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        TestingAuthenticationToken testingAuthenticationToken=new TestingAuthenticationToken(jpaUserDetails,null);
        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);

        mockMvc.perform(get("/private/application/{appID}/event","1","1").contentType(mediaType).principal(testingAuthenticationToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(1)));


    }
    @Test
    public void testGetEvent() throws Exception {
        JpaUserDetails jpaUserDetails=new JpaUserDetails();
        TestingAuthenticationToken testingAuthenticationToken=new TestingAuthenticationToken(jpaUserDetails,null);
        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);

        Mockito.when(eventService.findByID(1L)).thenReturn(event);
        Mockito.when(mapperUtil.map(Mockito.any(Event.class),Mockito.eq(EventDTO.class))).thenReturn(eventDTO);

        mockMvc.perform(get("/private/application/{appID}/event/{eventID}","1","1","1").contentType(mediaType).principal(testingAuthenticationToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testDeleteEvent() throws Exception {
        JpaUserDetails jpaUserDetails=new JpaUserDetails();
        TestingAuthenticationToken testingAuthenticationToken=new TestingAuthenticationToken(jpaUserDetails,null);
        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);

        Mockito.when(eventService.findByID(1L)).thenReturn(event);
        Mockito.when(mapperUtil.map(Mockito.any(Event.class),Mockito.eq(EventDTO.class))).thenReturn(eventDTO);

        mockMvc.perform(delete("/private/application/{appID}/event/{eventID}","1","1",1L).contentType(mediaType).principal(testingAuthenticationToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testDeleteAllEvents() throws Exception {

        Session session=Mockito.mock(Session.class);
        session.setId(1L);

        JpaUserDetails jpaUserDetails=new JpaUserDetails();
        TestingAuthenticationToken testingAuthenticationToken=new TestingAuthenticationToken(jpaUserDetails,null);
        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);

        Mockito.when(sessionService.findByID(1L)).thenReturn(session);
        //Mockito.when(session.getEvents()).thenReturn(Arrays.asList(event));
        Mockito.when(eventService.deleteEventsList(Mockito.anyList())).thenReturn(Collections.singletonList(event));
        Mockito.when(mapperUtil.map(Mockito.anyList(),Mockito.eq(EventDTO.class))).thenReturn(Collections.singletonList(eventDTO));

        mockMvc.perform(delete("/private/application/{appID}/event","1",1L).contentType(mediaType).principal(testingAuthenticationToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
