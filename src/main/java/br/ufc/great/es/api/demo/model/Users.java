package br.ufc.great.es.api.demo.model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonBackReference;


/**
 * Classe modelo de Usuário
 * @author armandosoaressousa
 *
 */
@Entity
public class Users extends AbstractModel<Long> implements UserDetails{	
	private static final long serialVersionUID = 1L;
	@Column(length=50)
	private String username;
	@Column(length=255)
	private String password;
	@Column(nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean enabled;
	@Column(length=255)
	private String email;	
	private double latitude=0;
	private double longitude=0;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Role> roles = new LinkedList<Role>();
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonBackReference(value="user-friend")
	@ManyToMany(fetch = FetchType.LAZY)
 	private List<Users> friendsList = new LinkedList<Users>();
	@Column(length=255)	
	private String name;
	
	public Users() {
		this.friendsList = new LinkedList<Users>();
	}
	
	public Users(String username, String password, String email) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Long getId() {
		Long id;
		id = super.getId();
		return id;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * Lista todos os amigos do usuario
	 * @return List<Users> lista de amigos do usuario
	 */
	public List<Users> getFriendsList() {
		return friendsList;
	}

	/**
	 * Atualiza a lista de amigos do usuario
	 * @param friendsList
	 */
	public void setFriendsList(List<Users> friendsList) {
		this.friendsList = friendsList;
	}
	
	/**
	 * Adiciona um novo amigo ao usuario
	 * @param friend
	 * @return true se o amigo foi adicionado com sucesso
	 */
	public boolean addFriend(Users friend) {
		if (!alreadyFriend(friend)) {
			this.friendsList.add(friend);	
			return true;
		}else {
			return false;
		} 
	}
	
	/**
	 * Checa se o usuario amigo já é amigo do usuario corrente
	 * @param friend
	 * @return true se o amigo já é amigo do usuario
	 */
	public boolean alreadyFriend(Users friend) {
		//percorre a lista de amigos e checa se o amigo já está nela
		for (Users user : this.friendsList) {
			if (user.getId() == friend.getId()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Dado um amigo, revove esse amigo da lista de amigos do usuario
	 * @param friend amigo que sera removido
	 * @return true se o amigo foi removido com sucesso
	 */
	public boolean deleteFriend(Users friend) {		
		//pega a lista de amigos
		List<Users> listaAux = this.getFriendsList();
		int tamanhoListaAux = listaAux.size();
		boolean achou=false;
		//encontra o indice do usuario a ser removido
		for (int i=0; i < tamanhoListaAux; i++) {
			if (listaAux.get(i).getId() == friend.getId()) {			
				this.friendsList.remove(i);
				achou=true;
				break;
			}
		}
		
		return achou;
	}
		
	/**
	 * Quantidade de amigos do usuario
	 * @return quantidade de amigos
	 */
	public int getAmountOfFriends() {
		return this.getFriendsList().size();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Lista as permissoes do usuario
	 * @return lista de permissoes
	 */
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return (Collection<? extends GrantedAuthority>) this.getRoles();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

}