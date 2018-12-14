package br.ufc.great.es.api.demo.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Classe modelo de Usuário
 * @author armandosoaressousa
 *
 */
@Entity
public class Users extends AbstractModel<Long>{	
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
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonBackReference(value="user-friend")
	@ManyToMany(fetch = FetchType.LAZY)
 	private List<Users> idFriendsList = new LinkedList<Users>();
	@Column(length=255)	
	private String name;
	
	public Users() {
		this.idFriendsList = new LinkedList<Users>();
	}
	
	public Users(String username, String password, String email) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

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
	 * Lista todos os ids dos amigos
	 * @return List<Long>
	 */
	public List<Users> getIdFriendsList() {
		return idFriendsList;
	}

	/**
	 * Atualiza a lista de Ids de amigos
	 * @param idFriendsList
	 */
	public void setIdFriendsList(List<Users> idFriendsList) {
		this.idFriendsList = idFriendsList;
	}
	
	/**
	 * Adiciona um novo id de amigo
	 * @param idFriend
	 */
	public boolean addIdFriend(Users idFriend) {
		if (!alreadyFriend(idFriend)) {
			this.idFriendsList.add(idFriend);	
			return true;
		}else {
			return false;
		} 
	}
	
	public boolean alreadyFriend(Users idFriend) {
		//percorre a lista de amigos e checa se o amigo já está nela
		for (Users idUser : this.idFriendsList) {
			if (idUser.getId() == idFriend.getId()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean deleteFriend(Users idFriend) {		
		//pega a lista de amigos
		List<Users> listaAux = this.getIdFriendsList();
		int tamanhoListaAux = listaAux.size();
		boolean achou=false;
		//encontra o indice do usuario a ser removido
		for (int i=0; i < tamanhoListaAux; i++) {
			if (listaAux.get(i).getId() == idFriend.getId()) {			
				this.idFriendsList.remove(i);
				achou=true;
				break;
			}
		}
		
		return achou;
	}
	
	public int getAmountOfFriends() {
		return this.getIdFriendsList().size();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
		
}