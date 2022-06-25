package admin.adminsiteserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class AdminSiteServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminSiteServerApplication.class, args);
	}

}
