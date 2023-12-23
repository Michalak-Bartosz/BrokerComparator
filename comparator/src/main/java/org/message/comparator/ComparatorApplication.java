package org.message.comparator;

import lombok.extern.slf4j.Slf4j;
import org.message.comparator.dto.security.RegisterRequestDto;
import org.message.comparator.entity.auth.DashboardUser;
import org.message.comparator.repository.dashboard.DashboardUserRepository;
import org.message.comparator.service.security.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.message.comparator.entity.auth.Role.ADMIN;

@Slf4j
@SpringBootApplication
public class ComparatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComparatorApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            AuthenticationService service,
            DashboardUserRepository dashboardUserRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            Optional<DashboardUser> adminUser = dashboardUserRepository.findByUsername("Admin");
            if (adminUser.isPresent()) {
                return;
            }
            var admin = RegisterRequestDto.builder()
                    .username("Admin")
                    .password("admin1Dashboard2Password3!")
                    .role(ADMIN)
                    .build();
            final String adminAccessToken = service.register(admin).getAccessToken();

            var test = RegisterRequestDto.builder()
                    .username("Test")
                    .password("123")
                    .role(ADMIN)
                    .build();
            final String testAccessToken = service.register(test).getAccessToken();

            log.info("Admin token: {}", adminAccessToken);
            log.info("Test token: {}", testAccessToken);
        };
    }
}
