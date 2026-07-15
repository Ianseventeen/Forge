package forge;

import forge.config.DatabaseConfig;
import forge.controller.UserController;
import forge.model.Post;
import forge.model.User;
import forge.repository.UserRepository;
import forge.service.PostService;
import forge.service.UserService;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import forge.strategy.HtmlRenderStrategy;
import forge.strategy.MarkdownRenderStrategy;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("=== [FORGE] Inicializando a Aplicacao ===");

        // 1. Roda as migrações do banco
        DatabaseConfig.initializeDatabase();

        // 2. Instancia as camadas na ordem correta
        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        UserController userController = new UserController(userService);

        // 3. Inicializa o servidor HTTP na porta 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Associa a rota /api/users ao nosso controlador de usuários
        server.createContext("/api/users", userController);

        server.setExecutor(null);
        server.start();

        System.out.println("=== [FORGE] Servidor rodando em http://localhost:8080/api/users ===");

        // 0. Criamos um usuário de exemplo
        User user = User.builder()
                .name("Ian")
                .email("ian@email.com")
                .password("123456")
                .university("UERJ")
                .course("Ciência da Computação")
                .build();

        userService.registerUser(user);

        // 1. Criamos um post de exemplo
        Post samplePost = Post.builder()
                .id(1L)
                .title("My First Design Pattern!")
                .author("Ian")
                .content("Today I learned the Strategy pattern in Java. It is awesome!")
                .userId(user.getId())
                .build();

        // 2. Instanciamos o serviço
        PostService postService = new PostService();

        System.out.println("=== TESTING STRATEGY PATTERN ===\n");

        // 3. Testando a estratégia HTML
        String htmlOutput = postService.viewPost(samplePost, new HtmlRenderStrategy());
        System.out.println("--- HTML OUTPUT ---");
        System.out.println(htmlOutput);
        System.out.println();

        // 4. Testando a estratégia Markdown
        String markdownOutput = postService.viewPost(samplePost, new MarkdownRenderStrategy());
        System.out.println("--- MARKDOWN OUTPUT ---");
        System.out.println(markdownOutput);
        server.stop(0);

    }
}