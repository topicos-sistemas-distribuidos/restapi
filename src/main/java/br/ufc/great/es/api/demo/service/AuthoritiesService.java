package br.ufc.great.es.api.demo.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.ufc.great.es.api.demo.model.Role;
import br.ufc.great.es.api.demo.repository.IAuthoritiesRepository;

@Service
public class AuthoritiesService extends AbstractService<Role, Long>{
	@Autowired
	private IAuthoritiesRepository authoritiesRepository; 
	
	@Override
	protected JpaRepository<Role, Long> getRepository(){
		return authoritiesRepository;
	}

	/**
	 * Lista todas as permissões registradas
	 * @return List<Authorities> 
	 */
	public List<Role> getListAll() {
		return authoritiesRepository.findAll();
	}

	public Role getRoleByNome(String nome) {
		return authoritiesRepository.findByNome(nome);
	}
	
    /**
     * Verifica se a permissão foi registrada no sistema
     * @param nome permissão selecionada pelo usuário
     * @return true se a permissão existe no sistema
     */
    public String checkAuthority(String nome) {
    	List<Role> roles = new LinkedList<>();
    	roles = this.getListAll();
    	String valor = null;
    	
    	for (Role elemento : roles) {
    		if (elemento.getNome().equals(nome)) {
    			valor = elemento.getNome();
    			break;
    		}
    	}
    	
		return valor;
	}

}
