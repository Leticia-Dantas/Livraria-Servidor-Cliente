# Sistema de Gerenciamento de Livros com Socket em Java

## Visão Geral
Este projeto é um sistema simples de gerenciamento de livros implementado em Java 17, utilizando programação de sockets para comunicação cliente-servidor e JSON para armazenamento de dados.

## Pré-requisitos
- Java 17
- Biblioteca [json-simple-1.1.1.jar]

## Componentes
- **Servidor Socket**: Gerencia as solicitações do cliente para realizar operações no arquivo JSON.
- **Cliente Socket**: Envia solicitações ao servidor socket para interagir com os registros dos livros.
- **Classe Livro**: Facilita a criação de novos objetos livro para serem armazenados no arquivo JSON.
- **livros.json**: Um arquivo JSON que armazena os registros dos livros.

## Operações Disponíveis
1. **Listar Livros**: Recuperar e exibir uma lista de todos os livros.
2. **Alugar Livro**: Alugar um livro e declementa um exemplar.
3. **Devolver Livro**: Devolver um livro alugado e aumenta um exemplar .
4. **Cadastrar Livro**: Adicionar um novo livro ao sistema.
##**Nota**: Todas essas alterações afentam o arquivo JSON.

## Instruções de Uso
1. Inicie primeiro a aplicação Servidor Socket.
2. Execute a aplicação Cliente, escolha uma opção para realizar uma operação.
3. O Cliente executará a operação escolhida e depois terminará.
##**Nota**: O cliente não executa em loop por design; para realizar outra operação, será necessário reiniciar a aplicação Cliente.

## Como Executar
Certifique-se de ter o Java 17 instalado e de ter adicionado `json-simple-1.1.1.jar` ao seu classpath.


