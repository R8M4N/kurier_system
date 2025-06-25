import java.sql.*;

public class LoginManager {
    private static final String SERVER = "serwer2469155.home.pl";
    private static final String USERNAME = "38759356_studia";
    private static final String PASSWORD = "omijamdriftembiede888";
    private static final String DATABASE = "38759356_studia";
    private static final String PORT = "3380";
    
    private static final String URL = "jdbc:mysql://" + SERVER + ":" + PORT + "/" + DATABASE 
            + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    
    public LoginManager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Nie można załadować sterownika MySQL: " + e.getMessage());
        }
    }
    
    public User zaloguj(String login, String haslo) {
        if (login == null || haslo == null) {
            return null;
        }
        
        String query = "SELECT rola FROM kurier_users WHERE login = ? AND haslo = ?";
        
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, login.trim());
            stmt.setString(2, haslo);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String rola = rs.getString("rola");
                System.out.println("Zalogowano użytkownika: " + login + " jako " + rola);
                
                if ("admin".equals(rola)) {
                    return User.ADMIN;
                } else if ("kurier".equals(rola)) {
                    return User.KURIER;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Błąd połączenia z bazą danych: " + e.getMessage());
        }
        
        System.out.println("Nieprawidłowe dane logowania dla: " + login);
        return null;
    }
}