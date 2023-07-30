package com.test.absensi;

import com.test.absensi.auth.AuthService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AbsensiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AbsensiApplication.class, args);
    }

}
