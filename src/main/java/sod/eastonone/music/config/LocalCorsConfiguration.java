package sod.eastonone.music.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@Profile("local")
public class LocalCorsConfiguration {

  @Bean
  public CorsFilter corsFilter() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    final CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://10.0.0.101:3000", "http://localhost:8000", "http://10.0.0.101:8000"));
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");

    Map<String, CorsConfiguration> corsConfigurationMap = new HashMap<String, CorsConfiguration> ();
    corsConfigurationMap.put("/graphql/**", config);
    corsConfigurationMap.put("/api/**", config);
    source.setCorsConfigurations(corsConfigurationMap);
    return new CorsFilter(source);
  }
}
