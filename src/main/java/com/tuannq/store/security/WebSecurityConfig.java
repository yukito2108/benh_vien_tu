package com.tuannq.store.security;

import com.tuannq.store.config.PasswordEncoderConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig<CustomUserDetailService> extends WebSecurityConfigurerAdapter {
    private final AuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final JwtRequestFilter jwtRequestFilter;

    private final JwtUserDetailsService jwtUserDetailsService;

    private final PasswordEncoderConfig passwordEncoder;

    @Autowired
    public WebSecurityConfig(
            PasswordEncoderConfig passwordEncoder,
            AuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtRequestFilter jwtRequestFilter,
            JwtUserDetailsService jwtUserDetailsService
    ) {
        this.passwordEncoder = passwordEncoder;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtRequestFilter = jwtRequestFilter;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }


    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder.passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                // swagger
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/home/**").hasAnyRole("CUSTOMER", "PROVIDER", "ADMIN")
//                .antMatchers("/api/**").hasAnyRole("CUSTOMER", "PROVIDER", "ADMIN")
                .antMatchers("/customers/all").hasRole("ADMIN")
                .antMatchers("/providers/new").hasRole("ADMIN")
                .antMatchers("/invoices/all").hasRole("ADMIN")
                .antMatchers("/providers/all").hasRole("ADMIN")
                .antMatchers("/customers/**").hasAnyRole("CUSTOMER", "ADMIN")
                .antMatchers("/providers/availability/**").hasRole("PROVIDER")
                .antMatchers("/providers/**").hasAnyRole("PROVIDER", "ADMIN")
                .antMatchers("/works/**").hasRole("ADMIN")
                .antMatchers("/exchange/**").hasRole("CUSTOMER")
                .antMatchers("/appointments/new/**").hasRole("CUSTOMER")
                .antMatchers("/appointments/**").hasAnyRole("CUSTOMER", "PROVIDER", "ADMIN")
                .antMatchers("/invoices/**").hasAnyRole("CUSTOMER", "PROVIDER", "ADMIN")
                // other

//                .antMatchers("/api/login").permitAll()
                .antMatchers(
                        "/api/order",
                        "/api/cart**",
                        "/cart**",
                        "/checkout**",
                        "/account**",
                        "/api/change-password",
                        "/api/update-profile"
                ).authenticated()
                .antMatchers("/admin/**", "/api/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()
                .and()
                .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessUrl("/")
                .deleteCookies("JWT_TOKEN")
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/css/**", "/script/**", "/image/**", "/vendor/**", "/favicon.ico", "/adminlte/**", "/media/static/**", "/js/**");
    }
}
