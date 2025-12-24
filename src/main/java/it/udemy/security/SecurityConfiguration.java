package it.udemy.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Bean
	public InMemoryUserDetailsManager userDetailsManager() {
		UserDetails user = User.builder()
							   .username("Edoardo")
							   .password(new BCryptPasswordEncoder().encode("password"))
							   .roles("USER")
							   .build();
		
		UserDetails admin = User.builder()
							    .username("Admin")
							    .password(new BCryptPasswordEncoder().encode("passwordAdmin"))
							    .roles("ADMIN", "USER")
							    .build();
		
		return new InMemoryUserDetailsManager(user, admin);
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> {
									auth.requestMatchers("/login/**", "/js/**", "/css/**", "/font/**", "/images/**")
										.permitAll();
									auth.requestMatchers("/articoli/aggiungi/**", "/articoli/modifica/**", "/articoli/elimina/**")
										.hasRole("ADMIN");
									auth.anyRequest().authenticated();
								  })
			.formLogin(form -> form.loginPage("/login")
						   .loginProcessingUrl("/autentica")
						   .usernameParameter("name")
						   .successHandler(this.authenticationSuccessHandler())
						   .failureHandler(this.authenticationFailureHandler())
						   .permitAll())
			.exceptionHandling(ex -> ex.accessDeniedHandler(this.accessDeniedHandler()));
		
		return http.build();
	}
	
	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new CustomSuccessHandler();
	}
	
	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		return new CustomFailureHandler();
	}
	
	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}
}
