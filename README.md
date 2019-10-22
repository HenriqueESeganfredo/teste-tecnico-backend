# Teste Técnico Back-End Livelo

Projeto JAVA com Framework Spring Boot.

Listagem de APIs para efetuar os testes via Postman:

# Cidades:
* /cidades - Lista todas as Cidades. Método GET;
* /cidades/CadastrarCidade - Cadastro de Cidades. Método POST. Parâmetro(s): id(Integer), nome(String), estado(String);
* /cidades/RemoverCidade - Remove uma Cidade. Método DELETE. Parâmetro(s): id(Integer);
* /cidades/BuscarPorNome - Busca uma ou mais Cidades pelo nome. Método GET. Parâmetro(s): nome(String);
* /cidades/BuscarPorEstado - Busca uma ou mais Cidades pelo estado. Método GET. Parâmetro(s): estado(String).

# Clientes:
* /clientes - Lista todos os Clientes. Método GET;
* /clientes/CadastrarCliente - Cadastro de Clientes. Método POST. Parâmetro(s): id(Integer), nome(String), sexo(String), dataNascimento(String), idade(Integer), idCidade(Integer);
* /clientes/RemoverCliente - Remove um Cliente. Método DELETE. Parâmetro(s): id(Integer);
* /clientes/BuscarPorId - Busca um Cliente pelo Id. Método GET. Parâmetro(s): id(Integer);
* /clientes/BuscarPorNome - Busca um ou mais Clientes pelo nome. Método GET. Parâmetro(s): nome(String);
* /clientes/AlterarNome - Altera o nome de um Cliente. Método PATCH. Parâmetro(s): id(Integer), novoNome(String);
