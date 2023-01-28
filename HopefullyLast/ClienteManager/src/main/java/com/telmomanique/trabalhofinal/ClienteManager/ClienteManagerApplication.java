package com.telmomanique.trabalhofinal.ClienteManager;

import com.telmomanique.trabalhofinal.ClienteManager.models.Cliente;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableEurekaClient
public class ClienteManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClienteManagerApplication.class, args);
	}

}
