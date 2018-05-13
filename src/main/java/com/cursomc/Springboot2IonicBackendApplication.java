package com.cursomc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cursomc.services.S3Service;

@SpringBootApplication
public class Springboot2IonicBackendApplication implements CommandLineRunner{
	
	@Autowired
	S3Service s3Service;

	public static void main(String[] args) {
		SpringApplication.run(Springboot2IonicBackendApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		s3Service.uploadFile("foto.jpg","E:\\tempFig\\104_1.jpg");
	}
}
