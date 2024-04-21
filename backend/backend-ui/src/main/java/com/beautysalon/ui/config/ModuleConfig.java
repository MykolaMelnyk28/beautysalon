package com.beautysalon.ui.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = {
        "com.beautysalon.domain",
        "com.beautysalon.api"
})
public class ModuleConfig {

}
