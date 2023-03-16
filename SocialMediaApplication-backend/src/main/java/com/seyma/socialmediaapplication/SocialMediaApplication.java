package com.seyma.socialmediaapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SocialMediaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialMediaApplication.class, args);
    }

}
