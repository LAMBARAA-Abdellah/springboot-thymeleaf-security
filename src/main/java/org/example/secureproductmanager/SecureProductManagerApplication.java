package org.example.secureproductmanager;

import org.example.secureproductmanager.entities.Product;
import org.example.secureproductmanager.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class SecureProductManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecureProductManagerApplication.class, args);
    }

    @Bean
    public CommandLineRunner start(ProductRepository productRepository) {
        return args -> {
            productRepository.save(Product.builder()
                    .name("computer")
                    .price(5444)
                    .quantity(12)
                    .build());

            productRepository.save(Product.builder()
                    .name("printer")
                    .price(454)
                    .quantity(545)
                    .build());

            productRepository.save(Product.builder()
                    .name("smartphone")
                    .price(10)
                    .quantity(12)
                    .build());
            System.out.println("📦 Liste des produits enregistrés :");
            productRepository.findAll().forEach(product -> System.out.println(product.toString()));
        };
    }



}
