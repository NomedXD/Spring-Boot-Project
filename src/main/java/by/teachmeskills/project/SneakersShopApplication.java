package by.teachmeskills.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "by")
public class SneakersShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(SneakersShopApplication.class, args);
    }
}
