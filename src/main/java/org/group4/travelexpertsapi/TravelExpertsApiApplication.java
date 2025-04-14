package org.group4.travelexpertsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TravelExpertsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelExpertsApiApplication.class, args);
    }

}
