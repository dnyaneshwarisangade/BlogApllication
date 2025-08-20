package com.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.blog.security.JwtAuthenticationEntryPoint;
import com.blog.security.JwtAuthenticationFilter;





@Configuration
@EnableMethodSecurity(prePostEnabled=true)
public class SecurityConfig {
	public static String [] PUBLIC_URLS= {"/api/v1/auth/**",
			"/v3/api-docs/**",
		    "/swagger-ui/**",
		    "/swagger-ui.html",
		    "/swagger-resources/**",
		    "/webjars/**"};
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Bean 
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		//it allows all get methods and url starting with /api/v1/auth/
		
		http.csrf(csrf -> csrf.disable())
	    .authorizeHttpRequests(auth -> auth
	            .requestMatchers(PUBLIC_URLS).permitAll()
	            .requestMatchers(HttpMethod.GET).permitAll()
	            .anyRequest().authenticated()
	        )
		.exceptionHandling(ex-> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
		
	}
	
	

	
	 @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
	        return builder.getAuthenticationManager();
	    }
}
