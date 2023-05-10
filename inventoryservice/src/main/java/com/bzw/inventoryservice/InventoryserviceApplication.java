package com.bzw.inventoryservice;

import com.bzw.inventoryservice.model.Inventory;
import com.bzw.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class InventoryserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryserviceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository){
		return args -> {
			Inventory i1=new Inventory();
			i1.setSkucode("senheizer headphones");
			i1.setQuantity(2);

			Inventory i2=new Inventory();
			i2.setSkucode("one+");
			i2.setQuantity(100);

			inventoryRepository.save(i1);
			inventoryRepository.save(i2);
		};
	}
}
