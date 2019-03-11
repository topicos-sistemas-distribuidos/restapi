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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.ufc.great.es.api.demo.exceptions.ServiceException;
import br.ufc.great.es.api.demo.model.Role;
import br.ufc.great.es.api.demo.model.Users;
import br.ufc.great.es.api.demo.service.AuthoritiesService;
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
	AuthoritiesService authoritiesService;
	
	@Autowired
	public void setUserService(UsersService userServices){
		this.userService = userServices;
	}
	
	@Autowired
	public void setAuthoritiesService(AuthoritiesService authoritiesService) {
		this.authoritiesService = authoritiesService;
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
     * @throws ServiceException 
     */
    @GET
    @Produces("application/json")
    @Path("/{id}")
    public Users getUser(@PathParam("id") String id) throws ServiceException{    	
    	Users user = null; 

    	user = userService.get(Long.parseLong(id));
    	
    	if (user==null) {
    		throw new ServiceException(HttpStatus.NOT_FOUND.value(), "Usuário não existe!", 1);
    	}

    	return (user);
    }
    
    /**
     * Dados os dados de um usuario adiciona um usuario no repositorio
     * @param user
     * @return código http
     * 
     * curl -v --header "Content-Type: application/json" --request POST --data '{"username":"novousuario", "password":"novousuario"}' http://localhost:8083/demo/users
     * @throws ServiceException 
     * 
     */
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response addUser(Users user) throws ServiceException {
    	
    	String senhaCriptografada;
    	Users userAux = userService.getUserByUserName(user.getUsername()); 
    	
    	//checa se o usuário já existe
    	if (userAux != null) {
    		throw new ServiceException(HttpStatus.BAD_REQUEST.value(), "Usuário já existe!", 1);
    	}
    	
    	if (user.getPassword().length() > 1){
        	senhaCriptografada = new GeradorSenha().criptografa(user.getPassword());
        	user.setPassword(senhaCriptografada);
            userService.save(user);
            URI uri = URI.create("/" + String.valueOf(user.getId()));
            return Response.created(uri).build();
    	}else{
    		throw new ServiceException(HttpStatus.BAD_REQUEST.value(), "Informe uma senha para o usuário", 1);
    	}
    }
    
    /**
     * Dado um id e os dados do user faz sua atualizacao
     * @param id
     * @param user
     * @return código http
     * @throws ServiceException 
     */
    @PUT
    @Consumes("application/json")
    @Path("/{id}")
    public Response updateUser(@PathParam("id") String id, Users user) throws ServiceException {
    	Users userAux = null; 
    	userAux = userService.get(Long.parseLong(id));

    	if (userAux==null) {
    		throw new ServiceException(HttpStatus.NOT_FOUND.value(), "Usuário não existe!", 1);
    	}	

    	userService.save(user);
    	return Response.noContent().build();
    }

    /**
     * Dado um id de um usuario faz sua remocao do repositorio
     * @param id
     * @return código http
     * @throws ServiceException 
     */
    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") String id) throws ServiceException{
    	Users user = userService.get(Long.parseLong(id));
    	
    	if (user == null){
    		throw new ServiceException(HttpStatus.NOT_FOUND.value(), "Usuário " + id + " não existe!",1);
    	}

    	userService.delete(Long.parseLong(id));
        return Response.ok().build();
    }
    
    /**
     * Dado email e senha retorna o JSON dos dados do usuario
     * @param 
     * @return código http
     * @throws ServiceException 
     */
    @GET
    @Produces("application/json")
    @Path("/{email}/{senha}")
    public Object getUserAutenticado(@PathParam("email") String email,@PathParam("senha") String senha) throws ServiceException {
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
    		throw new ServiceException(HttpStatus.NOT_FOUND.value(), "Usuário não existe!",1);
    	}    	
    }

    /**
     * Adiciona um amigo de forma birecional
     * @param idUser
     * @param idFriend
     * @return
     * @throws ServiceException 
     */
    @GET
    @Produces("application/json")
    @Path(value = "/{idUser}/add/friend/{idFriend}")
    public Object addFriend(@PathParam("idUser") long idUser, @PathParam("idFriend") long idFriend) throws ServiceException {
    	Message message = new Message();
    	
    	Users user = this.userService.get(idUser);
    	Users friend = this.userService.get(idFriend);
    	
    	//checa se user e friend existem 
    	checkUsers(user, friend);
    	
    	if (user.addFriend(friend)) {
    		this.userService.save(user);
    		if (friend.addFriend(user)){
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
     * Dado um usuário logado, ele remove o amigo selecionado
     * @param idUser
     * @param idFriend
     * @param model
     * @param ra
     * @return
     * @throws ServiceException 
     */
    @GET
    @Produces("application/json")
    @Path(value = "/{idUser}/delete/friend/{idFriend}")
    public Object deleteFriend(@PathParam("idUser") long idUser, @PathParam("idFriend") long idFriend) throws ServiceException {
    	Message message = new Message();
    	
    	Users user = this.userService.get(idUser);
    	Users friend = this.userService.get(idFriend);
    	
    	//checa se user e friend existem 
    	checkUsers(user, friend);
    	
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
 
    /**
     * Checa a validade de usuário e amigo
     * @param user
     * @param friend
     * @throws ServiceException
     */
	private void checkUsers(Users user, Users friend) throws ServiceException {
		if (user == null) {
    		if (friend == null) {
    			throw new ServiceException(HttpStatus.NOT_FOUND.value(), "Usuário e amigo não existem!",2);
    		}else {
    			throw new ServiceException(HttpStatus.NOT_FOUND.value(), "Usuário não existe!",1);
    		}    		
    	}else {
    		if (friend == null) {
    			throw new ServiceException(HttpStatus.NOT_FOUND.value(), "Usuário amigo não existe!",2);
    		}else{
    			if (user.getId() == friend.getId()) {
    				throw new ServiceException(HttpStatus.NOT_FOUND.value(), "Usuário não pode ser amigo dele mesmo",3);
    			}
    		}
    	}
	}

	/**
	 * Verifica as permissões do usuário
	 */
	private void checkUser() {
		// TODO Auto-generated method stub
		
	}
	
	 /**
     * Dado um usuário logado lista os amigos dele
     * @param idUser
     * @param model
     * @return
     * @throws ServiceException 
     */
    @GET
    @Produces("application/json")
    @Path(value = "/{idUser}/list/friends")
    public List<Users> listFriends(@PathParam("idUser") long idUser) throws ServiceException {    

		Users user = this.userService.get(idUser);
		
		if (user == null) {
			throw new ServiceException(HttpStatus.NOT_FOUND.value(), "Usuário não existe!",1);
		}
		
		List<Users> idFriends = user.getFriendsList();
		
		List<Users> listaAmigos = new LinkedList<Users>();
		
		for (Users id : idFriends) {
			listaAmigos.add(this.userService.get(id.getId()));
		}
        
        return listaAmigos;
    }
	
    /**
     * Seleciona uma imagem de um usuario
     * @param idUser id do usuario
     * @param model
     * @return um formulario para fazer o upload de uma imagem do perfil do usuario
     */
    @GET
    @Produces("application/json")
    @Path(value = "/{idUser}/select/image")
	public String selectImage(@PathParam(value = "idUser") Long idUser){
		Users editUser = userService.get(idUser);
		checkUser();
		//TODO select image
        return "users/formImage";
	}

	/**
	 * TODO é preciso zerar a sessão do usuário
	 * Return registration form template
	 * @param model
	 * @param user novo usuario que sera registrado
	 * @return formulario para registro de um novo usuario. Um novo usuario recebe a permissao padrao USER.
	 */
    @GET
    @Produces("application/json")
	@Path(value = "/register")
	public String showRegistrationPage(){
		//TODO fazer procedimento de registro de novo usuário		
		return "/register";
	}

	//TODO Revisar a forma de registra das permissões do usuário
    @GET
    @Produces("appliaction/json")
    @Consumes("application/json")
	@Path(value = "/register")
	public String processRegistrationForm(Users user, @PathParam("password") String password, 
			@PathParam("confirmpassword") String confirmPassword, @PathParam("authority") String authority) {
		
		String username = user.getUsername();
		Users userExists = this.userService.getUserByUserName(username);
		
		if (userExists != null) {			
			//TODO ("msgerro", "Usuário já existe!");
			return "redirect:/register";
		}else {	
			//checa a senha do usuário 			
			if (password.equals(confirmPassword)) {
			  	String senhaCriptografada = new GeradorSenha().criptografa(password);

			  	user.setPassword(senhaCriptografada);
				user.setEnabled(true);				
				Role authorities = new Role();	
				
				//checa o tipo do usuário
				if (authority.equals("USER")) {				
					authorities = authoritiesService.getRoleByNome("USER");
					List<Role> roles = new LinkedList<>();
					roles.add(authorities);
					user.setRoles(roles);
					this.userService.save(user);
					//TODO ("msg", "Usuário comum registrado com sucesso!");
					return "/login";				
				}
				
				//checa o tipo do usuário
				if (authority.equals("LOJISTA")) {			
					authorities = authoritiesService.getRoleByNome("STOREOWNER");
					List<Role> roles = new LinkedList<>();
					roles.add(authorities);
					user.setRoles(roles);
					this.userService.save(user);					
					//TODO ("msg", "Usuário lojista registrado com sucesso!");
					return "/login";				
				}	        	
			}else {
				//TODO ("msgerro", "Senha não confere!");
				return "redirect:/register";
			}			
			
		}
		return "/login";
	}
    
}