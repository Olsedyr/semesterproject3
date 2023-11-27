package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.sql.Time;
import java.time.LocalDateTime;

@SpringBootApplication
public class Semesterproject3Application {

	public static void main(String[] args) {

		SpringApplication.run(Semesterproject3Application.class, args);
	}
}
