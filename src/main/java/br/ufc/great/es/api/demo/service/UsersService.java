package br.ufc.great.es.api.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.ufc.great.es.api.demo.model.Users;
import br.ufc.great.es.api.demo.repository.IUsersRepository;

/**
 * Classe de serviço para consumir o repositório de dados de Usuário
 * @author armandosoaressousa
 *
 */
@Service
public class UsersService extends AbstractService<Users, Long> implements UserDetailsService {

	@Autowired
	private IUsersRepository usersRepository;
	
	@Override
	protected JpaRepository<Users, Long> getRepository(){
		return usersRepository;
	}
	
	public Users getUserByUserName(String username) {
		return usersRepository.findByUsername(username);
	}
	
	public Users getUserByEmail(String email) {
		return usersRepository.findByEmail(email);
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Users usuario = usersRepository.findByUsername(userName);
		
		if (usuario == null) {
			throw new UsernameNotFoundException("O usuário " + userName + " não foi encontrado");
		}
		
		return (UserDetails) usuario;
	}
	
}