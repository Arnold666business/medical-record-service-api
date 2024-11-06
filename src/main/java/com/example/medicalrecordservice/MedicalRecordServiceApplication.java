package com.example.medicalrecordservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MedicalRecordServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicalRecordServiceApplication.class, args);
	}

}
