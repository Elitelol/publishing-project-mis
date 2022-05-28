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
                .antMatchers("/attachment/**").hasAnyRole(Role.AUTHOR.getRole(), Role.PUBLICATION_MANAGER.getRole(), Role.WORKER.getRole())
                .antMatchers("/contract").hasRole(Role.PUBLICATION_MANAGER.getRole())
                .antMatchers("/contract/comment").hasAnyRole(Role.AUTHOR.getRole(), Role.PUBLICATION_MANAGER.getRole(), Role.WORKER.getRole())
                .antMatchers("/contract/{publicationId}").hasAnyRole(Role.AUTHOR.getRole(), Role.PUBLICATION_MANAGER.getRole(), Role.WORKER.getRole())
                .antMatchers("/contract/{id}/downloadContract").hasRole(Role.AUTHOR.getRole())
                .antMatchers("/contract/deleteComment").hasAnyRole(Role.AUTHOR.getRole(), Role.PUBLICATION_MANAGER.getRole(), Role.WORKER.getRole())
                .antMatchers(HttpMethod.POST,"/publication").hasRole(Role.AUTHOR.getRole())
                .antMatchers("/publication/{id}/submit").hasRole(Role.AUTHOR.getRole())
                .antMatchers("/publication/changeStatus").hasRole(Role.PUBLICATION_MANAGER.getRole())
                .antMatchers("/publication/{id}").hasAnyRole(Role.AUTHOR.getRole(), Role.PUBLICATION_MANAGER.getRole(), Role.WORKER.getRole())
                .antMatchers("/publication/assign").hasRole(Role.PUBLICATION_MANAGER.getRole())
                .antMatchers("/publication/all").hasAnyRole(Role.AUTHOR.getRole(), Role.PUBLICATION_MANAGER.getRole(), Role.WORKER.getRole())
                .antMatchers("/publication/author/{id}").hasAnyRole(Role.AUTHOR.getRole(), Role.PUBLICATION_MANAGER.getRole(), Role.WORKER.getRole())
                .antMatchers("/publication/manager/{id}").hasAnyRole(Role.AUTHOR.getRole(), Role.PUBLICATION_MANAGER.getRole(), Role.WORKER.getRole())
                .antMatchers("/publication/byProgress").hasAnyRole(Role.AUTHOR.getRole(), Role.PUBLICATION_MANAGER.getRole(), Role.WORKER.getRole())
                .antMatchers("/publication/worker/{id}").hasAnyRole(Role.AUTHOR.getRole(), Role.PUBLICATION_MANAGER.getRole(), Role.WORKER.getRole())
                .antMatchers("/publication/unmanaged").hasRole(Role.PUBLICATION_MANAGER.getRole())
                .antMatchers("/publication/{id}/setComplete").hasRole(Role.PUBLICATION_MANAGER.getRole())
                .antMatchers("/publication/{id}/setContract").hasRole(Role.PUBLICATION_MANAGER.getRole())
                .antMatchers(HttpMethod.DELETE,"/publication").hasRole(Role.AUTHOR.getRole())
                .antMatchers("/publication/{id}/users").hasAnyRole(Role.AUTHOR.getRole(), Role.PUBLICATION_MANAGER.getRole(), Role.WORKER.getRole())
                .antMatchers("/budget").hasRole(Role.PUBLICATION_MANAGER.getRole())
                .antMatchers("/budget/comment").hasAnyRole(Role.AUTHOR.getRole(), Role.PUBLICATION_MANAGER.getRole(), Role.WORKER.getRole())
                .antMatchers("/budget/{publicationId}").hasAnyRole(Role.AUTHOR.getRole(), Role.PUBLICATION_MANAGER.getRole(), Role.WORKER.getRole())
                .antMatchers("/budget/{id}/downloadContract").hasAnyRole(Role.AUTHOR.getRole(), Role.PUBLICATION_MANAGER.getRole(), Role.WORKER.getRole())
                .antMatchers("/budget/deleteComment").hasAnyRole(Role.AUTHOR.getRole(), Role.PUBLICATION_MANAGER.getRole(), Role.WORKER.getRole())
                .antMatchers(HttpMethod.POST ,"/task").hasAnyRole(Role.WORKER.getRole(), Role.PUBLICATION_MANAGER.getRole())
                .antMatchers( "/task/{id}").hasAnyRole(Role.AUTHOR.getRole(), Role.PUBLICATION_MANAGER.getRole(), Role.WORKER.getRole())
                .antMatchers("/task/{publicationId}/notStartedTasks").hasAnyRole(Role.AUTHOR.getRole(), Role.PUBLICATION_MANAGER.getRole(), Role.WORKER.getRole())
                .antMatchers("/task/{publicationId}/inProgressTasks").hasAnyRole(Role.AUTHOR.getRole(), Role.PUBLICATION_MANAGER.getRole(), Role.WORKER.getRole())
                .antMatchers("/task/{publicationId}/completedTasks").hasAnyRole(Role.AUTHOR.getRole(), Role.PUBLICATION_MANAGER.getRole(), Role.WORKER.getRole())
                .antMatchers("/task/userTasks/{publicationId}/{userId}").hasRole(Role.WORKER.getRole())
                .antMatchers("/task/comment").hasAnyRole()
                .antMatchers(HttpMethod.DELETE, "task/{id}").hasRole(Role.PUBLICATION_MANAGER.getRole())
                .antMatchers("/task/deleteComment").hasAnyRole(Role.AUTHOR.getRole(), Role.PUBLICATION_MANAGER.getRole(), Role.WORKER.getRole())
                .antMatchers("/type/**").permitAll()
                .antMatchers("/user/**").hasAnyRole(Role.AUTHOR.getRole(), Role.PUBLICATION_MANAGER.getRole(), Role.WORKER.getRole())
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
