package mk.oleg.java.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;
import mk.oleg.java.security.jwt.filter.JwtAuthorizationFilter;
import mk.oleg.java.security.jwt.filter.JwtUsernamePasswotdAuthenticationFilter;

@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final PasswordEncoder passwordEncoder; 
	private final AuthenticationConfiguration config;
	private final UserDetailsService userDetailsService;
	
	@Bean
	public SecurityFilterChain securityChain(HttpSecurity http) throws Exception {
		JwtUsernamePasswotdAuthenticationFilter authenticationFilter =
				new JwtUsernamePasswotdAuthenticationFilter(authenticationManager(config));
		JwtAuthorizationFilter authorizationFilter = new JwtAuthorizationFilter(userDetailsService);
		http.csrf().disable()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilter(authenticationFilter)
			.addFilterBefore(authorizationFilter, JwtUsernamePasswotdAuthenticationFilter.class)
			.authorizeRequests()
				.antMatchers("/login","/api/user/addStudentUser").permitAll()
				.anyRequest()
					.authenticated();
	
		return http.build();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setPasswordEncoder(passwordEncoder);
		authenticationProvider.setUserDetailsService(userDetailsService);
		return authenticationProvider;
	}
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}


}
