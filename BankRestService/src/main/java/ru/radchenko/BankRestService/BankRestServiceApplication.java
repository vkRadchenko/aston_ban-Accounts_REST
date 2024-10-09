package ru.radchenko.BankRestService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankRestServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(BankRestServiceApplication.class, args);
		System.out.println("My first springBoot app");
	}

}
