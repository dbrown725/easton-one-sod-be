package sod.eastonone.music.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local")
public class LocalCorsConfiguration {

	// Keeping as an old reference, see new Cors configuration in SecurityConfig.java
//  @Bean
//  public CorsFilter corsFilter() {
//    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//    final CorsConfiguration config = new CorsConfiguration();
//    config.setAllowCredentials(true);
//    config.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://10.0.0.101:3000", "http://localhost:8000", "http://10.0.0.101:8000"));
//    config.addAllowedHeader("*");
//    config.addAllowedMethod("*");
//
//    Map<String, CorsConfiguration> corsConfigurationMap = new HashMap<String, CorsConfiguration> ();
//    corsConfigurationMap.put("/graphql/**", config);
//    corsConfigurationMap.put("/api/**", config);
//    source.setCorsConfigurations(corsConfigurationMap);
//    return new CorsFilter(source);
//  }
}
