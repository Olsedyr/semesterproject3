package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class Semesterproject3Application {

	public static void main(String[] args) {
		SpringApplication.run(Semesterproject3Application.class, args);
	}

	@GetMapping
	public List<String> hello3(){
		return List.of("hello world");
	}
}
