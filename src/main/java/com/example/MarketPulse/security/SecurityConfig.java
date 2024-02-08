package com.example.MarketPulse.security;

import com.example.MarketPulse.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public SecurityConfig(JwtService service, UserRepository userRepos) {
        this.jwtService = service;
        this.userRepository = userRepos;
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http, PasswordEncoder encoder, UserDetailsService udService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(udService)
                .passwordEncoder(encoder)
                .and()
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService(this.userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authz -> authz
//                                .requestMatchers(HttpMethod.POST, "/users").permitAll()
//                                .requestMatchers(HttpMethod.POST, "/auth").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/roles").permitAll()
////                        .requestMatchers(HttpMethod.DELETE, "/users/**").hasAuthority("ROLE_SELLER")
//                                .requestMatchers(HttpMethod.DELETE, "/users/**").hasAuthority("SELLER")
////                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(Customizer.withDefaults())
//                .csrf(AbstractHttpConfigurer::disable)
//                .cors(Customizer.withDefaults())
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                );
//
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CSRF bescherming uitschakelen voor REST APIs
                .csrf(csrf -> csrf.disable())
                // CORS configuratie inschakelen
                .cors(cors -> cors.and())
                // Stateless sessiebeheer voor REST APIs
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Autorisatieregels
                .authorizeHttpRequests(authz -> authz

                        // Specifieke pad- en methodecombinaties
                                .requestMatchers(HttpMethod.POST, "/products/{productId}/uploadImage").hasAuthority("SELLER")
                        .requestMatchers(HttpMethod.POST, "/users").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth").permitAll()
                                .requestMatchers(HttpMethod.POST, "/roles").permitAll()
                                .requestMatchers(HttpMethod.GET, "/products/downloadFromDB/**").permitAll()

                                 // toegang tot users
                                 .requestMatchers(HttpMethod.DELETE, "/users/**").hasAnyAuthority("ADMIN", "SELLER")
                                 .requestMatchers(HttpMethod.DELETE, "/users/**").hasAuthority("ADMIN")
                                 .requestMatchers(HttpMethod.GET, "/users").hasAuthority("ADMIN")
                                // toegangscontrole products
                                .requestMatchers(HttpMethod.POST, "/products").hasAnyAuthority("ADMIN", "SELLER")
                                .requestMatchers(HttpMethod.PUT, "/products/**").hasAnyAuthority("ADMIN", "SELLER")
                                .requestMatchers(HttpMethod.DELETE, "/products/**").hasAnyAuthority("ADMIN", "SELLER")
                                // Toegang tot categories
                                .requestMatchers(HttpMethod.POST, "/categories/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/categories/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/categories/**").hasAuthority("ADMIN")
                                // Toegang tot orders
                                .requestMatchers(HttpMethod.GET, "/orders/**").hasAnyAuthority("ADMIN", "SELLER", "BUYER") // ADMIN, SELLER, en BUYER kunnen alle orders bekijken
                                .requestMatchers(HttpMethod.POST, "/orders/create/**").hasAnyAuthority("SELLER", "BUYER") // SELLER en BUYER kunnen orders creëren
                                .requestMatchers(HttpMethod.PUT, "/orders/**").hasAnyAuthority("ADMIN", "SELLER", "BUYER") // ADMIN, SELLER, en BUYER kunnen orders bijwerken
                                .requestMatchers(HttpMethod.DELETE, "/orders/**").hasAuthority("ADMIN") // Alleen ADMIN kan orders verwijderen
                                .requestMatchers(HttpMethod.GET, "/orders/users/**").hasAnyAuthority("ADMIN", "SELLER", "BUYER") // ADMIN, SELLER, en BUYER kunnen orders per gebruiker bekijken
                                // Toegang tot transacties
                                .requestMatchers(HttpMethod.POST, "/transactions").hasAnyAuthority("SELLER", "BUYER") // SELLER en BUYER kunnen transacties creëren
                                .requestMatchers(HttpMethod.GET, "/transactions").hasAnyAuthority("ADMIN", "SELLER", "BUYER") // ADMIN, SELLER, en BUYER kunnen alle transacties bekijken
                                .requestMatchers(HttpMethod.GET, "/transactions/users/**").hasAnyAuthority("ADMIN", "SELLER", "BUYER") // ADMIN, SELLER, en BUYER kunnen transacties per gebruiker bekijken
                                .requestMatchers(HttpMethod.GET, "/transactions/**").hasAnyAuthority("ADMIN", "SELLER", "BUYER") // ADMIN, SELLER, en BUYER kunnen specifieke transacties bekijken
                                .requestMatchers(HttpMethod.PATCH, "/transactions/**").hasAnyAuthority("ADMIN", "SELLER", "BUYER") // ADMIN, SELLER, en BUYER kunnen transacties bijwerken
                                .requestMatchers(HttpMethod.DELETE, "/transactions/**").hasAuthority("ADMIN") // Alleen ADMIN kan transacties verwijderen
                                // Toegangsregels voor winkelwagens
                                .requestMatchers(HttpMethod.GET, "/carts/**").hasAnyAuthority("ADMIN", "SELLER", "BUYER")
                                // Toegangsregels voor winkelwagenitems
                                .requestMatchers(HttpMethod.POST, "/cart-items/users/**").hasAnyAuthority("SELLER", "BUYER") // Toestaan dat SELLER en BUYER items toevoegen aan de winkelwagen
                                .requestMatchers(HttpMethod.DELETE, "/cart-items/**").hasAnyAuthority("SELLER", "BUYER") // Toestaan dat SELLER en BUYER items verwijderen uit hun winkelwagen
                                .requestMatchers(HttpMethod.PUT, "/cart-items/**").hasAnyAuthority("SELLER", "BUYER") // Toestaan dat SELLER en BUYER items in hun winkelwagen bijwerken
                                // Toegang tot reviews
                                .requestMatchers(HttpMethod.GET, "/reviews/**").permitAll() // Iedereen kan alle types van review verzoeken bekijken
                                .requestMatchers(HttpMethod.POST, "/reviews").hasAnyAuthority("BUYER") // Alleen BUYERs kunnen reviews creëren
                                .requestMatchers(HttpMethod.PUT, "/reviews/{reviewId}").hasAnyAuthority("ADMIN", "BUYER") // ADMIN en de originele BUYER kunnen reviews bijwerken
                                .requestMatchers(HttpMethod.DELETE, "/reviews/{reviewId}").hasAuthority("ADMIN") // Alleen ADMIN kan reviews verwijderen

                                // Algemene regels
//                                .requestMatchers("/users/**").permitAll()
                        .anyRequest().authenticated() // Alle andere verzoeken vereisen authenticatie
                )
                // JWT Token filter voor authenticatie
                .addFilterBefore(new JwtRequestFilter(jwtService, userDetailsService()), UsernamePasswordAuthenticationFilter.class);

        // Verdere configuratie zoals noodzakelijk
        return http.build();
    }
}



