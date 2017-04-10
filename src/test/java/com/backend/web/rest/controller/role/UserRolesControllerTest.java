package com.backend.web.rest.controller.role;

import com.backend.domain.application.ApplicationImpl;
import com.backend.domain.application.roles.UserRole;
import com.backend.domain.authenticationUser.profile.UserProfile;
import com.backend.service.role.IRoleService;
import config.MapperTestConfig;
import config.ServiceTestConfig;
import config.ValidatorTestConfig;
import controller.ControllerTestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.nio.charset.Charset;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes ={ControllerTestConfig.class, ValidatorTestConfig.class, MapperTestConfig.class, ServiceTestConfig.class})
@WebAppConfiguration
public class UserRolesControllerTest {
    @Autowired
    private HandlerExceptionResolver restExceptionHandler;
    @Autowired
    private UserRolesController userRolesController;
    @Autowired
    private IRoleService roleService;

    private MockMvc mockMvc;
    private UserProfile userProfile;
    private UserRole role;

    private MediaType mediaType=new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
    @Before
    public void setup()
    {
        userProfile=new UserProfile();
        userProfile.setId(1l);
        userProfile.setPrincipal("user");
        userProfile.setUsername("user");
        userProfile=Mockito.spy(userProfile);
        role=new UserRole();
        role.setId(1l);
        role.setApp(new ApplicationImpl());
        role.setProfile(userProfile);
        role=Mockito.spy(role);

        Mockito.when(roleService.findAllByProfile(Mockito.any(UserProfile.class))).thenReturn(Collections.singletonList(role));
        mockMvc= MockMvcBuilders.standaloneSetup(userRolesController).setMessageConverters(new MappingJackson2HttpMessageConverter()).setHandlerExceptionResolvers(restExceptionHandler).build();
    }

    @Test
    public void testGetRoles() throws Exception {
        TestingAuthenticationToken testingAuthenticationToken=new TestingAuthenticationToken(userProfile,null);
        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);

        mockMvc.perform(get("/private/roles").contentType(mediaType).principal(testingAuthenticationToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}