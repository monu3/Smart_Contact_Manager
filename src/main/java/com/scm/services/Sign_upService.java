package com.scm.services;

import com.scm.entity.User;

import java.util.List;
import java.util.Optional;

public interface Sign_upService {

    User saveUser(User user);
    Optional<User> getUserById(String id);
    Optional<User> updateUser(User user);
    void deleteUser(String id);
    boolean isUserExist(String id);
    boolean isUserExistByEmail(String email);
    List<User> getAllUsers();

}
