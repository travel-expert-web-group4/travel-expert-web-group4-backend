package org.group4.travelexpertsapi.service.auth;


import org.group4.travelexpertsapi.entity.Customer;
import org.group4.travelexpertsapi.entity.CustomerType;
import org.group4.travelexpertsapi.entity.auth.WebUser;
import org.group4.travelexpertsapi.repository.*;
import org.group4.travelexpertsapi.repository.auth.WebUserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class WebUserService implements UserDetailsService {
    private UserRepo userRepo;
    WebUserRepo webUserRepo;
    CustomerRepo customerRepo;
    CustomerTypeRepo customerTypeRepo;
    AgentRepository agentRepo;

    public WebUserService(WebUserRepo webUserRepo, CustomerRepo customerRepo, CustomerTypeRepo customerTypeRepo, AgentRepository agentRepo, UserRepo userRepo) {
        this.webUserRepo = webUserRepo;
        this.customerRepo = customerRepo;
        this.customerTypeRepo = customerTypeRepo;
        this.agentRepo = agentRepo;
        this.userRepo = userRepo;
    }

    @Value("${customer.image.upload-dir}")
    private String imageUploadDir;

    // create new web user
//    public void createNewUser(String email, String password, String agentEmail, String agentPassword) {
//        WebUser webUser=new WebUser(email, password);
//        webUser.setPoints((0));
//        Customer customer = customerRepo.findByCustemail(email);
//
//        if (customer != null) {
//            webUser.setCustomer(customer);
//            Double points =  getPointsBalance(customer.getId());
//            customerTypeSetter(webUser, points);
//            webUser.setPoints(points.intValue());
//        }
//
//        webUser.setRole("CUSTOMER");
//
//        // check if agent
//        webUser.setAgent(false);
//
//
//        if (agentEmail != null) {
//
////            Agent agent = agentRepo.findByAgtemail(agentEmail);
//            org.group4.travelexpertsapi.entity.User agent = userRepo.findByEmail(agentEmail).orElse(null);
//            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//
//            if (agent != null){
//                Boolean matches = encoder.matches(agentPassword, agent.getPasswordHash());
//                if (matches) {
//                    webUser.setAgent(true);
//                }
//
//            }
//        }
//
//        // CHANGE TO HASHED PASSWORD WHEN SECURITY LAYER ADDED
//        webUser.setPassword(password);
//
//        webUserRepo.save(webUser);
//    }

public void createNewUser(String email, String password, String agentEmail, String agentPassword, String role) {
    WebUser webUser = new WebUser(email, password);
    webUser.setPoints(0); // initialize default points

    if ("CUSTOMER".equalsIgnoreCase(role)) {
        Customer customer = customerRepo.findByCustemail(email);
        if (customer == null) {
            throw new RuntimeException("Customer not found for email: " + email);
        }

        if (webUserRepo.findByCustomer(customer) != null) {
            throw new RuntimeException("This customer is already registered.");
        }

        webUser.setCustomer(customer);
        webUser.setRole("CUSTOMER");
        webUser.setAgent(false);

        Double points = getPointsBalance(customer.getId());
        if (points == null) points = 0.0;
        customerTypeSetter(webUser, points);
        webUser.setPoints(points.intValue());

    } else if ("AGENT".equalsIgnoreCase(role)) {
        if (agentEmail == null || agentPassword == null) {
            throw new RuntimeException("Agent email and password are required.");
        }

        org.group4.travelexpertsapi.entity.User agent = userRepo.findByEmail(agentEmail).orElse(null);

        if (agent == null) {
            throw new RuntimeException("Agent not found.");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean matches = encoder.matches(agentPassword, agent.getPasswordHash());

        if (!matches) {
            throw new RuntimeException("Agent password is incorrect.");
        }

        // ✅ Agent confirmed — create as AGENT user
        webUser.setRole("AGENT");
        webUser.setAgent(true);

        // Optional: You could link to agent object if you want, or track it differently.
    } else {
        throw new RuntimeException("Invalid role provided. Must be CUSTOMER or AGENT.");
    }

    webUserRepo.save(webUser);
}


    public void customerTypeSetter(WebUser webUser, double points) {

            if (points >= 5000 &&  points < 20000) {
                webUser.setCustomerType(customerTypeRepo.findByNameContainsIgnoreCase("bronze"));
            } else if  (points >= 20000) {
                webUser.setCustomerType(customerTypeRepo.findByNameContainsIgnoreCase("platinum"));

            } else {
                webUser.setCustomerType(customerTypeRepo.findByNameContainsIgnoreCase("guest"));

            }

    }

    public Double getPointsBalance(Integer customerid) {
        Double balance = customerRepo.getTotalPrice(customerid);
        return balance;
    }

    // find web-user by email
    public WebUser getWebUserByEmail(String email) {
//        Customer customer = customerRepo.findByCustemail(email);
//        WebUser webUser = webUserRepo.findByCustomer(customer);
//        return webUser;
        Optional<WebUser> webUser = webUserRepo.findByEmail(email);
        return webUser.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

    }

    public WebUser getByEmail(String email) {
        return webUserRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    // find web-user by id
    public WebUser getWebUserById(Integer id) {
        WebUser webUser =  webUserRepo.findById(id).orElse(null);
        return webUser;
    }

    // Return customer type

    public CustomerType getCustomerType(Integer customerid) {
        WebUser webUser = webUserRepo.findByCustomer(customerRepo.findById(customerid).orElse(null));
        CustomerType typeOfWebUser = webUser.getCustomerType();
        return typeOfWebUser;

    }

    // Profile Picture manager

    public String uploadPicture(Integer customerid, MultipartFile image) {
        Customer customer = customerRepo.findById(customerid).orElse(null);
        String filePath = customer.getCustfirstname() + "_" + customer.getCustlastname() + ".jpg";
        try {
            File fileDir = new File(imageUploadDir);
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }
            //String fileName = image.getOriginalFilename();
            Path imagePath = Paths.get(imageUploadDir, filePath);
            Files.write(imagePath, image.getBytes());


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String ImageUrl = "/images/customers/" + filePath;

        return ImageUrl;
    }

    // save Profile Picture

//    public void savePicture(Integer customerid, MultipartFile image) {
//        Customer customer = customerRepo.findById(customerid).orElse(null);
//        WebUser webUser = webUserRepo.findByCustomer(customer);
//        String ImageUrl = uploadPicture(customerid, image);
//        webUser.setProfileImage(ImageUrl);
//        webUserRepo.save(webUser);
//    }

    public String savePicture(Integer customerid, MultipartFile image) {
        Customer customer = customerRepo.findById(customerid).orElse(null);
        WebUser webUser = webUserRepo.findByCustomer(customer);
        String imageUrl = uploadPicture(customerid, image);
        webUser.setProfileImage(imageUrl);
        webUserRepo.save(webUser);
        return imageUrl;
    }





    // Update Profile Picture of Customer
    public void updatePicture(Integer customerid, MultipartFile image) {
        Customer existingCustomer = customerRepo.findById(customerid).orElse(null);
        WebUser webUser = webUserRepo.findByCustomer(existingCustomer);

        if (webUser != null) {
            if (image != null && !image.isEmpty()) {
                String imagePath = uploadPicture(customerid, image);
                webUser.setProfileImage(imagePath);

            }
        }

        webUserRepo.save(webUser);
    }

    // Delete Profile Picture of Customer
    public void deletePicture(Integer customerid) {
        Customer customer = customerRepo.findById(customerid).orElse(null);
        WebUser webUser = webUserRepo.findByCustomer(customer);
        if (webUser != null) {
            String filePath = customer.getCustfirstname() + "_" + customer.getCustlastname() + ".jpg";
            Path imagePath = Paths.get(imageUploadDir, filePath);
            try {
                Files.delete(imagePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            webUser.setProfileImage(null);

        }

        webUserRepo.save(webUser);
    }

    // check if customer already in database

    public Boolean checkIfCustomerExist(String user_email) {
        Customer customer = customerRepo.findByCustemailContainingIgnoreCase(user_email);
        if (customer != null) {
            return true;
        } else  return false;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {


        Optional< org.group4.travelexpertsapi.entity.User> mobileUser = userRepo.findByEmail(email);
        Optional<WebUser> webUser = webUserRepo.findByEmail(email);

        // if user is in the webUsers (i.e. customers) table
        if (webUser.isPresent()) {
            WebUser user = webUser.get();
            return User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .roles(user.getRole())
                    .build();

        // if user is in the users(i.e. agents) table
        } else if(mobileUser.isPresent()) {
            org.group4.travelexpertsapi.entity.User  user = mobileUser.get();
            return User.builder()
                    .username(user.getEmail())
                    .password(user.getPasswordHash())
                    .roles(user.getRole().toUpperCase())
                    .build();
        }
        return null;
    }


//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Optional<WebUser> webUser = webUserRepo.findByEmail(email);
//        if (webUser.isPresent()) {
//            WebUser user = webUser.get();
//            return User.builder()
//                    .username(user.getEmail())
//                    .password(user.getPassword())
//                    .roles(getRoles(user))
//                    .build();
//        }
//
//        return null;
//    }
//
//     method to split roles
//    private String[] getRoles(WebUser webUser) {
//        if(webUser.getRole() == null)
//            return new String[] {"CUSTOMER"};
//        return webUser.getRole().split(",");
//    }



}
