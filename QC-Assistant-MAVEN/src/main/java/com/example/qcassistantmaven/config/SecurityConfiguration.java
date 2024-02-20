package com.example.qcassistantmaven.config;

import com.example.qcassistantmaven.domain.enums.RoleEnum;
import com.example.qcassistantmaven.repository.auth.UserRepository;
import com.example.qcassistantmaven.service.auth.UserDetailsServiceImpl;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity,
                                           SecurityContextRepository securityContextRepository) throws Exception{
        httpSecurity.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .permitAll()
                .requestMatchers(
                        "/login", "/register", "/login-error", "/register-error"
                )
                .permitAll()
                .requestMatchers(
                        "/languages/edit/**", "/languages/add/**",
                        "/destinations/edit/**", "/destinations/add/**",
                        "/destinations/import",

                        "/medidata/studies/add/**", "/medidata/studies/edit/**",
                        "/medidata/sponsors/add/**", "/medidata/sponsors/edit/**",
                        "/medidata/apps/add/**", "/medidata/apps/edit/**",
                        "/medidata/tags/add/**", "medidata/tags/edit/**",

                        "/iqvia/studies/add/**", "/iqvia/studies/edit/**",
                        "/iqvia/sponsors/add/**", "/iqvia/sponsors/edit/**",
                        "/iqvia/apps/add/**", "/iqvia/apps/edit/**",
                        "/iqvia/tags/add/**", "iqvia/tags/edit/**",

                        "/medable/studies/add/**", "/medable/studies/edit/**",
                        "/medable/sponsors/add/**", "/medable/sponsors/edit/**",
                        "/medable/apps/add/**", "/medable/apps/edit/**",
                        "/medable/tags/add/**", "medable/tags/edit/**"
                )
                .hasRole(RoleEnum.MODERATOR.name())
                .requestMatchers("/users/**")
                .hasRole(RoleEnum.ADMINISTRATOR.name())
                .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                        .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
                        .defaultSuccessUrl("/", true)
                        .failureForwardUrl("/login-error")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                )
                .securityContext(security -> security
                        .securityContextRepository(securityContextRepository())
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedPage("/access-denied"));

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new UserDetailsServiceImpl(userRepository);
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new DelegatingSecurityContextRepository(
                new RequestAttributeSecurityContextRepository(),
                new HttpSessionSecurityContextRepository()
        );
    }
}














