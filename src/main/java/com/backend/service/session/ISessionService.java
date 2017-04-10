package com.backend.service.session;

import com.backend.domain.application.Application;
import com.backend.domain.session.Session;
import com.backend.service.IBaseService;

import java.util.Collection;

public interface ISessionService extends IBaseService<Session,Long> {
   Session createSession(Session session);
   Collection<Session> findByApp(Application app);
   Session deleteSession(Long sessionID);
   Collection<Session> deleteSessionList(Collection<Session> list);
}
