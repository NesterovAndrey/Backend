package com.backend.config;

import com.backend.domain.application.Application;
import com.backend.domain.application.ApplicationDTO;
import com.backend.domain.application.ApplicationImpl;
import com.backend.domain.application.applicationInstall.*;
import com.backend.domain.event.Event;
import com.backend.domain.event.EventDTO;
import com.backend.domain.event.data.Data;
import com.backend.domain.event.data.DataDTO;
import com.backend.domain.event.data.DataImpl;
import com.backend.service.app.IAppService;
import com.backend.service.event.IEventService;
import com.backend.service.session.ISessionService;
import com.backend.domain.session.AppSession;
import com.backend.domain.session.Session;
import com.backend.domain.session.SessionDTO;
import com.backend.service.user.IUserService;
import com.backend.domain.authenticationUser.User;
import com.backend.domain.authenticationUser.UserDTO;
import com.backend.service.userProfile.IUserProfileService;
import com.backend.domain.authenticationUser.profile.UserProfile;
import com.backend.domain.authenticationUser.profile.UserProfileDTO;
import utils.mapping.IMapperUtil;
import utils.mapping.MapperUtil;
import utils.uid.ID;
import utils.uid.IID;
import utils.calendarService.ICalendarWrapper;
import org.modelmapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.UUID;


@Configuration
public class MappingsConfig {

    @Autowired
    private ICalendarWrapper calendarService;
    @Autowired
    private IEventService eventService;
    @Autowired
    private ISessionService sessionService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserProfileService profileService;
    @Autowired
    private IAppService appService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper= new ModelMapper();

        /////FROM DTO//////////////
        mapper.createTypeMap(DataDTO.class, Data.class).setProvider(provisionRequest -> new DataImpl());
        mapper.createTypeMap(ApplicationDTO.class, Application.class).setProvider(provisionRequest -> new ApplicationImpl());
        mapper.createTypeMap(SessionDTO.class, Session.class).setProvider(provisionRequest -> new AppSession());
        mapper.createTypeMap(EventDTO.class,Event.class).setProvider(provisionRequest -> new Event());

        mapper.createTypeMap(ApplicationInstallDTO.class,IApplicationInstall.class).setProvider(provisionRequest -> new ApplicationInstall());
        mapper.createTypeMap(ApllicationInstallDataDTO.class,IApplicationInstallData.class).setProvider(provisionRequest -> new ApplicationInstallData());
        ///////////////FROM DTO///////////////

        mapper.addMappings(new PropertyMap<SessionDTO, Session>() {
            @Override
            protected void configure() {
                using(new FromStringToApp()).map(source.getAppID()).setApp(null);

            }
        });
        mapper.addMappings(new PropertyMap<ApplicationDTO, Application>() {
            @Override
            protected void configure() {
                using(new FromLongToUserProfile()).map(source.getOwnerID()).setOwner(null);
            }
        });

        mapper.addMappings(new PropertyMap<UserDTO, User>() {
            @Override
            protected void configure() {
                using(new EncodePassword()).map(source.getPassword()).setPassword(null);
            }
        });
        mapper.addMappings(new PropertyMap<EventDTO, Event>() {
            @Override
            protected void configure() {
                using(new FromStringToApp()).map(source.getAppID()).setApp(null);
                using(new FromLongToSession()).map(source.getSessionID()).setSession(null);
                with(provisionRequest -> new ArrayList<Data>()).map(source.getDataList()).setData(null);
            }
        });
        mapper.addMappings(new PropertyMap<ApplicationInstallDTO, ApplicationInstall>() {
            @Override
            protected void configure() {
                using(new FromStringToApp()).map(source.getAppID()).setApp(null);
            }
        });

        //////////TO DTO//////////////////

        mapper.addMappings(new PropertyMap<AppSession, SessionDTO>() {
            @Override
            protected void configure() {
                using(new FromAppToString()).map(source.getApp()).setAppID(null);
            }
        });

        mapper.addMappings(new PropertyMap<Application, ApplicationDTO>() {
            @Override
            protected void configure() {
                using(new FromUserProfileToLong()).map(source.getOwner()).setOwnerID(null);
            }
        });
        mapper.addMappings(new PropertyMap<UserProfile, UserProfileDTO>() {
            @Override
            protected void configure() {

            }
        });

        mapper.addMappings(new PropertyMap<Event, EventDTO>() {
            @Override
            protected void configure() {

                using(new FromAppToString()).map(source.getApp()).setAppID(null);
                using(new FromSessionToLong()).map(source.getSession()).setSessionID(null);
                with(provisionRequest -> new ArrayList<DataDTO>()).map(source.getData()).setDataList(null);
            }
        });


        return mapper;
    }
    ///////FROM DTO////////////////////

    class FromStringToUser extends AbstractConverter<String, User> {
        @Override
        protected User convert(String s) {
            return s==null?null:userService.findByID(s);
        }
    }

    class FromStringToApp extends AbstractConverter<String, Application> {
        @Override
        protected Application convert(String s) {
            Logger log= LoggerFactory.getLogger(this.getClass());
            log.info("FROM S TO APP "+s);
            return s==null?null:appService.findByID(s);
        }
    }

    class FromLongToEvent extends AbstractConverter<Long, Event> {
        @Override
        protected Event convert(Long l) {
            return l==null?null:eventService.findByID(l);
        }
    }

    class FromLongToSession extends AbstractConverter<Long, Session> {
        @Override
        protected Session convert(Long l) {
            Logger log= org.slf4j.LoggerFactory.getLogger(this.getClass());
            log.info("SESSION "+l);
            return l==null?null:sessionService.findByID(l);
        }
    }
    class FromLongToUserProfile extends AbstractConverter<Long,UserProfile> {
        @Override
        protected UserProfile convert(Long l) {
            Logger log= org.slf4j.LoggerFactory.getLogger(this.getClass());
            log.info("UserProfile "+l);
            return l==null?null:profileService.getProfileByID(l);
        }
    }
    class FromStringToID extends AbstractConverter<String,IID>
    {
        @Override
        protected IID convert(String s) {
            return s==null?null:new ID(UUID.fromString(s));
        }
    }
    ////TO DTO///////////////////////////

    class FromUserToString extends AbstractConverter<User, String> {
        @Override
        protected String convert(User user) {
            return user.getId();
        }
    }

    class FromAppToString extends AbstractConverter<Application, String> {
        @Override
        protected String convert(Application app)
        {
            return app==null?"":app.getId();
        }
    }

    class FromEventToLong extends AbstractConverter<Event, Long> {
        @Override
        protected Long convert(Event event) {
            return event.getId();
        }
    }

    class FromSessionToLong extends AbstractConverter<Session, Long> {
        @Override
        protected Long convert(Session session) {
            return session==null?null:session.getId();
        }
    }
    class FromUserProfileToLong extends AbstractConverter<UserProfile, Long> {
        @Override
        protected Long convert(UserProfile profile) {
            return profile==null?null:profile.getId();
        }
    }

    class FromIDToString extends AbstractConverter<IID,String>
    {
        @Override
        protected String convert(IID id) {
            return id==null?null:id.toString();
        }
    }
    class EncodePassword extends AbstractConverter<String,String>
    {

        @Override
        protected String convert(String s) {
            return passwordEncoder.encode(s);
        }
    }
    @Bean
    @Autowired
    public IMapperUtil mapperUtil(ModelMapper modelMapper)
    {
        return new MapperUtil(modelMapper);
    }

}
