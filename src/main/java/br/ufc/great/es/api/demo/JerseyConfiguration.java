package br.ufc.great.es.api.demo;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import br.ufc.great.es.api.demo.controller.AccessControlController;
import br.ufc.great.es.api.demo.controller.UserController;
import br.ufc.great.es.api.demo.exceptions.GenericExceptionMapper;
import br.ufc.great.es.api.demo.exceptions.ServiceExceptionMapper;

/**
 * Faz a configuracao do Servico Jersey
 * @author armandosoaressousa
 */
@Configuration
@ApplicationPath("demo")
public class JerseyConfiguration extends ResourceConfig {
	
	public JerseyConfiguration() {
	}
	
	/**
	 * Faz os registros dos controllers
	 */
	@PostConstruct
	public void setUp() {		
		register(UserController.class);
		register(AccessControlController.class);
		register(ServiceExceptionMapper.class);
        register(GenericExceptionMapper.class);
	}
}