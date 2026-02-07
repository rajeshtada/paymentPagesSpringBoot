package com.ftk.pg.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

//	@Autowired
//	private CustomUserDetailsService customUserDetailsService;

//	@Autowired
//	private JwtTokenProvider tokenProvider;

	static List<String> permitAllUrls = List.of("/**");

	@Bean(name = "customSecurityFilterChain")
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		RequestMatcher r = request -> permitAllUrls.stream()
				.anyMatch(url -> new AntPathRequestMatcher(url).matches(request));

		http
//		.csrf(csrf -> csrf.disable())
		.csrf((csrf) -> csrf.ignoringRequestMatchers("/**"))
		.authorizeHttpRequests(requests -> requests
//								.requestMatchers("/frm", "/frm/api/**", "/frm/ui/**", "/frm/swagger-ui/**", "/frm/v3/api-docs/**",
//										"/frm/swagger-ui-custom.html", "/frm/api-docs/**","/frm/ping")

				.requestMatchers(r)

				.permitAll().anyRequest().authenticated())
				.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

//		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

//	@Bean
//	WebSecurityCustomizer webSecurityCustomizer() {
//		return (web) -> web.ignoring().requestMatchers("/security");
//	}

//	@Bean
//	JwtAuthenticationFilter jwtAuthenticationFilter() {
//		return new JwtAuthenticationFilter(tokenProvider, customUserDetailsService);
//	}
//
//	@Bean(name = "customAuthenticationManager")
//	AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//		return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(customUserDetailsService)
//				.passwordEncoder(passwordEncoder()).and().build();
//	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);

		// Add multiple allowed origins
		config.setAllowedOrigins(List.of("https://cdn.getepay.in", "https://d22922sbcxgvkr.cloudfront.net",
				"http://localhost:9000", "http://localhost:8080", "http://localhost:8081", "http://localhost:8082",
				"http://localhost:5500", "https://pay1.getepay.in:8443", "https://d3m7yytvp0uph9.cloudfront.net",
				"http://192.168.8.239:8080", "http://127.0.0.1:8080","http://192.168.8.239:5000","https://uiportal.mygetepay.com",
				"http://127.0.0.1:5501","https://template.artimusin.com/"));

		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

//	 @Bean
//	    public CorsFilter corsFilter() {
//	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//	        CorsConfiguration config = new CorsConfiguration();
//	        config.setAllowCredentials(true);
//	        config.addAllowedOrigin("http://localhost:9000"); // Replace with your frontend URL
//	        config.addAllowedHeader("*");
//	        config.addAllowedMethod("*");
//	        source.registerCorsConfiguration("/**", config);
//	        return new CorsFilter(source);
//	    }

}
