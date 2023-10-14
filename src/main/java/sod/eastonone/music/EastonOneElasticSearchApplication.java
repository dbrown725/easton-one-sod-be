package sod.eastonone.music;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling

public class EastonOneElasticSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(EastonOneElasticSearchApplication.class, args);
	}

}