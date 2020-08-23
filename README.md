#Desafio B2W

Instalação

-Para rodar a o projeto em seu ambiente, é preciso ter instalado o java 1.8,o Postman para fazer os testes de integração, o banco da dados local mongoDB, cujo o mesmo não precisa de usuario nem senha para conexão e são configuradas no arquivo "application.properties".

-As seguintes Tecnologias foram utilizadas no desenvolvimento do projeto:
•Maven 4.0.0 
•Java 1.8
•SpringBoot 2.3.2
•Mogodb 4.4.0
•Postman para os testes de integração

#Aplicação

Para executar a aplicação basta fazer download das dependencias usando o maven, e depois gerar um build da aplicação.

#USADO A API Rest

#Adicionando um novo planeta

Para adicionar um novo planeta deve-se enviar um POST para o caminho `localhost:8080/api/planetas`, no formato jason, cujo o mesmo deve está cadastrado na API Star War, e todos os campos são obrigatórios. E seu retonor é o registro criado com o status 201 e a quantidade de aparições em filmes.
{
    "nome": "<NOME>",
    "clima": "<CLIMA>",
    "terreno": "<TERRENO>"
}

#Listando todos os planetas

Para listar todos os planetas cadastrados deve-se enviar um GET para o caminho `localhost:8080/api/planetas`, e retorna uma listas com todos os registros encontrados com o status 200 e a quantidade de aparições em filmes.

#Buscando por nome

Para buscar umplanetas por nome, deve-se enviar um GET para o caminho `localhost:8080/api/planetas/por-nome?<NOME>` , onde <NOME> é o nome do planeta desejado. E retorna uma lista de planetas encontrados com status 200 e a quantidade de aparições em filmes.

#Buscando por ID

Para procurar por planetas por id, deve-se enviar um GET para o caminho `localhost:8080/api/planetas/<ID>`, onde <ID> é o id do planeta desejado. E retorna o planeta desejado com status 200 e a quantidade de aparições em filmes.

#Remover o planeta

Para remover um planeta, deve-se enviar um DELETE para o caminho `localhost:8080/api/planetas/delete/<ID>`, onde <ID> é o id do planeta desejado. E o mesmo não retorna nenhum dado com o status 204.
