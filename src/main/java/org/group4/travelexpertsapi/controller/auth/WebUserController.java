package org.group4.travelexpertsapi.controller.auth;



import org.group4.travelexpertsapi.entity.auth.WebUser;
import org.group4.travelexpertsapi.service.auth.JwtService;
import org.group4.travelexpertsapi.service.auth.WebUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/user")
public class WebUserController {

    private final JwtService jwtService;
    private final WebUserService webUserService;
    private final AuthenticationManager authenticationManager;

    public WebUserController(WebUserService webUserService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.webUserService = webUserService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    // register new user

//    @PostMapping("/register-user")
//    public ResponseEntity<Void> newUser(@RequestPart("email") String email,
//                                        @RequestPart("password") String password,
//                                        @RequestPart(value="agentEmail", required = false) String agentEmail,
//                                        @RequestPart(value = "agentPassword", required = false) String agentPassword) throws Exception {
//        // NOTE: ONCE SECURITY LAYER IS ADDED, PASSWORD WILL BE HASHED
//
////        if(agentEmail.equals(email)) {
////            throw new IOException("Agents cannot use company email. Please enter a private email");
////        }
//        if (agentEmail != null && agentEmail.equals(email)) {
//            throw new IOException("Agents cannot use company email. Please enter a private email");
//        }
//
//
//        String encodedPassword = new BCryptPasswordEncoder().encode(password);
//
//
//
////        String encodedPassword = password;
////        String encodedAgentPassword = agentPassword;
//        webUserService.createNewUser(email, encodedPassword, agentEmail, agentPassword);
//
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

@PostMapping("/register-user")
public ResponseEntity<?> newUser(
        @RequestParam("email") String email,
        @RequestParam("password") String password,
        @RequestParam("role") String role, // 👈 new
        @RequestParam(value = "agentEmail", required = false) String agentEmail,
        @RequestParam(value = "agentPassword", required = false) String agentPassword) {

    if ("agent".equalsIgnoreCase(role) && agentEmail != null && agentEmail.equals(email)) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Agents cannot use their company email as their login email.");
    }

    try {
        String encodedPassword = new BCryptPasswordEncoder().encode(password);

        webUserService.createNewUser(email, encodedPassword, agentEmail, agentPassword, role); // 👈 updated

        return ResponseEntity.status(HttpStatus.CREATED).build(); // 201 Created
    } catch (RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ex.getMessage()); // 409 Conflict
    }
}



    @GetMapping("/{user_id}")
    public WebUser getUserById(@PathVariable Integer user_id) {
        return webUserService.getWebUserById(user_id);
    }

    @GetMapping("/email")
    public WebUser getEmail(@RequestPart("email") String email) {
        return webUserService.getWebUserByEmail(email);
    }

    @GetMapping("/check-user")
    public boolean checkUser(@RequestPart("email") String email) {
        return webUserService.checkIfCustomerExist(email);
    }

//    @GetMapping("/login")
//    public String loginUser(@RequestPart("email") String email, @RequestPart("password") String password) throws BadCredentialsException {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(email, password));
//        if (authentication.isAuthenticated()) {
//            return jwtService.generateToken(webUserService.loadUserByUsername(email));
//        } else {
//            throw new BadCredentialsException("Incorrect email or password. Authentication failed.");
//        }
//
//    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam("email") String email,
                                       @RequestParam("password") String password) {
        try {
            // Authenticate user with credentials
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));

            if (authentication.isAuthenticated()) {
                // Generate JWT Token
                String jwtToken = jwtService.generateToken(webUserService.loadUserByUsername(email));

                // Return token in response body
                return ResponseEntity.ok().body("Bearer " + jwtToken); // Send token as response

            } else {
                throw new BadCredentialsException("Incorrect email or password. Authentication failed.");
            }
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials!");
        }
    }



}
