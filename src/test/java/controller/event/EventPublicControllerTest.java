package controller.event;
import static org.hamcrest.Matchers.*;

import com.backend.domain.application.Application;
import com.backend.domain.event.Event;
import com.backend.domain.event.EventDTO;
import com.backend.domain.event.data.Data;
import com.backend.domain.event.data.DataDTO;
import com.backend.domain.event.data.DataImpl;
import com.backend.domain.session.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
@RunWith(SpringJUnit4ClassRunner.class)
public class EventPublicControllerTest extends BaseEventTest {

    @Test
    public void testCreateEvent() throws Exception {
        DataDTO dataDTO=new DataDTO();
        dataDTO.setName("x");
        dataDTO.setValue("100");

        Data data=new DataImpl();
        data.setName("x");
        data.setValue("100");
        data.setId(1L);

        EventDTO eventDTO=new EventDTO();
        eventDTO.setAppID("1");
        eventDTO.setId(1L);
        eventDTO.setSessionID(1L);
        eventDTO.setName("event");
        eventDTO.setDataList(Collections.singletonList(dataDTO));

        Event event=new Event();
        event.setName("event");
        event.setId(1L);
        event.setSession(Mockito.mock(Session.class));
        event.setApp(Mockito.mock(Application.class));
        event.setData(Collections.singletonList(data));

        Mockito.when(eventService.createEvent(event)).thenReturn(event);
        Mockito.when(mapperUtil.map(Mockito.any(EventDTO.class),Mockito.eq(Event.class))).thenReturn(event);
        Mockito.when(mapperUtil.map(Mockito.any(Event.class),Mockito.eq(EventDTO.class))).thenReturn(eventDTO);

        MvcResult result=mockMvc.perform(post("/public/application/{appID}/session/{sessionID}/event/","1",1L).content(toJSon(eventDTO)).contentType(mediaType))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("id",is(1))).andReturn();
    }

}
