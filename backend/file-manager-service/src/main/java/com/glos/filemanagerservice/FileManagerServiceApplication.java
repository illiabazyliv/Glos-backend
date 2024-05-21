package com.glos.filemanagerservice;

import com.glos.filemanagerservice.pathUtils.Path;
import com.glos.filemanagerservice.pathUtils.PathParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class FileManagerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileManagerServiceApplication.class, args);
	}

}
