package com.hahn.banco.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.hahn.banco.service.implement.ClientServiceImpl;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
    @Autowired
	private ClientServiceImpl clientService;

	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
          .authorizeRequests()
          .antMatchers(HttpMethod.POST, "/login").permitAll()
          .antMatchers(HttpMethod.GET, "/api/v1/department/**").permitAll()
          .antMatchers(HttpMethod.POST, "/api/v1/department/**").permitAll()
          .antMatchers(HttpMethod.GET, "/api/v1/city/**").permitAll()
          .antMatchers(HttpMethod.POST, "/api/v1/city/**").permitAll()
          .antMatchers(HttpMethod.GET, "/api/v1/address/**").permitAll()
          .antMatchers(HttpMethod.POST, "/api/v1/address/**").permitAll()
          .anyRequest().authenticated()
          .and()
          .addFilter(new JWTAuthenticationFilter(authenticationManager()))
          .addFilter(new JWTAuthorizationFilter(authenticationManager()))
          .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		  .and().httpBasic();
    }
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
	    auth.userDetailsService(clientService).passwordEncoder(passwordEnconder());
	}
    
    @Bean
	CorsConfigurationSource corsConfigurationSource() {
	    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
	    return source;
	}

	@Bean
	public StrictHttpFirewall httpFirewall() {
		StrictHttpFirewall firewall = new StrictHttpFirewall();
		firewall.setAllowedHttpMethods(Arrays.asList("GET", "POST"));
		return firewall;
	}
	
	@Bean 
	public BCryptPasswordEncoder passwordEnconder() {
		BCryptPasswordEncoder passwordEnconder = new BCryptPasswordEncoder();
		return passwordEnconder;
	}
	
}


