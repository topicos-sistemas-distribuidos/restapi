package br.ufc.great.es.api.demo.model;

import javax.persistence.Entity;
import org.springframework.security.core.GrantedAuthority;

@Entity
public class Role extends AbstractModel<Long> implements GrantedAuthority{
	private static final long serialVersionUID = 1L;
	private String nome;
	    
	public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

	@Override
	public String getAuthority() {
		return this.nome;
	}

}