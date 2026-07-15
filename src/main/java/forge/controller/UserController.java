package forge.controller;

import com.google.gson.Gson;
import forge.model.User;
import forge.service.UserService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class UserController implements HttpHandler {

    private final UserService userService;
    private final Gson gson = new Gson();

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Habilita CORS para o seu site conseguir fazer requisições para a API
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
        exchange.getResponseHeaders().add("Content-Type", "application/json");

        // Trata requisições de pre-flight (comum em navegadores)
        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            try {
                // 1. Lê o JSON enviado pelo front-end e transforma em objeto User
                InputStreamReader reader = new InputStreamReader(exchange.getRequestBody());
                User user = gson.fromJson(reader, User.class);

                // 2. Chama o serviço para cadastrar (com as validações que corrigimos!)
                User registeredUser = userService.registerUser(user);

                // 3. Devolve o usuário cadastrado (com o ID gerado) como JSON
                String jsonResponse = gson.toJson(registeredUser);
                sendResponse(exchange, 201, jsonResponse);

            } catch (IllegalArgumentException e) {
                // Se cair em qualquer uma das suas validações, devolve erro 400 (Bad Request)
                String errorJson = "{\"error\": \"" + e.getMessage() + "\"}";
                sendResponse(exchange, 400, errorJson);
            } catch (Exception e) {
                // Erros inesperados
                String errorJson = "{\"error\": \"Internal server error.\"}";
                sendResponse(exchange, 500, errorJson);
            }
        } else {
            // Método não permitido
            exchange.sendResponseHeaders(405, -1);
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String responseText) throws IOException {
        byte[] bytes = responseText.getBytes();
        exchange.sendResponseHeaders(statusCode, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
}