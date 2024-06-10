package com.beautysalon.core;

import com.beautysalon.core.services.ApiProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.beautysalon.core"})
@EnableConfigurationProperties({ApiProperties.class})
public class CoreConfig {

}