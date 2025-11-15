package app.vercel.ingenio_theta.trakr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TrakrApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrakrApplication.class, args);
	}

}
