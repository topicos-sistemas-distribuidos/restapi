package br.ufc.great.es.api.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufc.great.es.api.demo.model.Users;

/**
 * Interface repositório de Usuário baseada no JPARepository do Spring
 * @author armandosoaressousa
 *
 */
@Repository
public interface UsersRepository extends JpaRepository<Users, Long>{
	Users findByUsername(String username);
	Users findByEmail(String email);
}