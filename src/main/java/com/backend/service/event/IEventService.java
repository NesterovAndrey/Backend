package com.backend.service.event;

import com.backend.domain.event.Event;
import com.backend.service.IBaseService;

import java.util.Collection;
import java.util.List;


public interface IEventService extends IBaseService<Event,Long> {
    Event createEvent(Event event);
    Collection<Event> getAllEvents();
    Collection<Event> deleteAll();
    Event deleteEvent(Long eventID);
    Event deleteEvent(Event event);
    List<Event> deleteEventsList(List<Event> events);
}
