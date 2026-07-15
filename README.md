# Forge 🚀

Backend de um blog simples desenvolvido em **Java Puro** (sem frameworks como Spring Boot). O objetivo do projeto é demonstrar a arquitetura em camadas, migrações de banco de dados com Flyway e a aplicação prática do padrão de projeto **Strategy** do GoF.

---

## 🛠️ Tecnologias e Bibliotecas

* **Linguagem:** Java 21
* **Gerenciador de Dependências:** Gradle (Kotlin DSL)
* **Banco de Dados:** MySQL
* **Migrações:** Flyway
* **Conversão JSON:** Google Gson
* **Produtividade:** Lombok

---

## 📐 Arquitetura do Projeto

O código está dividido em quatro camadas principais para isolar as responsabilidades:

* **Model (`forge.model`):** Classes que representam as entidades do sistema (`User` e `Post`).
* **Repository (`forge.repository`):** Comunicação direta com o banco de dados usando JDBC puro e `PreparedStatement` para proteção contra SQL Injection.
* **Service (`forge.service`):** Onde ficam as validações e regras de negócio (como checagem de formato de e-mail e força de senha).
* **Controller (`forge.controller`):** Criação do servidor HTTP nativo (`HttpServer`) e conversão de requisições JSON.

---

## 🎨 Design Pattern: Strategy

Para evitar o uso de condicionais de fluxo (`if/else`) ao exibir posts em formatos diferentes, foi implementado o padrão **Strategy** para renderização.

* **Interface:** `PostRenderStrategy`
* **HTML:** `HtmlRenderStrategy` (gera código formatado para exibição web).
* **Markdown:** `MarkdownRenderStrategy` (gera texto formatado para editores).

A troca de renderização ocorre dinamicamente em tempo de execução através do `PostService`.

---

## 🗄️ Modelagem do Banco de Dados

O banco de dados é gerenciado incrementalmente através de migrations do Flyway:

* **`users`**: Cadastro de usuários com id, nome, e-mail único, senha, universidade e curso.
* **`posts`**: Cadastro de posts vinculados obrigatoriamente a um autor (`user_id`) com chave estrangeira configurada com `ON DELETE CASCADE`.

---

## 🚀 Como Executar Localmente

### 1. Criar o Banco de Dados
No seu cliente SQL (como o DBeaver), crie o banco de dados localmente:
```sql
CREATE DATABASE forge_db;
```
### 2. Configurar as Credenciais
No arquivo `src/main/java/forge/config/DatabaseConfig.java`, insira as credenciais do seu MySQL:

```java
private static final String URL = "jdbc:mysql://localhost:3306/forge_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
private static final String USER = "seu_usuario";
private static final String PASSWORD = "sua_senha";
```

### 3. Executar o projeto
Rode o comando abaixo pelo terminal na pasta raiz do projeto:
```Bash
./gradlew run
```

O Flyway criará as tabelas de forma automática e o servidor iniciará escutando em http://localhost:8080.