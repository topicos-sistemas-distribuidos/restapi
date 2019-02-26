package br.ufc.great.es.api.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.ufc.great.es.api.demo.model.Users;
import br.ufc.great.es.api.demo.repository.IUsersRepository;

/**
 * Classe de serviço para consumir o repositório de dados de Usuário
 * @author armandosoaressousa
 *
 */
@Service
public class UsersService extends AbstractService<Users, Long>{

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
	
}