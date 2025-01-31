package com.example.socialmediaapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SocialmediaappApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SocialmediaappApplication.class, args);
		SocialMediaApp app = context.getBean(SocialMediaApp.class);
        app.run();
	}
}