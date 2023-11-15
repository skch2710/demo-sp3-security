package com.demosp3security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.demosp3security.service.CustomeUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
//this annotation will enable method level security
public class SecurityConfig{
	
	@Autowired
	private CustomeUserDetailsManager customeUserDetailsManager;
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
//	@Bean
//	UserDetailsManager inMemoryUserDetailsManager() {
//		
//		Collection<GrantedAuthority> authorities = new HashSet<>();
//		authorities.add(new SimpleGrantedAuthority("Super User"));
//		
//		Collection<GrantedAuthority> authorities1 = Arrays.asList(
//			    new SimpleGrantedAuthority("Super User"),
//			    new SimpleGrantedAuthority("Admin"));
//		
//		User ur1 = new User("skch2710@gmail.com", passwordEncoder().encode("S@thi$+b27"), authorities);
//		User ur2 = new User("my-user", passwordEncoder().encode("password"), authorities1);
//		
////		UserDetails user1 = User.withUsername("skch2710@gmail.com").password(passwordEncoder().encode("S@thi$+b27")).roles("USER").build();
////		UserDetails user2 = User.withUsername("my-user").password(passwordEncoder().encode("password")).roles("USER", "ADMIN").build();
//		return new InMemoryUserDetailsManager(ur1,ur2);
//	}
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customeUserDetailsManager);
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.securityMatcher("/api/v1/**")
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
						.requestMatchers("/api/public/**").permitAll()
						.anyRequest().authenticated())
				.csrf(Customizer.withDefaults())
				.formLogin(Customizer.withDefaults())
				.httpBasic(Customizer.withDefaults())
				.build();
	}
	
}
