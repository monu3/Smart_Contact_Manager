package com.scm.config;


import com.scm.services.impl.SecurityCustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final OAuthAuthenticationSuccessHandler oAuthAuthenticationSuccessHandler;


    //create user and login using database user

//    @Bean
//    public UserDetailsService userDetailsService() {


//        UserDetails user1 = User
//                .withUsername("admin")
//                .password("admin")
//                .roles("ADMIN","USER")
//                .build();
//
//        UserDetails user2 = User
//                .withUsername("user123")
//                .password("user123")
////                .roles("ADMIN","USER")
//                .build();
//
//
//
//        var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1,user2);
//        return inMemoryUserDetailsManager;

    private final SecurityCustomUserDetailsService userDetailsService;

    public SecurityConfig(OAuthAuthenticationSuccessHandler oAuthAuthenticationSuccessHandler, SecurityCustomUserDetailsService userDetailsService) {
        this.oAuthAuthenticationSuccessHandler = oAuthAuthenticationSuccessHandler;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //give special permission to the https request mapping in our application
        httpSecurity.authorizeHttpRequests((authorizeRequests) -> {
//               authorizeRequests.requestMatchers("/home","/sign_up","/about","/services").permitAll();
            authorizeRequests.requestMatchers("/user/**").authenticated();
            authorizeRequests.anyRequest().permitAll();
        });
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        //form default login
        //if anyone things want to change something in login form come here and change
        httpSecurity.formLogin(formLogin -> {
            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticate");
            formLogin.successForwardUrl("/user/dashboard");
            formLogin.failureUrl("/login?error=true");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");
        });

        //customize for form logout
        httpSecurity.logout(logout -> {
            logout.logoutUrl("/logout");
            logout.logoutSuccessUrl("/login?logout=true");
        });


        //oauth client configure
        httpSecurity.oauth2Login(oauth2Login -> {
            oauth2Login.loginPage("/login");
            oauth2Login.successHandler(oAuthAuthenticationSuccessHandler);
        });

        return httpSecurity.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
