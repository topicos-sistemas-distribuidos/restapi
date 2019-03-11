package br.ufc.great.es.api.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.ufc.great.es.api.demo.security.authentication.AuthenticationEntryPoint;
import br.ufc.great.es.api.demo.service.UsersService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private AuthenticationEntryPoint authEntryPoint;
	
	@Autowired
	private UsersService userService;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
	    http.csrf().disable()
        .authorizeRequests()
        .and()
        .exceptionHandling()
        .and()
        .authorizeRequests()
        .antMatchers("/demo/users/**").authenticated()        
        .antMatchers("/index.html").permitAll()
        .antMatchers("/resources/**").permitAll()
        .anyRequest().authenticated();
	    
	    http.httpBasic().authenticationEntryPoint(authEntryPoint);
    }
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		
		return bCryptPasswordEncoder;
	}

	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userService)
        	.passwordEncoder(new BCryptPasswordEncoder());
    }
	
}