package com.scm.services.impl;


import com.scm.Helper.AppConstant;
import com.scm.Helper.ResourcesNotFound;
import com.scm.entity.User;
import com.scm.repo.Sign_upRepo;
import com.scm.services.Sign_upService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class Sign_upServiceImpl implements Sign_upService {

    private final Sign_upRepo sign_upRepo;
    private final PasswordEncoder passwordEncoder;

    public Sign_upServiceImpl(Sign_upRepo signUpRepo, PasswordEncoder passwordEncoder) {
        sign_upRepo = signUpRepo;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User saveUser(User user) {
        String userId = UUID.randomUUID().toString();
        //set user id
        user.setUserId(userId);

        //set user password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //set user role
        user.setRolesList(List.of(AppConstant.ROLE_USER));

        return sign_upRepo.save(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
        User user = sign_upRepo.findById(id).orElseThrow(()->new ResourcesNotFound("User Not Found"));
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> updateUser(User user) {
        User updateUser = sign_upRepo.findById(user.getUserId()).orElseThrow(()-> new ResourcesNotFound("User not found !"));
//        updateUser.setUserName(user.getUserName());
        updateUser.setEmail(user.getEmail());
        updateUser.setPassword(user.getPassword());
        updateUser.setPhone(user.getPhone());
        updateUser.setProfilePicture(user.getProfilePicture());
        updateUser.setPhoneVerified(user.isPhoneVerified());
        updateUser.setProvider(user.getProvider());
        updateUser.setProviderUserId(user.getProviderUserId());

        User savedUser = sign_upRepo.save(updateUser);
        return Optional.of(savedUser);
    }

    @Override
    public void deleteUser(String id) {
        User user = sign_upRepo.findById(id).orElseThrow(()-> new ResourcesNotFound("User not found !"));
        sign_upRepo.delete(user);
    }

    @Override
    public boolean isUserExist(String id) {
        User user = sign_upRepo.findById(id).orElse(null);
        return user != null;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User user = sign_upRepo.findByEmail(email).orElse(null);
        return user!=null;
    }

    @Override
    public List<User> getAllUsers() {
        return sign_upRepo.findAll();
    }
}
