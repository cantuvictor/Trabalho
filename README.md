---

# Criação e Implementação de Tipos Genéricos

## Descrição

Este projeto é uma aplicação Java desenvolvida como parte de uma atividade da disciplina de Desenvolvimento Web, cursada no quarto período do curso de Sistemas de Informação na Unimater. O principal objetivo é demonstrar a implementação de um sistema CRUD (Create, Read, Update, Delete) utilizando JDBC para gerenciar informações sobre tipos de produtos e vendas. A aplicação inclui um servidor HTTP simples e classes DAO (Data Access Object) para manipulação de dados no banco de dados.

## Estrutura do Projeto

### 1. `GenericDAOImpl`

A classe `GenericDAOImpl` é uma implementação genérica da interface DAO, que fornece operações básicas para qualquer entidade que implemente a interface `Entity`. Esta classe é responsável por gerenciar as operações CRUD para qualquer tipo de entidade de forma genérica.

#### Métodos Implementados:
- **`findById(int id)`**: Recupera uma entidade a partir do seu ID.
- **`upsert(T entity)`**: Insere uma nova entidade ou atualiza uma entidade existente.
- **`delete(int id)`**: Exclui uma entidade com base no seu ID.

### 2. Extensões da `GenericDAOImpl`

#### 2.1 `ProductDAO`

A classe `ProductDAO` herda de `GenericDAOImpl` e é especificamente responsável pelas operações CRUD para a entidade `Product`.

#### 2.2 `SaleDAO`

A classe `SaleDAO` também herda de `GenericDAOImpl` e gerencia as operações CRUD para a entidade `Sale`.

#### 2.3 `SaleItemDAO`

Da mesma forma, a classe `SaleItemDAO` herda de `GenericDAOImpl` e manipula as operações CRUD para a entidade `SaleItem`.

### 3. `App`

A classe `App` contém o método `main`, que é responsável por inicializar o servidor HTTP e estabelecer a conexão com o banco de dados. Ela também executa operações CRUD básicas utilizando as classes DAO.

#### Funcionalidades:
- Cria um servidor HTTP na porta 8080 com um contexto para a rota `/helloWorld`, que retorna a resposta "Hello World".
- Estabelece uma conexão com o banco de dados MySQL.
- Realiza operações CRUD (Create, Read, Update, Delete) nas tabelas `product_type`, `sale_type`, `sale_item_type`, e `product`.

### 4. Estrutura do Banco de Dados

O banco de dados `jdbc2608` utilizado pela aplicação contém as seguintes tabelas:

- **`product_type`**: Armazena informações sobre os tipos de produtos.
- **`product`**: Armazena informações sobre os produtos, com uma referência para `product_type`.
- **`sale`**: Registra as informações de vendas.
- **`sale_type`**: Armazena tipos de vendas.
- **`sale_item_type`**: Armazena tipos de itens de venda.
- **`sale_item`**: Registra os itens de venda com referências para as tabelas `product` e `sale`.

### 5. Scripts SQL

O projeto inclui um script SQL que cria o banco de dados, define as tabelas e insere dados de exemplo. Este script pode ser utilizado para configurar o ambiente de banco de dados necessário para a aplicação.

---