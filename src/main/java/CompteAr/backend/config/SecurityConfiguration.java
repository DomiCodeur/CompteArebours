package CompteAr.backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration Spring Security.
 *
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http)  throws Exception {
    http
        .cors()  // Active la gestion CORS par Spring Security
        .and()
        .csrf()
        .disable()
        .authorizeHttpRequests()
        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() //permettre requête preflight
        .requestMatchers(HttpMethod.POST, "/users/**","/dates/**", "/auth/**","/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**")
        .permitAll()
        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "https://zzztracker.vercel.app/**" )
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
  
}
