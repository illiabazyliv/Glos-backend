package com.glos.filemanagerservice;

import com.glos.filemanagerservice.entities.User;
import com.glos.filemanagerservice.utils.MapUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Map;

@EnableFeignClients
@SpringBootApplication
public class FileManagerServiceApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(FileManagerServiceApplication.class, args);
	}

}
