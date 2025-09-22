# SistemaDeEstoque

Sistema de Estoque
Descrição do Projeto
Este projeto é um sistema simples de gerenciamento de estoque desenvolvido para fins de estudo, utilizando o framework Spring Boot no backend e um frontend construído com HTML, CSS e JavaScript. Ele permite o cadastro de produtos, armazenamento de dados em um banco de dados MySQL e a visualização desses produtos.

Tecnologias Utilizadas
O projeto foi construído utilizando as seguintes tecnologias:

Backend
Java 21

Spring Boot 3.5.5

Spring Boot Starter Web: Para o desenvolvimento de aplicações web e APIs RESTful.

Spring Boot Starter Thymeleaf: Motor de templates para o frontend.

Spring Boot Starter Data JPA: Para a persistência de dados.

Spring Boot Starter Security: Para segurança da aplicação.

MySQL Connector/J: Driver de conexão com o banco de dados MySQL.

Lombok: Para reduzir o código clichê (getters, setters, etc.).

Frontend
HTML5

CSS3

JavaScript

Estrutura de Pastas
A estrutura de pastas do projeto segue a convenção do Spring Boot, separando as partes do backend (Java) do frontend (recursos estáticos e templates).

.
└── SistemaDeEstoque/

    ├── src/
    │   ├── main/
    │   │   ├── java/
    │   │   │   └── com/br/SistemaDeEstoque/
    │   │   │       ├── controller/
    │   │   │       │   └── ProdutoController.java
    │   │   │       ├── model/
    │   │   │       │   └── Produto.java
    │   │   │       ├── repository/
    │   │   │       │   └── ProdutoRepository.java
    │   │   │       ├── service/
    │   │   │       │   └── ProdutoService.java
    │   │   │       └── SistemaDeEstoqueApplication.java
    │   │   └── resources/
    │   │       ├── static/
    │   │       │   ├── css/
    │   │       │   │   └── style.css
    │   │       │   └── js/
    │   │       │       └── script.js
    │   │       ├── templates/
    │   │       │   ├── form.html
    │   │       │   ├── index.html
    │   │       │   └── lista.html
    │   │       └── application.properties
    │   └── test/
    ├── .gitignore
    ├── pom.xml
    └── README.md
Como Executar o Projeto
Para executar o projeto localmente, siga os passos abaixo.

1. Configurar o Banco de Dados
O projeto utiliza um banco de dados MySQL. Você deve criar um banco de dados com o nome estoque para que a aplicação consiga se conectar.

SQL

CREATE DATABASE estoque;
2. Configurar o CORS (Cross-Origin Resource Sharing)
Como o frontend e o backend rodam em portas diferentes, você precisa configurar a política de CORS para evitar erros de conexão no navegador.

No arquivo ProdutoController.java, adicione a anotação @CrossOrigin na classe, especificando a origem do seu frontend (neste caso, http://127.0.0.1:5500):

Java

import org.springframework.web.bind.annotation.CrossOrigin;
// ... outras importações

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "http://127.0.0.1:5500") // Adicione esta linha
public class ProdutoController {

        @PostMapping("/produtos")
    public String adicionar(@ModelAttribute("novoProduto") Produto p,
            @AuthenticationPrincipal UserDetails auth) {
        User u = users.findByUsername(auth.getUsername()).orElseThrow();
        p.setUsuario(u); 
        p.setDataRegistro(java.time.LocalDate.now());
        produtos.salvarParaUsuario(p, u);
        return "redirect:/dashboard";
    }
    
}
3. Executar a Aplicação Spring Boot
Após configurar o banco de dados e o CORS, você pode iniciar a aplicação.

Abra seu IDE (como o VS Code ou IntelliJ).

Navegue até o arquivo principal da aplicação: src/main/java/com/br/SistemaDeEstoque/SistemaDeEstoqueApplication.java.

Execute o método main. A aplicação irá iniciar e o console mostrará que o servidor Tomcat está rodando na porta 8080.
