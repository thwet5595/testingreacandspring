package com.thwet.react.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.thwet.react.test.service.impl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	@Qualifier("userDetailsService")
	private UserDetailsServiceImpl userDetailsService;

	@Bean("userAuthenticationProvider")
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);
		return authenticationProvider;
	}

	@Autowired
	@Qualifier("userAuthenticationProvider")
	private DaoAuthenticationProvider userAuthenticationProvider;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(userAuthenticationProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/user/**").authorizeRequests().anyRequest().hasRole("USER").and().formLogin()
				.loginPage("/user/login").permitAll().loginProcessingUrl("/user/loginPost")
				.usernameParameter("email").passwordParameter("password").permitAll().defaultSuccessUrl("/user")
				.and().logout().logoutUrl("/user/logout").logoutSuccessUrl("/user/login?logout").deleteCookies("JSESSIONID").and().csrf();
	}
}
