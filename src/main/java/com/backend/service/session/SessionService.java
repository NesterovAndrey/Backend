package com.backend.service.session;


import com.backend.domain.application.Application;
import com.backend.domain.session.Session;
import com.backend.repository.SessionRepository;

import com.backend.service.app.IAppService;
import utils.precondition.IValidator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Date;


public class SessionService implements ISessionService {

    @Autowired
    private IValidator validator;
    @Autowired
    private IAppService appService;

    @Autowired
    private SessionRepository sessionRepository;

    public Session createSession(Session session)
    {
        validator.notNull(session,"Session must be not null");
        session.setStartTime(new Date());
        session.setEndTime(new Date());
        return sessionRepository.save(session);
    }

    @Override
    public Collection<Session> findByApp(Application app) {
        validator.notNull(app,"Application must be not null");
        return sessionRepository.findAllByApp(app);
    }

    public Session findByID(Long id)
    {
        validator.notNull(id,"Session id must be not null");
        validator.isTrue(id>=0,"Session id must be greater then 0");
        Session session=sessionRepository.findOne(id);
        return session;
    }

    public Session deleteSession(Long id) {
        validator.notNull(id,"Session id must be not null");
        validator.isTrue(id>=0,"Session id must be equal or greater then 0");
        Session session= findByID(id);
        sessionRepository.delete(session);
        return session;
    }
    public Collection<Session> deleteSessionList(Collection<Session> list) {
        validator.notNull(list,"Session list must be not null");
        sessionRepository.delete(list);
        return list;
    }
}
