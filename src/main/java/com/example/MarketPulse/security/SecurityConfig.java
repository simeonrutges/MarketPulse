package com.example.MarketPulse.security;

import com.example.MarketPulse.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

//    private final JwtService jwtService;
//    private final UserRepository userRepository;
//    private final UserDetailsService udService;
//    private final PasswordEncoder passwordEncoder;
//
//    public SecurityConfig(JwtService jwtService, UserRepository userRepository, UserDetailsService udService, PasswordEncoder passwordEncoder) {
//        this.jwtService = jwtService;
//        this.userRepository = userRepository;
//        this.udService = udService;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManagerBean(AuthenticationManagerBuilder builder) throws Exception {
//        builder.userDetailsService(udService).passwordEncoder(passwordEncoder);
//        return builder.build();
//    }
//
////    @Bean
//    public UserDetailsService userDetailsService() {
//        return new MyUserDetailsService(this.userRepository);
//    }
//
////    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((authz) -> authz
//                        .requestMatchers("/**").permitAll() // Sta alles toe
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(Customizer.withDefaults())
//                .csrf(AbstractHttpConfigurer::disable) // Schakel CSRF uit
//                .cors(Customizer.withDefaults()) // Standaard CORS-configuratie
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                );
//
//        return http.build();
//    }

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
//                .authorizeHttpRequests((authz) -> authz
//                        .requestMatchers("/**").permitAll() // Sta alles toe
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(Customizer.withDefaults())
//                .csrf(AbstractHttpConfigurer::disable) // Schakel CSRF uit
//                .cors(Customizer.withDefaults()) // Standaard CORS-configuratie
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                );
//
//        return http.build();
//    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((authz) -> authz
//                        // Definieer paden en rollen
//                        .requestMatchers(HttpMethod.POST, "/users").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/roles").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/auth").permitAll()
//                        .requestMatchers(HttpMethod.DELETE, "/users").hasAuthority("ROLE_SELLER")
////                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
////                        .requestMatchers("/user/**").permitAll()
////                        .requestMatchers("/management/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER")
//                        // Voor alle andere paden vereis authenticatie
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(Customizer.withDefaults())
//                .csrf(AbstractHttpConfigurer::disable) // Schakel CSRF uit
//                .cors(Customizer.withDefaults()) // Standaard CORS-configuratie
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                );
//
//        return http.build();

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
                        .requestMatchers(HttpMethod.POST, "/users").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth").permitAll()
                        .requestMatchers(HttpMethod.POST, "/roles").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/users/**").hasAuthority("SELLER")
                        .anyRequest().authenticated()
                )
                // JWT Token filter voor authenticatie
                .addFilterBefore(new JwtRequestFilter(jwtService, userDetailsService()), UsernamePasswordAuthenticationFilter.class);

        // Verdere configuratie zoals noodzakelijk
        return http.build();
    }


}



