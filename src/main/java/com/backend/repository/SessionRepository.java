package com.backend.repository;

        import com.backend.domain.application.Application;
        import com.backend.domain.session.Session;
        import org.springframework.data.jpa.repository.JpaRepository;

        import java.util.List;

public interface SessionRepository extends JpaRepository<Session,Long> {
    List<Session> findAllByApp(Application app);
}
