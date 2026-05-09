package org.example.psychology_center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication

public class PsychologyCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(PsychologyCenterApplication.class, args);
        System.out.println("APP_PASSWORD=" + System.getenv("APP_PASSWORD"));
        System.out.println("USERNAME="+System.getenv("MAIL_USERNAME"));
    }

}
