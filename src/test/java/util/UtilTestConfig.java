package util;

import com.backend.service.app.IAppService;
import com.backend.service.event.IEventService;
import com.backend.service.session.ISessionService;
import com.backend.service.user.IUserService;
import com.backend.service.userProfile.IUserProfileService;
import utils.calendarService.ICalendarWrapper;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UtilTestConfig {

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return Mockito.mock(PasswordEncoder.class);
    }
    @Bean
    public ICalendarWrapper calendarWrapper()
    {
        return Mockito.mock(ICalendarWrapper.class);
    }
    @Bean
    public IUserService userService() {
        return Mockito.mock(IUserService.class);
    }

    @Bean
    public IAppService appService() {
        return Mockito.mock(IAppService.class);
    }

    @Bean
    public IEventService eventService() {
        return Mockito.mock(IEventService.class);
    }

    @Bean
    public IUserProfileService profileService(){return Mockito.mock(IUserProfileService.class);}

    @Bean
    ISessionService sessionService() {
        return Mockito.mock(ISessionService.class);
    }
   /* @Autowired
    private IEventService eventService;
    @Autowired
    private ISessionService sessionService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IAppService appService;
    @Autowired
    private IUserProfileService profileService;
    @Bean
    public ModelMapper modelMapper() {
        final ModelMapper mapper = new ModelMapper();

        /////FROM DTO//////////////
        mapper.createTypeMap(DataDTO.class, Data.class).setProvider(provisionRequest -> new DataImpl());
        mapper.createTypeMap(ApplicationDTO.class, Application.class).setProvider(provisionRequest -> new ApplicationImpl());
        mapper.createTypeMap(SessionDTO.class, Session.class).setProvider(provisionRequest -> new AppSession());
        mapper.createTypeMap(EventDTO.class,Event.class).setProvider(provisionRequest -> new Event());

        //////////////TO DTO//////////////
        //mapper.createTypeMap(Data.class, DataDTO.class).setProvider(new Provider<DataDTO>() {
        //  @Override
        //  public DataDTO get(ProvisionRequest<DataDTO> provisionRequest) {
        //     return new DataDTO();
        // }
        // });

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
                //using(new FromStringToID()).map(source.getPrivateKey()).setPrivateKey(null);
            }
        });

        mapper.addMappings(new PropertyMap<UserDTO, User>() {
            @Override
            protected void configure() {

               // using(new FromIdToApps()).map(source.getId()).seApps(null);
            }
        });
        mapper.addMappings(new PropertyMap<EventDTO, Event>() {
            @Override
            protected void configure() {
                using(new FromStringToApp()).map(source.getAppID()).setApp(null);
                using(new FromLongToSession()).map(source.getSessionID()).setSession(null);
                with(provisionRequest -> new ArrayList<Data>()).map(source.getDataList()).setData(null);

                //skip().setData(null);

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
                //using(new FromIDToString()).map(source.getPrivateKey()).setPrivateKey(null);
            }
        });
        mapper.addMappings(new PropertyMap<UserProfile, UserProfileDTO>() {
            @Override
            protected void configure() {
               // using(new FromUserProfileToLong()).map(source.getId()).setId(null);
               // using(new FromUserToString()).map(source.getOwner()).setOwnerID(null);
                //using(new FromIDToString()).map(source.getPrivateKey()).setPrivateKey(null);
            }
        });

        mapper.addMappings(new PropertyMap<Event, EventDTO>() {
            @Override
            protected void configure() {

                using(new FromAppToString()).map(source.getApp()).setAppID(null);
                using(new FromSessionToLong()).map(source.getSession()).setSessionID(null);
                with(provisionRequest -> new ArrayList<DataDTO>()).map(source.getData()).setDataList(null);

                //skip().setData(null);

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
        protected String convert(Application application)
        {
            return application==null?"":application.getId();
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
    /*class FromIdToApps extends AbstractConverter<String,List<Application>>
    {

        @Override
        protected List<Application> map(String s) {
            List<Application> apps=s!=null?userService.findByID(s).getApps():new ArrayList<Application>();
            return apps;
        }
    }*/
   /* class FromIDToString extends AbstractConverter<IID,String>
    {

        @Override
        protected String convert(IID id) {
            return id==null?null:id.toString();
        }
    }
    */

}
