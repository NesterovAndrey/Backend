package com.backend.service.user;

import com.backend.domain.authenticationUser.User;
import com.backend.repository.UserRepository;
import utils.precondition.IValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserService implements IUserService{

    @Autowired
    private IValidator validator;

    @Autowired
    private UserRepository userRepository;


    public User register(User user) {
        validator.notNull(user,"User must be not null");
        userRepository.save(user);
        return user;
    }

    public User deleteUser(String userID) {
        validator.notNull(userID,"User ID must be not null");
        validator.isTrue(!userID.isEmpty(),"USer ID must be not empty");
        User user= findByID(userID);
        userRepository.delete(user);
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public User findByID(String userID) {
        validator.notNull(userID, "User ID must be not null");
        validator.isTrue(!userID.isEmpty(),"USer ID must be not empty");
        User user=userRepository.findOne(userID);
        return user;
    }

    public User getUserByName(String username) {
        validator.notNull(username,"Username must be not null");
        validator.isTrue(!username.isEmpty(),"Username must be not empty");
        User user=userRepository.findUserByUsername(username);
        return user;
    }
    public Boolean isExists(String username)
    {
        Logger logger=LoggerFactory.getLogger(this.getClass());
        logger.info("USERNAME "+username+" "+username.isEmpty());
        validator.notNull(username,"Username must be not null");
        validator.isTrue(!username.isEmpty(),"Username must be not empty");
        return userRepository.findUserByUsername(username)!=null;
    }
    private boolean checkIsNull(User user)
    {
       return user==null;
    }
}
