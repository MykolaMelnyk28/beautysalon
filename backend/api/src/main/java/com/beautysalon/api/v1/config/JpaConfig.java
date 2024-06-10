package com.beautysalon.api.v1.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "com.beautysalon.core.entities")
@EnableJpaRepositories(basePackages = "com.beautysalon.core.repository")
public class JpaConfig {
}
