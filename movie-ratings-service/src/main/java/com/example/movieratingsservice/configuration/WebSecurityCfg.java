package com.example.movieratingsservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class WebSecurityCfg {

    private AuthenticationMenager authenticationMenager;
    private SecurityContextRepository securityContextRepository;

    public WebSecurityCfg(AuthenticationMenager authenticationMenager, 
                          SecurityContextRepository securityContextRepository) {
        this.authenticationMenager = authenticationMenager;
        this.securityContextRepository = securityContextRepository;
    }
    
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http.exceptionHandling().authenticationEntryPoint((swe, e) -> {
            return Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED));
        }).accessDeniedHandler((swe, e) -> Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN)))
        .and().csrf().disable()
        .authenticationManager(authenticationMenager).securityContextRepository(securityContextRepository)
        .authorizeExchange().pathMatchers(HttpMethod.POST, "/rate").authenticated().and()
        .authorizeExchange().anyExchange().permitAll().and().build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

