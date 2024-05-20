package com.beautysalon.ui.config;

import com.beautysalon.domain.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            UserService userService
    ) throws Exception {
        http
                .authorizeHttpRequests(x -> {
                    x.requestMatchers("*", "/panel/*").hasAnyRole("SYS_ADMIN", "ROOT_ADMIN");

                    x.requestMatchers("/panel/services").hasAnyRole("SYS_ADMIN", "ROOT_ADMIN");
                    x.requestMatchers("/panel/images").hasAnyRole("SYS_ADMIN", "ROOT_ADMIN");
                    x.requestMatchers("/panel/employees").hasAnyRole("SYS_ADMIN", "ROOT_ADMIN");
                    x.requestMatchers("/panel/appointments").hasAnyRole("SYS_ADMIN", "ROOT_ADMIN");
                    x.requestMatchers("/panel/users").hasAnyRole("SYS_ADMIN", "ROOT_ADMIN");
                    x.requestMatchers("/panel/schedules").hasAnyRole("SYS_ADMIN", "ROOT_ADMIN");

                    x.requestMatchers("/panel/services/**").hasAnyRole("SERVICE_EDITOR", "ROOT_ADMIN");
                    x.requestMatchers("/panel/images/**").hasAnyRole("IMAGE_EDITOR", "ROOT_ADMIN");
                    x.requestMatchers("/panel/employees/**").hasAnyRole("EMPLOYEE_EDITOR", "ROOT_ADMIN");
                    x.requestMatchers("/panel/appointments/**").hasAnyRole("APPOINTMENT_EDITOR", "ROOT_ADMIN");
                    x.requestMatchers("/panel/users/**").hasAnyRole("ACCOUNT_EDITOR", "ROOT_ADMIN");
                    x.requestMatchers("/panel/schedules/**").hasAnyRole("SCHEDULES_EDITOR", "ROOT_ADMIN");

                    x.requestMatchers("/css/**", "/js/**").permitAll();
                    x.requestMatchers("/login", "/panel/login").permitAll();
                })
                .userDetailsService(userService)
                .formLogin(x -> {
                    x.defaultSuccessUrl("/panel", true);
                })
                .logout(x -> {
                    x.permitAll();
                });
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.builder()
//                .username("root")
//                .password("$2a$10$4yMyrBLZjgFNr/CV0/DfdOMvkY8l352H/fzPejhGZKbRNXWc.fAFK")
//                .roles("ROOT_ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }

}
