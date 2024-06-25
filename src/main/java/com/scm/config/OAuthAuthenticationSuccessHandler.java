package com.scm.config;


import com.scm.Helper.AppConstant;
import com.scm.entity.User;
import com.scm.enums.Providers;
import com.scm.repo.Sign_upRepo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final Sign_upRepo sign_upRepo;

    public OAuthAuthenticationSuccessHandler(Sign_upRepo signUpRepo) {
        sign_upRepo = signUpRepo;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
//                response.sendRedirect("/user/profile");

        //fetch the default data coming by google to this user

        var oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        String authorizedClientRegistrationId  = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();


        //to retrieve the data from the provider
        var oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
        log.info(oauthUser.getName());
        oauthUser.getAttributes().forEach((key, value) ->
                log.info(key + "=" + value)
        );
        log.info(oauthUser.getAuthorities().toString());

        User user2 = new User();
        user2.setUserId(UUID.randomUUID().toString());
        user2.setRolesList(List.of(AppConstant.ROLE_USER));
        user2.setEmailVerified(true);


        if(authorizedClientRegistrationId.equalsIgnoreCase("google")) {

            user2.setEmail(Objects.requireNonNull(oauthUser.getAttribute("email")).toString());
            user2.setUserName(Objects.requireNonNull(oauthUser.getAttribute("name")).toString());
            user2.setProfilePicture(Objects.requireNonNull(oauthUser.getAttribute("picture")).toString());
            user2.setProviderUserId(oauthUser.getName());

        } else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {

            user2.setEmail(Objects.requireNonNull(oauthUser.getAttribute("email")).toString());
            user2.setProfilePicture(Objects.requireNonNull(oauthUser.getAttribute("avatar_url")).toString());
            user2.setProviderUserId(oauthUser.getName());
            user2.setUserName(Objects.requireNonNull(oauthUser.getAttribute("login")).toString());
        }


//        //use this data and save this information in my database
//        String email = Objects.requireNonNull(user.getAttribute("email")).toString();
//        String name = Objects.requireNonNull(user.getAttribute("name")).toString();
//        String photo = Objects.requireNonNull(user.getAttribute("picture")).toString();
//
//        User user1 = new User();
//        user1.setEmail(email);
//        user1.setUserName(name);
//        user1.setPassword(UUID.randomUUID().toString());
//        user1.setProfilePicture(photo);
//        user1.setUserId(UUID.randomUUID().toString());
//        user1.setProvider(Providers.GOOGLE);
//        user1.setEmailVerified(true);
//        user1.setProviderUserId(user.getName());
//        user1.setRolesList(List.of(AppConstant.ROLE_USER));


        User user3 = sign_upRepo.findByEmail(user2.getEmail()).orElse(null);
        if (user3 == null) {
            sign_upRepo.save(user2);
        }

        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }
}
