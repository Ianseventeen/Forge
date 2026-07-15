package forge.config;

import org.flywaydb.core.Flyway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String URL = "jdbc:mysql://localhost:3306/forge_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "I@n060709";

    public static void initializeDatabase() {
        System.out.println("[Flyway] Verificando e aplicando migracoes do banco de dados...");

        try {
            Flyway flyway = Flyway.configure()
                    .dataSource(URL, USER, PASSWORD)
                    // Opcional: Garante que se o banco for criado do zero, ele aceite tabelas existentes se necessário
                    .baselineOnMigrate(true)
                    .load();

            flyway.migrate();
            System.out.println("[Flyway] Banco de dados atualizado com sucesso!");
        } catch (Exception e) {
            System.err.println("[Erro Crítico] Falha ao executar as migrações do Flyway: " + e.getMessage());
            // Em produção, se a migration falhar, o sistema deve parar imediatamente
            throw new RuntimeException("Falha na inicialização do banco de dados", e);
        }
    }

    /**
     * Metodo utilitário que seus Repositories vão usar para pegar uma conexão aberta com o MySQL.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
