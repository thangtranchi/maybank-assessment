package com.mb.assessment.application.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mb.assessment.application.service.UserService;

@Configuration
public class SecurityConfig {
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider(UserService userService) {

		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();

		auth.setUserDetailsService(userService);
		auth.setPasswordEncoder(bCryptPasswordEncoder());

		return auth;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.cors().and().authorizeHttpRequests(
				configure -> configure.requestMatchers("/h2-console/**").permitAll().anyRequest().authenticated())
				.headers(headers -> headers.frameOptions().disable())
				.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"));
		;

		// for HTTP Basic authorisation
		http.httpBasic(Customizer.withDefaults());
//		 http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());
		http.csrf().ignoringRequestMatchers("/h2-console/**");
		// CSRF is not required for stateless REST APIs that use POST, PUT, DELETE,
		// PATCh
		http.csrf(csrf -> csrf.disable());

		return http.build();
	}
	@Bean
    public WebMvcConfigurer corsConfigurer()
    {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:4200");
            }
        };
    }
	

}
