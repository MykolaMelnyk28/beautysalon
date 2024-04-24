package com.beautysalon.ui;

import com.beautysalon.domain.entities.UserEntity;
import com.beautysalon.domain.entities.UserRole;
import com.beautysalon.domain.repository.UserRepository;
import com.beautysalon.domain.services.ApiProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@EnableConfigurationProperties({ApiProperties.class})
public class BackendUiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendUiApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return (x) -> {
            userRepository.findByUsername("root").ifPresentOrElse((u) -> {}, () -> {
                final UserEntity user = new UserEntity();
                final Set<UserRole> roles = new HashSet<>();
                user.setUsername("root");
                user.setPassword("$2a$10$E9UugF2VPO7emXBhjgqU2uXiKTZbNFaIdTWfG3NPgFCG2rTfLiDBi");
                roles.add(UserRole.ROLE_ROOT_ADMIN);
                user.setAuthorities(roles);
                userRepository.save(user);
            });
        };
    }

}
