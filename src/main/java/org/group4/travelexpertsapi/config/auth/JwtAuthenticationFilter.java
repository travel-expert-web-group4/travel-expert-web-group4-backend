package org.group4.travelexpertsapi.config.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.group4.travelexpertsapi.service.auth.JwtService;
import org.group4.travelexpertsapi.service.auth.WebUserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final WebUserService webUserService;

    public JwtAuthenticationFilter(JwtService jwtService, WebUserService webUserService) {
        this.jwtService = jwtService;
        this.webUserService = webUserService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
//        System.out.println("authHeader: " + authHeader);

        // check if token is null OR does not start with Bearer
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // token has passed null check

//        String authToken = authHeader.substring(7);
        String authToken = authHeader.substring(7).trim();

        //extract username from token

        String username = jwtService.getUsernameFromToken(authToken);

//        System.out.println("username: " + username);

//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            // extract user details from username
//            UserDetails userDetails = webUserService.loadUserByUsername(username);
//
//            // check if token is not expired
//
//            if (jwtService.checkIfTokenExpired(authToken)) {
//                // perform basic authentication using username and password
//                UsernamePasswordAuthenticationToken authenticationToken =
//                        new UsernamePasswordAuthenticationToken(
//                                username,
//                                userDetails.getPassword(),
//                                userDetails.getAuthorities());
//                // add authenticationToken to the request
//                authenticationToken.setDetails(new WebAuthenticationDetailsSource()
//                        .buildDetails(request));
//                // put authenticationToken in the security context
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//            }
//
//        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = webUserService.loadUserByUsername(username);

            if (jwtService.checkIfTokenExpired(authToken)) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                userDetails.getPassword(),
                                userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }


        filterChain.doFilter(request, response);

    }
}
