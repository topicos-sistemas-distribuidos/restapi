# restapi
Demo Rest API

1. Crie o banco demo

2. Popule o banco demo

3. Comandos para rodar a aplicação
$mvn clean 
$mvn test
$mvn spring-boot:run

4. Comando para fazer um teste simples de autenticação
$curl --user armando:armando http://localhost:8083/demo/users/1

Deverá vir a seguinte resposta: 
{"id":1,"username":"armando","password":"$2a$10$DN7O2a3TvO9M.lVHFZkOW.k395HX.OLNYE3dq2uXZ92P/2YwyXVM6","enabled":false,"email":"armando@ufpi.edu.br","latitude":0.0,"longitude":0.0,"name":null,"amountOfFriends":1}not-grt-0150:restapi
