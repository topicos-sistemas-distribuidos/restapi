package br.ufc.great.es.api.demo.controller;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import br.ufc.great.es.api.demo.model.Role;
import br.ufc.great.es.api.demo.model.Users;
import br.ufc.great.es.api.demo.service.AuthoritiesService;
import br.ufc.great.es.api.demo.service.UsersService;

/**
 * Faz o controle do domínio de Controle de Acesso
 * @author armandosoaressousa
 *
 */
@Controller
public class AccessControlController {
	
	private AuthoritiesService authoritiesService;
	private UsersService userService;

	@Autowired
	public void setAuthoritiesService(AuthoritiesService authoritiesService) {
		this.authoritiesService = authoritiesService;
	}
	
    @Autowired
    public void setUserService(UsersService userService) {
    	this.userService = userService;
    }
	
    /**
     * Checa as permissões do usuário que requisitou o recurso
     */
	private void checkUser() {
		//TODO
	}
	
	/**
	 * Lista as permissões cadastradas
	 * @param model
	 * @return página com a lista de permissões cadastradas
	 */
	@GET
	@Produces("application/json")
	@Path(value="/accesscontrol")
    public String index(Model model) {
    	List<Role> authoritiesList = this.authoritiesService.getListAll();
    	checkUser();
    	
    	//TODO
        return "accesscontrol/list";
    }
    
	/**
	 * Lista os usuários cadastrados no sistema
	 * @param model
	 * @return página contendo os usuários do sistema
	 */
	@GET
	@Produces("application/json")
	@Path(value="/accesscontrol/users")
	public String listUsers(){
    	List<Users> list = userService.getListAll();
    	checkUser();
    	
    	//TODO
		
		return "accesscontrol/users";
	}

    /**
     * Edita as permissões do usuário selecionado
     * @param id
     * @param model
     * @return formulário dos dados do usuário com a edição de sua permissão no sistema
     */
	@GET
	@Produces("application/json")
    @Path(value="/accesscontrol/users/edit/{id}")
    public String editUserAuthority(@PathVariable Long id) {
		Users editUser = userService.get(id);
		checkUser();
		
		//TODO    	
        return "accesscontrol/formAuthority";
    }
    
    /**
     * Salva as alterações do usuário editado
     * @param user novos dados do usuário
     * @param nome permissao selecionada
     * @param ra redireciona os atributos
     * @return página que lista todos os usuários
     */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
    @Path(value = "/accesscontrol/users/saveedited")
    public String saveEdited(Users user, @RequestParam("nome") String authority) { 
    	Users userEdited = this.userService.get(user.getId());
    	List<Users> friends = userEdited.getFriendsList();    	
    	List<Role> roles = new LinkedList<>();		
		     	
    	switch (authoritiesService.checkAuthority(authority)) {
		case "USER":
			roles.add(authoritiesService.getRoleByNome("USER"));
			userEdited.setRoles(roles);
			break;
		case "STOREOWNER":
			roles.add(authoritiesService.getRoleByNome("STOREOWNER"));
			roles.add(authoritiesService.getRoleByNome("USER"));
			userEdited.setRoles(roles);
			break;
		default:
			//TODO ra.addFlashAttribute("successFlash", "A permissão não está registrada no sistema!");
			break;
		}
    	
    	userEdited.setFriendsList(friends);
		this.userService.save(userEdited);					
        //TODO ("successFlash", "As permissões do Usuário " + userEdited.getUsername() + " foram alteradas com sucesso.");
          				
        return "redirect:/accesscontrol/users";
    }
    
	/**
     * Adiciona uma nova permissão a um usário existente
     * @param model
     * @return formulário para preencher os dados da nova permissão
     */
	@GET
	@Produces("application/json")
    @Path(value="/accesscontrol/add")
    public String add() {
    	checkUser();
    	
    	//TODO 
        return "accesscontrol/form";
    }

    /**
     * Salva a permissão selecionada
     * @param authorities
     * @param ra
     * @return volta para a página de lista de permissões
     */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
    @Path(value = "/accesscontrol/save")
    public String save(Role authorities) {   	  
    	
    	Role save = authoritiesService.save(authorities);
    	//TODO ("successFlash", "Permissão salva com sucesso.");

    	return "redirect:/accesscontrol";	
    }
    
    /**
     * TODO: Revisar a operação de edicão de permissão
     * Edita a permissão selecionada
     * @param id
     * @param model
     * @return
     */
	@GET
	@Produces("application/json")
    @Path(value="/accesscontrol/edit/{id}")
    public String edit(@PathVariable Long id) {
    	checkUser();
    	
    	//TODO 
        return "accesscontrol/formEdit";
    }

}