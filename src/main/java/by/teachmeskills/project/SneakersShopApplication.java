package by.teachmeskills.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SneakersShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(SneakersShopApplication.class, args);
    }
}
