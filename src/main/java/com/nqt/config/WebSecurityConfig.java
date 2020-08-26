package com.nqt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	//private static final String[] CSRF_IGNORE = { "/api/auth/**", "api/products/**" };

//	@Autowired
//	private UserDetailsServiceImpl userDetailsService;

//	@Autowired
//	private JwtAuthEntryPoint unauthorizedHandler;
//	
//	@Bean
//	public JwtAuthTokenFilter authenticationJwtTokenFilter() {
//		return new JwtAuthTokenFilter();
//	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//
//		// Sét đặt dịch vụ để tìm kiếm User trong Database.
//		// Và sét đặt PasswordEncoder.
//		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//
//	}
	
//	@Bean
//	@Override
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//		// TODO Auto-generated method stub
//		return super.authenticationManagerBean();
//	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

//		http.csrf()
//			.ignoringAntMatchers(CSRF_IGNORE)
//			.csrfTokenRepository(csrfTokenRepository())
//			.and()
//			.addFilterAfter(new SecurityCsrfFilter(), CsrfFilter.class);
		
//		http.cors().and().authorizeRequests()
//				.antMatchers("/users").permitAll()
//				//.anyRequest().authenticated()
//				.and()
//				.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		http.authorizeRequests()
		.antMatchers("/**").permitAll();
		
		http.csrf().disable();
		//.anyRequest().authenticated()
		

	}

//	private CsrfTokenRepository csrfTokenRepository() {
//		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
//		repository.setHeaderName(SecurityCsrfFilter.CSRF_COOKIE_NAME);
//		return repository;
//	}

}