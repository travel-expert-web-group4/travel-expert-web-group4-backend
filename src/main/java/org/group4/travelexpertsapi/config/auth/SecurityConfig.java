package org.group4.travelexpertsapi.config.auth;

import org.group4.travelexpertsapi.service.auth.WebUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private WebUserService webUserService;

    public SecurityConfig(WebUserService webUserService) {
        this.webUserService = webUserService;
    }

    // auth config for spring security
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return webUserService;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // grant authorization to users
        httpSecurity.authorizeHttpRequests(
                configurer ->
                        configurer

                                .requestMatchers(HttpMethod.GET, "/api/user/check-user").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/user/register-user").permitAll()
                                // requests by type

                                // GET

                                // permitAll
                                .requestMatchers(HttpMethod.GET,
                                        "/api/agencies",
                                        "/api/agencies/**",
                                        "/api/agents",
                                        "/api/agents/**",
                                        "/api/package",
                                        "/api/package/**",
                                        "/api/review/package**",
                                        "/api/review/**",
                                        "/api/product/**",
                                        "/api/supplier/**"
                                ).permitAll()

                                // only Customers
                                .requestMatchers(HttpMethod.GET,
                                        "/api/booking/customer/**",
                                        "/api/booking/**",
                                        "/api/chat/history/*/*",
                                        "/api/chat/interactions",
                                        "/api/chat/contacts",
                                        "/api/customer/**"

                                ).hasRole("CUSTOMER")

                                // POST

                                // only Customers
                                .requestMatchers(HttpMethod.POST,
                                        "/api/booking/new/**",
                                        "/api/customer/new/**",
                                        "/api/customer/*/profile-picture",
                                        "/api/review/post"
                                ).hasRole("CUSTOMER")

                                // PUT

                                // only Customers
                                .requestMatchers(HttpMethod.PUT,
                                        "/api/booking/**",
                                        "/api/customer/**"

                                ).hasRole("CUSTOMER")

                                // DELETE

                                // only Customers
                                .requestMatchers(HttpMethod.DELETE,
                                        "/api/booking/**",
                                        "/api/customer/**"
                                ).hasRole("CUSTOMER")



                                // CHAT ?? TK
//                                .requestMatchers("/chat.private").hasRole("CUSTOMER")

                                // AGENTS ACCESS API
//


                                .anyRequest().authenticated()
        );

        // basic auth
        httpSecurity.httpBasic(Customizer.withDefaults());

        // disable csrf
        httpSecurity.csrf(csrf -> csrf.disable());

        // build
        return httpSecurity.build();

    }
}
