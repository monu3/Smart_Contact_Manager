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

        DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
//        log.info(user.getName());
//        user.getAttributes().forEach((key, value) ->
//                log.info(key + "=" + value)
//        );
//        log.info(user.getAuthorities().toString());

        //use this data and save this information in my database
        String email = Objects.requireNonNull(user.getAttribute("email")).toString();
        String name = Objects.requireNonNull(user.getAttribute("name")).toString();
        String photo = Objects.requireNonNull(user.getAttribute("picture")).toString();

        User user1 = new User();
        user1.setEmail(email);
        user1.setUserName(name);
        user1.setPassword(UUID.randomUUID().toString());
        user1.setProfilePicture(photo);
        user1.setUserId(UUID.randomUUID().toString());
        user1.setProvider(Providers.GOOGLE);
        user1.setEmailVerified(true);
        user1.setProviderUserId(user.getName());
        user1.setRolesList(List.of(AppConstant.ROLE_USER));


        User user2 = sign_upRepo.findByEmail(email).orElse(null);
        if (user2 == null) {
            sign_upRepo.save(user1);
        }

        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }
}
