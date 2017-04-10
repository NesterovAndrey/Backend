package com.backend.service.user;

import com.backend.domain.authenticationUser.User;
import com.backend.service.IBaseService;

import java.util.List;


public interface IUserService extends IBaseService<User,String> {
   User register(User user);
   User deleteUser(String userID);
   List<User> getAllUsers();
   User getUserByName(String name);
   Boolean isExists(String username);
}
