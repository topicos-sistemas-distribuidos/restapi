package br.ufc.great.es.api.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.ufc.great.es.api.demo.security.authentication.AuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private AuthenticationEntryPoint authEntryPoint;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
	    http.csrf().disable()
        .authorizeRequests()
        .and()
        .exceptionHandling()
        .and()
        .authorizeRequests()
        .antMatchers("/demo/users/**").authenticated()        
        .antMatchers("/index.html").permitAll();
	    
	    http.httpBasic().authenticationEntryPoint(authEntryPoint);
    }
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		
		return bCryptPasswordEncoder;
	}

	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
         
        String password = "armando"; 
        String encrytedPassword = this.passwordEncoder().encode(password);
        System.out.println("Encoded password of armando=" + encrytedPassword);
         
        InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> mngConfig = auth.inMemoryAuthentication();
 
        // Defines 2 users, stored in memory.
        // ** Spring BOOT >= 2.x (Spring Security 5.x)
        // Spring auto add ROLE_
        //UserDetails u1 = User.withUsername("armando").password(encrytedPassword).roles("USER").build();
        //UserDetails u2 = User.withUsername("jerry").password(encrytedPassword).roles("USER").build();
        //mngConfig.withUser(u1);
        //mngConfig.withUser(u2);
 
        // If Spring BOOT < 2.x (Spring Security 4.x)):
        // Spring auto add ROLE_
        mngConfig.withUser("armando").password("armando").roles("USER");
        mngConfig.withUser("jerry").password("123").roles("USER");
    }
	
}
