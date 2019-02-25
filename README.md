# restapi
Serviço demo Rest API. 

Features
---

* Serviços de CRUD para Usuários
* Controle básico de autenticação

TODO
--- 

* Controle de permissão e perfil de usuários

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

curl --user armando:armando http://localhost:8083/demo/users/1

develove a seguinte resposta:

{"id":1,"username":"armando","password":"$2a$10$DN7O2a3TvO9M.lVHFZkOW.k395HX.OLNYE3dq2uXZ92P/2YwyXVM6","enabled":false,"email":"armando@ufpi.edu.br","latitude":0.0,"longitude":0.0,"name":null,"amountOfFriends":1}


Referências
---

[1] Maven. Management of Builds and Dependencies. Available at https://maven.apache.org

[2] Spring Boot 1. It is a Java Framework (based on the Spring Platform) for web applications that use inversion control container for the Java platform. Available at https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-security

[3] Jersey. It is an open source RESTful Web Services framework to facilitate development of RESTful Web services and their clients in Java, a standard and portable JAX-RS API. Availale at https://jersey.github.io 

[4] Spring Data JPA. Abstartion of data access. Available at https://docs.spring.io/spring-data/jpa/docs/current/reference/html

[5] Mysql 5. Database Management System. Available at https://dev.mysql.com/downloads/mysql

[6] Spring security. Available at https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/

Dúvidas, questionamentos ou sugestões enviar email para armando@ufpi.edu.br
