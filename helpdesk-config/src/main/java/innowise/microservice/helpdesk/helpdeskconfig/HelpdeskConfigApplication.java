package innowise.microservice.helpdesk.helpdeskconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class HelpdeskConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelpdeskConfigApplication.class, args);
	}

}
