package com.glos.filemanagerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FileManagerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileManagerServiceApplication.class, args);
	}

}
