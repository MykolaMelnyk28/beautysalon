package com.beautysalon.ui;

import com.beautysalon.core.CoreConfig;
import com.beautysalon.core.entities.UserEntity;
import com.beautysalon.core.entities.UserRole;
import com.beautysalon.core.repository.UserRepository;
import com.beautysalon.core.services.ApiProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@Import(CoreConfig.class)
public class UiApplication {

	public static void main(String[] args) {
		SpringApplication.run(UiApplication.class, args);
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
