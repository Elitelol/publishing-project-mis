package com.aivarasnakvosas.publishingservicemis.security;

import com.aivarasnakvosas.publishingservicemis.entity.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author Aivaras Nakvosas
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthTokenFilter authTokenFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();
        http.exceptionHandling().authenticationEntryPoint(
                (request, response, ex) -> response.sendError(
                                HttpServletResponse.SC_UNAUTHORIZED,
                                ex.getMessage()
                )
        ).and();
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/access/**").permitAll()
                .antMatchers("/publication/**").permitAll()
                .antMatchers("/task/**").permitAll()
                .antMatchers("/attachment/**").permitAll()
                .antMatchers("/budget/**").permitAll()
                .antMatchers("/contract/**").permitAll()
                .antMatchers("/type/**").permitAll()
                /*
                .antMatchers("/user/all").hasAnyRole()
                .antMatchers("/user").hasAnyRole()
                .antMatchers("/publication").hasAnyRole()
                .antMatchers("/publication/assign").hasAnyRole()
                .antMatchers("/publication/changeStatus").hasAnyRole()
                .antMatchers("/publication/contract").hasAnyRole()
                .antMatchers("/publication/budget").hasAnyRole()
                .antMatchers("/publication/all").hasAnyRole()
                .antMatchers("/publication/author").hasAnyRole()
                .antMatchers("/publication/manager").hasAnyRole()
                .antMatchers("/publication/byProgress").hasAnyRole()
                .antMatchers("/publication/unmanaged").hasAnyRole()
                .antMatchers(HttpMethod.POST ,"/task").hasAnyRole()
                .antMatchers(HttpMethod.GET ,"/task").hasAnyRole()
                .antMatchers(HttpMethod.POST ,"/task/comment").hasAnyRole()

                 */
                .anyRequest().authenticated();
        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(Collections.singletonList("http://localhost:3000"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE"));
        config.addAllowedHeader("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
