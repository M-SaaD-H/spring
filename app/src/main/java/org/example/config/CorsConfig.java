package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

    CorsConfiguration cors = new CorsConfiguration();

    cors.setAllowCredentials(true);
    cors.addAllowedOrigin("http://localhost:3000");
    cors.addAllowedHeader("*");
    cors.addAllowedMethod("*");

    source.registerCorsConfiguration("/**", cors);
    
    return new CorsFilter(source);
  }
}