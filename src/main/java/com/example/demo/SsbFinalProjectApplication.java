
package com.example.demo;

import java.io.IOException;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SsbFinalProjectApplication implements CommandLineRunner {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(SsbFinalProjectApplication.class, args);
		openHomePage();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Starting of SSB_PROJECT ........................");

	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	private static void openHomePage() throws IOException {
		Runtime rt = Runtime.getRuntime();
		rt.exec("rundll32 url.dll,FileProtocolHandler " + "http://localhost:8080");
	}
}

