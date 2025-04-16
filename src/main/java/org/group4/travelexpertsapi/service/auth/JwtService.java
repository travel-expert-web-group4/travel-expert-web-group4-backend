package org.group4.travelexpertsapi.service.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.group4.travelexpertsapi.entity.Agent;
import org.group4.travelexpertsapi.entity.auth.WebUser;
import org.group4.travelexpertsapi.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

//    public String generateToken(UserDetails userDetails) {
//        Map<String, String> claims = new HashMap<>();
//        claims.put("webuser", "travelexpertsapp");
//
//        return Jwts.builder()
//                .claims(claims)
//                .subject(userDetails.getUsername())
//                .issuedAt(Date.from(Instant.now()))
//                .expiration(Date.from(Instant.now().plusSeconds(3600))) // 1 hour
//                .signWith(getSigningKey())
//                .compact();
//    }

    @Autowired
    private WebUserService webUserService;

//    public String generateToken(UserDetails userDetails) {
//        // Lookup WebUser by email (username)
//
//
//        WebUser webUser = webUserService.getWebUserByEmail(userDetails.getUsername());
//
//        if (webUser == null || webUser.getCustomer() == null) {
//            throw new RuntimeException("User or associated customer not found.");
//        }
//
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("webuser", "travelexpertsapp");
//        claims.put("sub", webUser.getEmail());  // same as userDetails.getUsername()
//        claims.put("role", "ROLE_" + webUser.getRole().toUpperCase());
//        claims.put("id", webUser.getCustomer().getId());
//
//        return Jwts.builder()
//                .claims(claims)
//                .subject(webUser.getEmail())
//                .issuedAt(Date.from(Instant.now()))
//                .expiration(Date.from(Instant.now().plusSeconds(3600)))  // 1 hour expiry
//                .signWith(getSigningKey())
//                .compact();
//    }



    @Autowired
    private UserRepo userRepo;

    public String generateToken(org.springframework.security.core.userdetails.UserDetails userDetails) {
        WebUser webUser = webUserService.getWebUserByEmail(userDetails.getUsername());

        if (webUser == null) {
            throw new RuntimeException("WebUser not found.");
        }
        // ✅ Add these logs here
        System.out.println("💡 WebUser role: " + webUser.getRole());
        System.out.println("💡 WebUser customerType: " +
                (webUser.getCustomerType() != null ? webUser.getCustomerType().getName() : "null"));


        Map<String, Object> claims = new HashMap<>();
        claims.put("webuser", "travelexpertsapp");
        claims.put("sub", webUser.getEmail());
        claims.put("role", "ROLE_" + webUser.getRole().toUpperCase());
        claims.put("webUserId", webUser.getId());

        // ✅ Always include customerType if exists
        if (webUser.getCustomerType() != null) {
            claims.put("customerType", webUser.getCustomerType().getName());
        }

        if ("CUSTOMER".equalsIgnoreCase(webUser.getRole())) {
            if (webUser.getCustomer() == null) {
                throw new RuntimeException("Customer data missing for this user.");
            }

            claims.put("customerId", webUser.getCustomer().getId());
            claims.put("points", webUser.getPoints());
        }
        else if ("AGENT".equalsIgnoreCase(webUser.getRole())) {
            org.group4.travelexpertsapi.entity.User agentUser =
                    userRepo.findByEmail(webUser.getEmail()).orElse(null);

            if (agentUser != null && agentUser.getAgentid() != null) {
                Agent agent = agentUser.getAgentid();
                claims.put("agentId", agent.getId());
                claims.put("agentEmail", agent.getAgtemail());
                claims.put("agentName", agent.getAgtfirstname() + " " + agent.getAgtlastname());
                claims.put("position", agent.getAgtposition());
            }
        }

        return Jwts.builder()
                .claims(claims)
                .subject(webUser.getEmail())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusSeconds(3600))) // 1 hour
                .signWith(getSigningKey())
                .compact();
    }

    public String generateAgentToken(UserDetails userDetails) {

        // using Hash-map to store claims

        Map<String, String> claim = new HashMap<>();
        claim.put("webuser", "travelexpertsapp");

        return Jwts.builder()
                .claims(claim)
                .setSubject(userDetails.getUsername())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusSeconds(3600))) // 1 hour
                .signWith(getSigningKey())
                .compact();

    }


    private Claims getPayload(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token.trim())
                .getPayload();
    }

    public String getUsernameFromToken(String token) {
        return getPayload(token).getSubject();
    }

    public boolean checkIfTokenExpired(String token) {
        return getPayload(token.trim()).getExpiration().after(new Date());
    }



}
