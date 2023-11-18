# Para rodar o projeto

```
1-Clone o projeto.
2-Entre na pasta backend.
3-Crie a network do docker que será utilizada para comunicação entre os containers.
4-docker network create jogodistribuido_network --subnet=192.168.0.0/16
5-Será necessário fazer um pull das imagens utilizadas no projeto.
6-docker compose pull
7-A partir dessa etapa, o projeto já pode ser executado.
8-Para rodar o projeto tem que ser feito em duas etapas, primeiro subir os containers referente ao banco de dados, depois a aplicação.
9-docker compose up management1 mysqlNode1 mysqlNode2 mysqlServer -d
10-Espere aproximadamente 20 segundos para que o banco de dados esteja pronto para receber conexões.
10- docker compose up 
11-Após isso, o front end ja pode ser rodado, basta ir na pasta frontend e rodar o comando npm start


```
