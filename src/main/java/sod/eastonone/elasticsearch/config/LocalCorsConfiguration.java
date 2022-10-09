package sod.eastonone.elasticsearch.config;

import java.util.Arrays;
import java.util.List;

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
	  System.out.println("************************************ in LocalCorsConfiguration corsFilter()");
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    final CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    //config.addAllowedOrigin("http://localhost:3000");
    config.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://10.0.0.101:3000"));
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");
    source.registerCorsConfiguration("/graphql/**", config);
    return new CorsFilter(source);
  }
}
