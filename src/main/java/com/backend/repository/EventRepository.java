package com.backend.repository;

import com.backend.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface EventRepository extends JpaRepository<Event,Long> {
    List<Event> findAllBySession(String id);
}
