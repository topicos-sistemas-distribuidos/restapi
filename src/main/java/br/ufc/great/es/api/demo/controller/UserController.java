package br.ufc.great.es.api.demo.controller;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufc.great.es.api.demo.model.Users;
import br.ufc.great.es.api.demo.service.UsersService;
import br.ufc.great.es.api.demo.utils.GeradorSenha;
import br.ufc.great.es.api.demo.utils.Message;


/**
 * Users Controller
 * @author armandosoaressousa
 *
 */
@Component
@Path("/users")
public class UserController {
	UsersService userService;
	
	@Autowired
	public void setUserService(UsersService userServices){
		this.userService = userServices;
	}
		
	/**
	 * Contrutor of UserController
	 * @param userService
	 */
	public UserController() {	   
	}

	 /**
     * Retorna em um JSON todos os usuarios cadastrados
     * @return código http
     */
    @GET
    @Produces("application/json")
    public List<Users> getAllUsers() {
       	List<Users> listUsers = new LinkedList<Users>();
    	listUsers = userService.getListAll();
    	return listUsers;
    }
    
    /**
     * Dado um id retorna o JSON dos dados do usuario
     * @param id
     * @return código http
     */
    @GET
    @Produces("application/json")
    @Path("/{id}")
    public Users getUser(@PathParam("id") String id) {
    	return userService.get(Long.parseLong(id));
    }
    
    /**
     * Dados os dados de um usuario adiciona um usuario no repositorio
     * @param user
     * @return código http
     * 
     * curl -v --header "Content-Type: application/json" --request POST --data '{"username":"novousuario", "password":"novousuario"}' http://localhost:8083/demo/users
     * 
     */
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response addUser(Users user) {
    	
    	String senhaCriptografada;
    	
    	if (user.getPassword().length() > 1){
        	senhaCriptografada = new GeradorSenha().criptografa(user.getPassword());
        	user.setPassword(senhaCriptografada);
            userService.save(user);
            URI uri = URI.create("/" + String.valueOf(user.getId()));
            return Response.created(uri).build();
    	}
    	
    	URI uri2 = URI.create("/"); 
    	
    	return Response.created(uri2).build();
    }
    
    /**
     * Dado um id e os dados do user faz sua atualizacao
     * @param id
     * @param user
     * @return código http
     */
    @PUT
    @Consumes("application/json")
    @Path("/{id}")
    public Response updateUser(@PathParam("id") String id, Users user) {
       userService.save(user);
       return Response.noContent().build();
    }


    /**
     * Dado um id de um usuario faz sua remocao do repositorio
     * @param id
     * @return código http
     */
    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") String id) {
        userService.delete(Long.parseLong(id));
        return Response.ok().build();
    }
    
    /**
     * Dado email e senha retorna o JSON dos dados do usuario
     * @param 
     * @return código http
     */
    @GET
    @Produces("application/json")
    @Path("/{email}/{senha}")
    public Object getUserAutenticado(@PathParam("email") String email,@PathParam("senha") String senha) {
    	Users user = userService.getUserByEmail(email);
    	Message message = new Message();
    	
    	//consulta o usuário por email e se existe retorna os dados do usuário
    	if (user != null) { //usuário existe
    		boolean checaSenha = new GeradorSenha().comparaSenhas(senha, user.getPassword());
        	if (senha.length() >0 && checaSenha){        		
                return user;	
        	}else{        		
        		message.setId(1);
        		message.setConteudo("Senha incorreta!");
                return message;	    		
        	}    	
    		
    	}else {
    		message.setId(2);
    		message.setConteudo("Usuário não existe!");
    		return message;
    	}    	
    }

    /**
     * Adiciona um amigo de forma birecional
     * @param idUser
     * @param idFriend
     * @return
     */
    @GET
    @Produces("application/json")
    @Path(value = "/{idUser}/add/friend/{idFriend}")
    public Object addFriend(@PathParam("idUser") long idUser, @PathParam("idFriend") long idFriend) {
    	Message message = new Message();
    	
    	Users user = this.userService.get(idUser);
    	Users friend = this.userService.get(idFriend);
    	
    	if (user.addIdFriend(friend)) {
    		this.userService.save(user);
    		if (friend.addIdFriend(user)){
    			this.userService.save(friend);	
    		}    
    		message.setId(3);
    		message.setConteudo("O amigo foi salvo com sucesso.");
    	}else {
    		message.setId(4);
    		message.setConteudo("O amigo já existe!!!!.");
    	}
    	
    	return message;	
    }
    
    /**
     * Dado um usuário logado lista os amigos dele
     * @param idUser
     * @param model
     * @return
     */
    @GET
    @Produces("application/json")
    @Path(value = "/{idUser}/list/friends")
    public List<Users> listFriends(@PathParam("idUser") long idUser) {    

		Users user = this.userService.get(idUser);
		List<Users> idFriends = user.getIdFriendsList();
		
		List<Users> listaAmigos = new LinkedList<Users>();
		
		for (Users id : idFriends) {
			listaAmigos.add(this.userService.get(id.getId()));
		}
        
        return listaAmigos;
    }
    
    /**
     * Dado um usuário logado, ele remove o amigo selecionado
     * @param idUser
     * @param idFriend
     * @param model
     * @param ra
     * @return
     */
    @GET
    @Produces("application/json")
    @Path(value = "/{idUser}/delete/friend/{idFriend}")
    public Object deleteFriend(@PathParam("idUser") long idUser, @PathParam("idFriend") long idFriend) {
    	Message message = new Message();
    	
    	Users user = this.userService.get(idUser);
    	Users friend = this.userService.get(idFriend);
    	
    	if (user.deleteFriend(friend)) {        	 
        	this.userService.save(user);
        	if(friend.deleteFriend(user)) {
        		this.userService.save(friend);
        	}
        	message.setId(5);
        	message.setConteudo("Amigo removido com sucesso!");
    	}else {
        	message.setId(6);
        	message.setConteudo("O amigo não foi removido.");
    	}
    	
    	return message;
    }
    
}