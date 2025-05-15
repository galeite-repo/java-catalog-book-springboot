# Catálogo do Sábio - API de Livros

API REST feita em Java com Spring Boot para gerenciar um catálogo de livros. Possui endpoints para listagem, busca por ID, autor ou gênero, e exibição dos gêneros distintos. Projeto com foco em performance, estrutura limpa e boas práticas de desenvolvimento.

---

## Arquitetura e Stack Técnica

### Estrutura da Aplicação

A aplicação segue o padrão em camadas:

- `Controller` → Recebe as requisições
- `Service` → Contém a lógica de negócio
- `Repository` → Comunicação com o banco

Cache com Redis foi adicionado em consultas de gênero para reduzir latência em chamadas repetidas.

### Tecnologias

- **Java 17**: linguagem principal
- **Spring Boot**: estrutura base do projeto
  - Spring Data JPA
  - Spring Security
  - Spring Cache (com Redis)
- **PostgreSQL**: banco relacional
- **Redis**: cache
- **Swagger/OpenAPI**: documentação da API
- **Docker / Docker Compose**
- **Java Faker**: geração de dados fake
- **JUnit 5 + Mockito**: testes unitários e integração

---

## Funcionalidades

- `GET /api/books`: lista todos os livros (com paginação)
- `GET /api/books/{id}`: busca um livro por ID
- `GET /api/books/genre?genre={genre}`: busca livros por gênero
- `GET /api/books/author?author={author}`: busca livros por autor
- `GET /api/books/genres`: retorna todos os gêneros distintos (**ADMIN**)

---

## Segurança

Autenticação básica configurada com dois perfis:

- **Usuário comum**
  - `user` / `user`
  - Acesso de leitura
- **Administrador**
  - `admin` / `admin`
  - Acesso total

---

## Regras e Lógica

- **Validações**: Exceções customizadas como `BookNotFoundException`
- **Geração de dados**: Banco populado com 7.000 livros fictícios no start do app
- **Cache com Redis**: Habilitado para busca por gênero
- **Entidade principal**: `BookEntity`
  - `id`, `title`, `author`, `description`, `genre`

---

## Melhorias Futuras

- Testes mais robustos (integração e carga)
- Paginação com ordenação e filtros dinâmicos
- Autenticação com OAuth2 / JWT
- Observabilidade com Prometheus + Grafana

---

## Desafios Encontrados

- Configuração do Redis com Spring Cache
- Geração de dados fake realistas
- Segurança e controle de acesso por perfil

---

## Como rodar o projeto

### Requisitos

- Docker e Docker Compose
- Java 17 + Maven (caso rode localmente sem container)

### Passos para rodar com Docker

```bash
docker-compose up --build

```

## Endpoints

- **API**: http://localhost:3000
- **Swagger**: http://localhost:3000/swagger-ui.html
    


