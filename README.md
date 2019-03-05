# restapi
Serviço demo Rest API. 

Estrutura geral da aplicação

app <br/>
|-Application (1) <br/>
|-JerseyConfiguration (2) <br/>
|-exception (3) <br/>
	--GenericExceptionMapper <br/>
|-model (4) <br/>
	--User <br/>
	--AbstractModel <br/>
|-repository (5) <br/>
	--UserRepository <br/>
|-service (6) <br/>
	--UserService <br/>
	--AbstractService <br/>
|-controller (7) <br/>
	--UserController <br/>
|-security (8) <br/>
	--WebSecurityConfig <br/>
	--autentication <br/>
		---AuthenticationEntryPoint <br/>
|-utils (9) <br/>
<br/>
<br>
pom.xml <br/> 
|-spring-boot-starter-undertow <br/>
|-spring-boot-starter-jersey <br/>
|-spring-boot-starter-data-rest <br/>
|-spring-boot-starter-data-jpa <br/>
|-spring-boot-starter-security <br/>
|-spring-security-test <br/>
|-spring-boot-starter-test <br/>
|-mysql-connector-java <br/>
<br/>
<br/>
1)Criada uma aplicação Spring boot padrão.

2)Criada a classe JerseyConfiguration para registrar os controladores.

3)Criado o GenericExceptionMapper para mapear as exceções encontradas durante as requisições.

4)Criada a classe base modelo

5)Criada a interface Repository que funcionará como o repositório da classe modelo

6)Criada a classe Service que funcionará como um serviço que vai manipular os dados do repositório

7)Criada a classe Controller que será o controlador com as funcionalidades que irão manipular o domínio do modelo

8)Configuração da segurança da aplicação <br/>
a)Criada a classe WebSecurityConfig que será responsável pelo controle de acesso as requisições da aplicação. <br/>
b)Criada a classe AuthenticationEntryPoint para implementar a autenticação básica via controle de cabeçalho passando uma chave de acesso nas requisições. <br/>

9)Classes utilitárias para apoiar a aplicação

Features
---

* Serviços de CRUD para Usuários
* Controle básico de autenticação

Sobre as operações para execução da aplicação
---

1. Faça o clone do repositório.

2. Crie o banco demo.
```
mysql> create database demo
```

3. Rode o script restaura-banco.sql para criar as tabelas com os dados de exemplo.
```
mysql> source scripts/sql/restaura-banco.sql
```

4. Limpe o projeto via comando clean do maven.
```
$mvn clean
```

5. Compile o projeto via modo teste do maven. 
```
$mvn test
```

6. Execute a classe principal (BackendApplication) do projeto via maven. 
```
$mvn spring-boot:run
```

7. Exemplo de chamada de teste:
```
$curl --user armando:armando http://localhost:8083/demo/users/1
```

develove a seguinte resposta:

```json
{"id":1,"username":"armando","password":"$2a$10$DN7O2a3TvO9M.lVHFZkOW.k395HX.OLNYE3dq2uXZ92P/2YwyXVM6","enabled":true,"email":"armando@ufpi.edu.br","latitude":0.0,"longitude":0.0,"roles":[{"nome":"ROLE_ADMIN","authority":"ROLE_ADMIN"}],"name":"Armando Soares Sousa,"amountOfFriends":1,"authorities":[{"nome":"ROLE_ADMIN","authority":"ROLE_ADMIN"}],"accountNonExpired":true,"accountNonLocked":true,"credentialsNonExpired":true}
```

Referências
---

[1] Maven. Management of Builds and Dependencies. Available at https://maven.apache.org

[2] Spring Boot 1. It is a Java Framework (based on the Spring Platform) for web applications that use inversion control container for the Java platform. Available at https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-security

[3] Jersey. It is an open source RESTful Web Services framework to facilitate development of RESTful Web services and their clients in Java, a standard and portable JAX-RS API. Availale at https://jersey.github.io 

[4] Spring Data JPA. Abstartion of data access. Available at https://docs.spring.io/spring-data/jpa/docs/current/reference/html

[5] Mysql 5. Database Management System. Available at https://dev.mysql.com/downloads/mysql

[6] Spring security. Available at https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/

Dúvidas, questionamentos ou sugestões enviar email para armando@ufpi.edu.br
