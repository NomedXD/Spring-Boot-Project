package by.teachmeskills.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class SneakersShopApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(SneakersShopApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
