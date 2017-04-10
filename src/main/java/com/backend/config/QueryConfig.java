package com.backend.config;


import com.backend.domain.query.INativeQueryBuilder;
import com.backend.domain.query.NativeQueryBuilder;
import com.backend.domain.query.NativeQueryEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class QueryConfig {


    @Bean
    public String MAUQueryString()
    {
        return "SELECT MONTH(start_time) as MONTH_OF_YEAR,YEAR(start_time) AS YEAR,COUNT(DISTINCT(install_id)) AS MAU\n"+
                "FROM session\n"+
                "WHERE start_time\n" +
                "BETWEEN CAST(:from  AS DATE)\n" +
                "AND  CAST(:to AS DATE)\n" +
                "GROUP BY MONTH(start_time),YEAR(start_time)";
    }
    @Bean
    public String WAUQueryString()
    {
        return "SELECT WEEKOFYEAR(start_time) as WEEK_NUMBER,YEAR(start_time) AS YEAR,COUNT(DISTINCT(install_id)) AS WAU\n"+
                "FROM session\n"+
                "WHERE start_time\n"+
                "BETWEEN CAST(:from  AS DATE)\n"+
                "AND  CAST(:to AS DATE)\n"+
                "GROUP BY WEEKOFYEAR(start_time),YEAR(start_time)";
    }
    @Bean
    public String DAUQueryString()
    {
        return "SELECT start_time,COUNT(DISTINCT(install_id)) AS DAU\n"+
                "FROM session\n"+
                "WHERE start_time\n"+
                "BETWEEN CAST(:from AS DATE)\n"+
                "AND  CAST(:to AS DATE)\n"+
                "GROUP BY start_time";
    }
    @Bean
    public String RetentionQueryString()
    {
        return "SELECT\n" +
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
    }
    @Bean
    public String newUserRetentionString()
    {
        return "SELECT\n" +
                " session.start_time, \n" +
                " COUNT(DISTINCT(session.install_id)) AS active_installs, \n" +
                " COUNT(DISTINCT(future_session.install_id)) AS retained_installs,\n" +
                " COUNT(DISTINCT(future_session.install_id)) / \n" +
                " COUNT(DISTINCT(session.install_id)) as retention\n" +
                "FROM session\n" +
                "JOIN app_install ON\n" +
                "  session.install_id = app_install.id \n" +
                "  AND app_install.install_date= session.start_time\n" +
                "LEFT JOIN session as future_session ON\n" +
                "  session.install_id= future_session.install_id\n" +
                "  AND session.start_time= future_session.start_time - INTERVAL :day DAY\n" +
                "GROUP BY app_install.install_date";
    }
    @Bean
    public String existringUserRetentionString()
    {
        return "SELECT\n" +
                " session.start_time, \n" +
                " COUNT(DISTINCT(session.install_id)) AS active_installs, \n" +
                " COUNT(DISTINCT(future_session.install_id)) AS retained_installs,\n" +
                " COUNT(DISTINCT(future_session.install_id)) / \n" +
                " COUNT(DISTINCT(session.install_id)) as retention\n" +
                "FROM session\n" +
                "JOIN app_install ON\n" +
                "  session.install_id = app_install.id \n" +
                "  AND app_install.install_date != session.start_time\n" +
                "LEFT JOIN session as future_session ON\n" +
                "  session.install_id= future_session.install_id\n" +
                "  AND session.start_time= future_session.start_time - INTERVAL :day DAY\n" +
                "GROUP BY session.start_time";
    }
    @Bean
    @Scope("prototype")
    @Autowired
    public INativeQueryBuilder nativeQueryBuilder(EntityManager entityManager)
    {
        INativeQueryBuilder queryBuilder=new NativeQueryBuilder(entityManager);
        queryBuilder.registerQueryString(NativeQueryEnum.MAU,MAUQueryString());
        queryBuilder.registerQueryString(NativeQueryEnum.WAU,WAUQueryString());
        queryBuilder.registerQueryString(NativeQueryEnum.DAU,DAUQueryString());
        queryBuilder.registerQueryString(NativeQueryEnum.RETENTION,RetentionQueryString());
        queryBuilder.registerQueryString(NativeQueryEnum.EXISTING_USER_RETENTION,existringUserRetentionString());
        queryBuilder.registerQueryString(NativeQueryEnum.NEW_USER_RETENTION,newUserRetentionString());
        return queryBuilder;
    }
}
