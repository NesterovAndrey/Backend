package com.backend.web.rest.controller.accounts;

import com.backend.service.user.IUserService;
import com.backend.domain.authenticationUser.User;
import com.backend.domain.authenticationUser.UserDTO;
import com.backend.web.validation.IResourceValidator;
import com.backend.web.rest.exception.ValidationException;
import com.backend.service.userProfile.IUserProfileService;
import com.backend.domain.authenticationUser.profile.UserProfile;
import com.backend.domain.authenticationUser.profile.UserProfileDTO;
import utils.mapping.IMapperUtil;
import utils.message.MessageItemImpl;
import utils.message.MessageSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Formatter;
import java.util.List;

@RestController
@RequestMapping(value = "/accounts")
public class AccountsController {
    @Autowired
    private IResourceValidator<User,String> userValidator;
    @Autowired
    private IMapperUtil mapperUtil;
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserProfileService profileService;
    @Autowired
    private MessageSource messageSource;
    @RequestMapping(method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> createUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult)
    {
       checkErrors(bindingResult);
        User user=mapperUtil.map(userDTO,User.class);
        userService.register(user);

        return new ResponseEntity<>("User created", HttpStatus.CREATED);
    }
    @RequestMapping(value = "/{userID}", method= RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<String> deleteUser(@PathVariable("userID") String userID)
    {
        userValidator.validate(userID);
        userService.deleteUser(userID);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @RequestMapping(method= RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Collection<UserProfileDTO>> getAllUsers()
    {
        Collection<UserProfile> profiles=profileService.getAll();
        Collection<UserProfileDTO> dtos=mapperUtil.map(profiles,UserProfileDTO.class);
        return new ResponseEntity<>(dtos, HttpStatus.OK);

    }

    private void checkErrors(BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            throw new ValidationException(new MessageItemImpl("exception.validation.fields",new Object[]{processBindingFields(bindingResult)}));
        }
    }
    private String processBindingFields(BindingResult bindingResult)
    {
            List<String> errors= new ArrayList<>();
            for(FieldError error:bindingResult.getFieldErrors())
            {
                errors.add(new Formatter().format("%s:%s",error.getField(),error.getDefaultMessage()).toString());
            }
            return StringUtils.join(errors,"\n");
    }
}
