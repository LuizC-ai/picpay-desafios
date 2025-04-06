package main.java.com.picpaysimplificado;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.picpaysimplificado.domain.model")
@EnableJpaRepositories("com.picpaysimplificado.infrastructure.repository")
public class PicpayApplication {
    public static void main(String[] args) {
        SpringApplication.run(PicpayApplication.class, args);
    }
}
