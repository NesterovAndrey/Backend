package mapper;

import com.backend.domain.application.Application;
import com.backend.domain.application.ApplicationDTO;
import com.backend.domain.application.ApplicationImpl;
import com.backend.service.userProfile.IUserProfileService;
import com.backend.domain.authenticationUser.profile.UserProfile;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.modelmapper.*;

import java.lang.reflect.Type;

public class ApplicationMapperTest {

    @Test
    public void testMapper() {


        ApplicationDTO appDto = Mockito.mock(ApplicationDTO.class);
        appDto.setId("1");
        appDto.setOwnerID(1L);
        Application app = Mockito.mock(Application.class);
        app.setId("1");

        ModelMapper mapper = new ModelMapper();
        Type type=new TypeToken<ApplicationImpl>(){}.getType();
        mapper.addMappings(new PropertyMap<ApplicationDTO, ApplicationImpl>() {
            @Override
            protected void configure() {
                using(new Converter()).map(source.getOwnerID()).setOwner(null);
            }
        });

        Application appResult = mapper.map(appDto, type);
        Assert.assertEquals(1L,(long)appResult.getOwner().getId());
    }

    class Converter extends AbstractConverter<Long, UserProfile>
    {
        UserProfile user=Mockito.mock(UserProfile.class);
        IUserProfileService profileService = Mockito.mock(IUserProfileService.class);
        public Converter() {

            Mockito.when(user.getId()).thenReturn(1L);
            Mockito.when(profileService.getProfileByID(Mockito.anyLong())).thenReturn(user);
        }
        @Override
        protected UserProfile convert(Long id) {
            return profileService.getProfileByID(id);
        }
    }
}
