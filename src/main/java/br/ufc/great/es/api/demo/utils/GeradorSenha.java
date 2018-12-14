package br.ufc.great.es.api.demo.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Classe que apoia a manipulação de codificação de senhas da aplicação
 * @author armandosoaressousa
 */
public class GeradorSenha {
	
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();  
	
	/**
	 * Criptografa senha baseeada no padrão BCryptPasswordEncoder
	 * @param valor senha original
	 * @return senha criptografada
	 */
	public String criptografa(String valor){
		return encoder.encode(valor);
	}
	
	/**
	 * Compara a senha criptografada do banco com a senha passada
	 * @param password senha passada
	 * @param senhaCriptografada senha cripgrafada guardada no banco
	 * @return
	 */
	public boolean comparaSenhas(String password, String senhaCriptografada){
		return encoder.matches(password, senhaCriptografada);
	}

}