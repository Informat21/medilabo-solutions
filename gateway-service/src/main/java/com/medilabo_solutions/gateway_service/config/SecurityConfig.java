package com.medilabo_solutions.gateway_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    // Création d'un utilisateur en mémoire
    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("admin")
                .password("{noop}password") // {noop} = pas d’encodage
                .roles("ADMIN")
                .build();

        return new MapReactiveUserDetailsService(user);
    }

    // Configuration de la sécurité
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        RedirectServerAuthenticationSuccessHandler successHandler =
                new RedirectServerAuthenticationSuccessHandler("/front/patients");
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable) // désactive CSRF
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/login", "/logout").permitAll() // accès libre
                        .anyExchange().authenticated() // tout le reste nécessite login
                )
                .httpBasic(Customizer.withDefaults()) // Auth Basic (Postman / navigateur)
//                .formLogin(Customizer.withDefaults()) // Formulaire login si accès depuis navigateur
                .formLogin(form -> form
                        .authenticationSuccessHandler(successHandler)
                )
                .build();
    }
}
