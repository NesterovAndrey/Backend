package com.backend.domain.query;

import javax.persistence.*;

public final class RetentionQuery extends NativeQuery {
    private static final String QUERY = "SELECT\n" +
            " session.start_time, \n" +
            " COUNT(DISTINCT(session.install_id)) AS active_installs, \n" +
            " COUNT(DISTINCT(future_session.install_id)) AS retained_installs,\n" +
            " COUNT(DISTINCT(future_session.install_id)) / \n" +
            " COUNT(DISTINCT(session.install_id)) as retention\n" +
            "FROM session\n" +
            "LEFT JOIN session as future_session ON\n" +
            "  session.install_id= future_session.install_id\n" +
            "  AND session.start_time= future_session.start_time - INTERVAL :day DAY\n" +
            "GROUP BY session.start_time";

    public RetentionQuery(EntityManager entityManager, int dayNum) {
        super(entityManager, QUERY);
        registerParam("day", dayNum);
    }
}
