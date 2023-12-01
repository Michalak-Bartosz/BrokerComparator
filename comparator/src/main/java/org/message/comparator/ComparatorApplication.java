package org.message.comparator;

import lombok.extern.slf4j.Slf4j;
import org.message.comparator.dto.security.RegisterRequestDto;
import org.message.comparator.service.security.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static org.message.comparator.entity.Role.ADMIN;

@Slf4j
@SpringBootApplication
public class ComparatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComparatorApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            AuthenticationService service
    ) {
        return args -> {
            var admin = RegisterRequestDto.builder()
                    .username("Admin")
                    .password("admin1Dashboard2Password3!")
                    .role(ADMIN)
                    .build();
            log.info("Admin token: {}", service.register(admin).getAccessToken());
        };
    }
}
