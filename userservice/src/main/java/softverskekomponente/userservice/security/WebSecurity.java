package softverskekomponente.userservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import softverskekomponente.userservice.entities.Admin;
import softverskekomponente.userservice.repositories.AdminRepository;
import softverskekomponente.userservice.repositories.UserRepository;

import static softverskekomponente.userservice.security.SecurityConstants.*;

import java.util.Arrays;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	private CustomAuthenticationProvider customAuthenticationProvider;
	private BCryptPasswordEncoder encoder;
	private UserRepository userRepo;
	private AdminRepository adminRepo;

	@Autowired
	public WebSecurity(CustomAuthenticationProvider customAuthenticationProvider, UserRepository userRepo,
			BCryptPasswordEncoder encoder, AdminRepository adminRepo) {
		super();
		this.customAuthenticationProvider = customAuthenticationProvider;
		this.userRepo = userRepo;
		this.adminRepo = adminRepo;
		this.encoder = encoder;
		
		if (adminRepo.existsByEmail("admin@gmail.com")) {
			
		}else {
			Admin admin = new Admin("admin@gmail.com", encoder.encode("admin123"));
			adminRepo.saveAndFlush(admin);
			
		}
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().and().csrf().disable().authorizeRequests().antMatchers(LOGIN_PATH, REGISTRATION_PATH).permitAll()
				.anyRequest().authenticated().and().addFilter(new JWTAuthenticationFilter(authenticationManager()))
				.addFilter(new JWTAuthorizationFilter(authenticationManager(), userRepo, adminRepo)).sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "PATCH"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.addExposedHeader("Authorization");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {

		customAuthenticationProvider.setEncoder(encoder);
		auth.authenticationProvider(customAuthenticationProvider);
	}

}