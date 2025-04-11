package org.group4.travelexpertsapi.controller.auth;


//import org.group4.travelexpertsapi.entity.Customer;
import org.group4.travelexpertsapi.entity.auth.WebUser;
import org.group4.travelexpertsapi.service.auth.WebUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class WebUserController {

    private WebUserService webUserService;

    public WebUserController(WebUserService webUserService) {
        this.webUserService = webUserService;
    }

    // register new user

    @PostMapping("/register-user")
    public ResponseEntity<Void> newUser(@RequestPart("email") String email, @RequestPart("password") String password,  @RequestPart(value="agentEmail", required = false) String agentEmail, @RequestPart(value = "agentPassword", required = false) String agentPassword) {
        // NOTE: ONCE SECURITY LAYER IS ADDED, PASSWORD WILL BE HASHED


        String encodedPassword = new BCryptPasswordEncoder().encode(password);



//        String encodedPassword = password;
//        String encodedAgentPassword = agentPassword;
        webUserService.createNewUser(email, encodedPassword, agentEmail, agentPassword);


        return new ResponseEntity<>(HttpStatus.OK);
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


}
