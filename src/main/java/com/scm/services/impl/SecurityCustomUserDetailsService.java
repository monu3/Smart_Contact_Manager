package com.scm.services.impl;

import com.scm.repo.Sign_upRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityCustomUserDetailsService implements UserDetailsService {

    private final Sign_upRepo sign_upRepo;

    public SecurityCustomUserDetailsService(Sign_upRepo signUpRepo) {
        sign_upRepo = signUpRepo;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //we have to load our user
        return sign_upRepo.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User not found with email: " + username));
    }
}
