# Catálogo do Sábio - API de Livros (Desafio)

API REST feita em Java com Spring Boot para gerenciar um catálogo de livros. Possui endpoints para listagem com paginação, listagem usando cachê com Redis, busca por ID, autor ou gênero, e exibição dos gêneros distintos. Projeto com foco em performance, estrutura limpa e boas práticas de desenvolvimento.

---

## Arquitetura e Stack Técnica

### Estrutura da Aplicação

A aplicação segue o padrão em camadas:

- `Controller` → Recebe as requisições
- `Service` → Contém a lógica de negócio
- `Repository` → Comunicação com o banco

Cache com Redis foi adicionado em consulta de todos os livros para reduzir latência em chamadas repetidas.

### Tecnologias

## Backend
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

## Frontend
- **Angular 19**: framework principal
  - Angular CLI
  - Angular Router
  - Angular Forms
- **TypeScript**: linguagem principal
- **Tailwind CSS + Flowbite**: estilização

---

## Funcionalidades

- `GET /api/books`: lista todos os livros (com paginação)
- `GET /api/books/all`: lista todos os livros (Redis)
- `GET /api/books/{id}`: busca um livro por ID
- `GET /api/books/genre?genre={genre}`: busca livros por gênero
- `GET /api/books/author?author={author}`: busca livros por autor
- `GET /api/books/genres`: retorna todos os gêneros distintos (**admin only**)

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
- Paginação com filtros dinâmicos
- Integração com Agent IA
- Autenticação com OAuth2 ou JWT
- Observabilidade com Prometheus + Grafana
- Página de Registro e Login

---

## Desafios Encontrados

- Configuração do Redis com Spring Cache
- Geração de dados fake realistas
- Frontend com Flowbite + Tailwind

---

## Como rodar o projeto

### Requisitos

- Docker e Docker Compose
- Java 17 + Maven (caso rode localmente sem container)
- NodeJS + Angular (caso rode localmente sem container)

### Passos para rodar com Docker

```bash
docker-compose up -d --build

```

## Endpoints

- **API**: http://localhost:3000
- **Swagger**: http://localhost:3000/swagger-ui.html
    

## Frontend
- **URL**: http://localhost:8080

