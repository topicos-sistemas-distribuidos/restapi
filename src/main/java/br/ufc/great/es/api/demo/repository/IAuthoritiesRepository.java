package br.ufc.great.es.api.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufc.great.es.api.demo.model.Role;


@Repository
public interface IAuthoritiesRepository extends JpaRepository<Role, Long>{
	Role findByNome(String nome);
}
