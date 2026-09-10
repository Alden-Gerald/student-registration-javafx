package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Database {

    private static final String DEFAULT_URL =
            "jdbc:mysql://localhost:3306/student_registration"
            + "?useSSL=false"
            + "&allowPublicKeyRetrieval=true"
            + "&serverTimezone=UTC";

    private Database() {
    }

    public static Connection getConnection() throws SQLException {
        String url = envOrDefault("STUDENT_DB_URL", DEFAULT_URL);
        String user = envOrDefault("STUDENT_DB_USER", "root");
        String password = envOrDefault("STUDENT_DB_PASSWORD", "");
        return DriverManager.getConnection(url, user, password);
    }

    private static String envOrDefault(String name, String fallback) {
        String value = System.getenv(name);
        return value == null || value.isBlank() ? fallback : value;
    }
}
