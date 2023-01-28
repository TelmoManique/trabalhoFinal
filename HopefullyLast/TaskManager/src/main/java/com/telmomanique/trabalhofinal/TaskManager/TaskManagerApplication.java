package com.telmomanique.trabalhofinal.TaskManager;

import com.telmomanique.trabalhofinal.TaskManager.models.Task;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableEurekaClient
public class TaskManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagerApplication.class, args);
	}

	//TODO DELETE
	/*
	@Bean
	public List<Task> populateClientes(){
		List<Task> taskList= new ArrayList<Task>();

		Task t1 = new Task();
		t1.setId(2);
		t1.setStatus("ongoing");
		t1.setLanguage("");
		t1.setCliente_id(3);
		t1.setHash("hudrgrgg");
		t1.setInt_date(new Timestamp(System.currentTimeMillis()));
		taskList.add(t1);
		return taskList;
	}
	*/

}
