package com.backend.service.event;

import com.backend.domain.event.Event;
import com.backend.repository.EventRepository;
import com.backend.service.session.ISessionService;
import utils.precondition.IValidator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

public class EventService implements IEventService {

    @Autowired
    private IValidator validator;
    @Autowired
    private ISessionService sessionService;

    @Autowired
    private EventRepository eventRepository;

    public Event createEvent(Event event)
    {
        validator.notNull(event,"Event must be not null");
        return eventRepository.save(event);
    }
    public Event findByID(Long id)
    {
        validator.notNull(id,"Event id must be not null");
        validator.isTrue(id>=0,"Event ID must be positive value");
        Event event =eventRepository.findOne(id);
        return event;
    }

    @Override
    public Collection<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Collection<Event> deleteAll() {
        Collection<Event> events=getAllEvents();
        eventRepository.deleteAll();
        return events;
    }

    public Event deleteEvent(Long id) {
        validator.notNull(id,"Event id must be not null");
        validator.isTrue(id>=0,"Event ID must be positive value");
        Event event = findByID(id);
        eventRepository.delete(event);
        return event;
    }

    @Override
    public Event deleteEvent(Event event) {
        validator.notNull(event,"Event id must be not null");
        eventRepository.delete(event);
        return event;
    }

    public List<Event> deleteEventsList(List<Event> events) {
        validator.notNull(events,"Events list must be not empty");
        eventRepository.delete(events);
        return events;
    }


}
