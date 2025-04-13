package org.group4.travelexpertsapi.config.auth;

import org.group4.travelexpertsapi.service.auth.WebUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private WebUserService webUserService;


    private JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(WebUserService webUserService, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.webUserService = webUserService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
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
                                .requestMatchers(HttpMethod.GET, "/api/user/login").permitAll()
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
                                        "/api/customer/**",
                                        "/api/payment-success",
                                        "/api/stripe/payment-cancel"

                                ).hasRole("CUSTOMER")

                                // POST

                                // only Customers
                                .requestMatchers(HttpMethod.POST,
                                        "/api/booking/new/**",
                                        "/api/customer/new/**",
                                        "/api/customer/*/profile-picture",
                                        "/api/review/post",
                                        "/api/stripe/checkout"
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



                                // CHAT ??? TK
//                                .requestMatchers("/chat.private").hasRole("CUSTOMER")

                                // AGENTS ACCESS API
//


                                .anyRequest().authenticated()
        );

        // add jwt filter
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // basic auth
        httpSecurity.httpBasic(Customizer.withDefaults());

        // disable csrf
        httpSecurity.csrf(csrf -> csrf.disable());

        // build
        return httpSecurity.build();

    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(authenticationProvider());
    }
}
