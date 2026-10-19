package br.com.consultafipe;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport
public class ConsultafipeApplication{

	public static void main(String[] args) {
		SpringApplication.run(ConsultafipeApplication.class, args);
	}
}
