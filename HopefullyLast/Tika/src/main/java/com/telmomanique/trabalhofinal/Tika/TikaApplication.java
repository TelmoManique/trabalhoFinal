package com.telmomanique.trabalhofinal.Tika;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class TikaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TikaApplication.class, args);
	}

}
