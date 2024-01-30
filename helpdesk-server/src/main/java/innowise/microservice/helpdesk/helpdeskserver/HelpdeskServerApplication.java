package innowise.microservice.helpdesk.helpdeskserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class HelpdeskServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelpdeskServerApplication.class, args);
	}

}
