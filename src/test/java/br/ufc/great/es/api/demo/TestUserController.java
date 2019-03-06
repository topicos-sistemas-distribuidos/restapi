package br.ufc.great.es.api.demo;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;
import java.util.Base64;
import org.junit.Before;
import org.junit.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class TestUserController {
	int port;
	String usuarioEsenha;		
	String campoBase64Enconding;

	@Before
	public void setUp() {
		RestAssured.port = 8083;
		usuarioEsenha = "armando:armando";
		campoBase64Enconding = Base64.getEncoder().encodeToString(usuarioEsenha.getBytes());
	}
	
	@Test
	public void testGetUser() {
		JsonPath path = given()
                .header("Accept", "application/json")
                .header("Authorization", "Basic " + campoBase64Enconding)
                .get("/demo/users/1")
                .andReturn().jsonPath();

		int userId = path.getInt("id"); 
		String userFullName = path.getString("name");
		String userName = path.getString("username"); 
		String userEmail = path.getString("email");
		
		assertEquals(1, userId);
		assertEquals("Armando Soares Sousa", userFullName);
		assertEquals("armando", userName);
		assertEquals("armando@ufpi.edu.br", userEmail);	
	}
	
	@Test
	public void testGetAllUsers() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUserAutenticado() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddFriend() {
		fail("Not yet implemented");
	}

	@Test
	public void testListFriends() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteFriend() {
		fail("Not yet implemented");
	}

}
