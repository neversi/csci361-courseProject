package hotel.repository.postgres;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import hotel.model.User;
import hotel.repository.UserRepository;

public class UserRepositoryPostgres extends PostgresCRUD<User> implements UserRepository {
    private static String url = "jdbc:postgresql://localhost:3006/postgres";
    private static String username = "postgres";
    private static String password = "postgres";

    public UserRepositoryPostgres() {
    }

    public Optional<User> getUserByName(User u) throws Exception {
        try{
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                String sql = "SELECT * FROM "  + u.tableName() + " WHERE email = ?";
                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    u.setPlaceHolders(preparedStatement);
                    ResultSet result = preparedStatement.executeQuery();
                    int count = 0;

                    while(u.readResultSet(result)) {
                        count += 1;
                    }
                    if (count == 0) {
                        return Optional.empty();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
            throw new Exception("UserRepositoryPostgres.getUserByName: " + e.toString());
        } 
        System.out.println(u);
        return Optional.of(u);
    }
}
