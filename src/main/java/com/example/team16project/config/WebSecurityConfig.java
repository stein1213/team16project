package com.example.team16project.config;


import com.example.team16project.service.user.UserDetailsServiceImpl;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Bean
    public WebSecurityCustomizer configure() {
        return web ->
                web
                .ignoring()
                .requestMatchers("/js/**", "/css/**", "/images/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(auth ->
                        auth
                                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .requestMatchers("/articles", "/article","/user/login", "/user/signup").permitAll()
                                .requestMatchers("/admin", "/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()).exceptionHandling(except ->
                        except.accessDeniedPage("/error/403"))
            .formLogin(auth -> auth.loginPage("/user/login")
                    .defaultSuccessUrl("/user/check", true)
                            .failureUrl("/user/login?error=true")
                    )
                .logout(auth -> auth.logoutSuccessUrl("/user/login")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true))
                .csrf(auth -> auth.disable())
                .build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
