package com.cursomc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Springboot2IonicBackendApplication implements CommandLineRunner{
	
	//@Autowired
	//S3Service s3Service;

	public static void main(String[] args) {
		SpringApplication.run(Springboot2IonicBackendApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		//s3Service.uploadFile("foto.jpg","E:\\tempFig\\104_1.jpg");
	}
}
