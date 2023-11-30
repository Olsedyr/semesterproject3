package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Semesterproject3Application {

	public static void main(String[] args) {

		SpringApplication.run(Semesterproject3Application.class, args);}

//	@Bean
//	CommandLineRunner commandLineRunner (BatchRepository batchRepository){
//
//        return args -> {
//			Batch hej = new Batch(1,122,12);
//			batchRepository.save(hej);
//		};
//
//    }
}
