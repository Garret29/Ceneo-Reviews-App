package pl.krakow.uek.ceneoReviewsApp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.krakow.uek.ceneoReviewsApp.model.Product;
import pl.krakow.uek.ceneoReviewsApp.model.Review;
import pl.krakow.uek.ceneoReviewsApp.repository.ProductRepository;

import java.util.LinkedList;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

    @Bean
    public CommandLineRunner commandLineRunner(ProductRepository productRepository) {
        return args -> {

        };
    }
}
