package com.patryk.marcisz.gumtreecopy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableGlobalMethodSecurity(
//        prePostEnabled = true,
//        securedEnabled = true,
//        jsr250Enabled = true)
@SpringBootApplication
public class MainApp{

    public static void main(String[] args) {
        SpringApplication.run(MainApp.class, args);
    }

}
