package com.beautysalon.ui.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "com.beautysalon.domain.entities")
@EnableJpaRepositories(basePackages = "com.beautysalon.domain.repository")
public class JpaConfig {
}
