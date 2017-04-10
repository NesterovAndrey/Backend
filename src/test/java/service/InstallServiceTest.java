package service;

import com.backend.domain.application.Application;
import com.backend.domain.application.applicationInstall.ApplicationInstall;
import com.backend.domain.application.applicationInstall.IApplicationInstall;
import com.backend.service.install.IInstallService;
import com.backend.repository.InstallRepository;
import utils.precondition.IValidator;
import config.MapperTestConfig;
import config.RepositoryTestConfig;
import config.ServiceTestConfig;
import config.ValidatorTestConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;
import java.util.Collections;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes ={ValidatorTestConfig.class, MapperTestConfig.class, ServiceTestConfig.class,ServiceBeanConfig.class, RepositoryTestConfig.class})
public class InstallServiceTest {

    @Autowired
    private IValidator validator;
    @Autowired
    private InstallRepository installRepository;
    @Autowired
    @Qualifier("installServiceTestTarget")
    private IInstallService installService;


    private IApplicationInstall install;
    private IApplicationInstall spyInstall;
    private Application app;

    @Before
    public void setup() {

        this.install=new ApplicationInstall();
        this.install.setId("123");
        this.install.setApp(app);
        spyInstall=Mockito.spy(this.install);
        Mockito.when(installRepository.save(spyInstall)).thenReturn(spyInstall);

        Mockito.when(installRepository.findAll()).thenReturn(Collections.singletonList(install));

        Mockito.doThrow(new IllegalArgumentException()).when(validator).isTrue(Mockito.eq(false),Mockito.anyString());
        Mockito.doThrow(new NullPointerException()).when(validator).notNull(Mockito.isNull(),Mockito.anyString());
    }

    @Test
    public void testSaveInstall()
    {
        IApplicationInstall install=installService.saveInstall(this.spyInstall);
        Assert.assertNotNull(install);
        Assert.assertTrue(install==this.spyInstall);
    }
    @Test(expected = NullPointerException.class)
    public void testSaveNullInstall()
    {
        installService.saveInstall(null);
    }
    @Test
    public void testFindAll()
    {
        Collection<IApplicationInstall> appInstalls=installService.findAll();
        Assert.assertNotNull(appInstalls);
        Assert.assertTrue(appInstalls.size()>0);
    }
}
