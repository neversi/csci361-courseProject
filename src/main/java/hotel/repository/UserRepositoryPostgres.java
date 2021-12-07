package hotel.repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import hotel.model.User;

public class UserRepositoryPostgres implements UserRepository {
    public PostgresCRUD<User> crud;

    private static String url = "jdbc:postgresql://localhost:3006/postgres";
    private static String username = "postgres";
    private static String password = "postgres";

    public UserRepositoryPostgres() {
        crud = new PostgresCRUD<>();
    }

    public Optional<User> getUserByName(User u) throws Exception {
        try{
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                String sql = "SELECT * FROM "  + u.getTable() + " WHERE username = ?";
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
        
        return Optional.of(u);
    }

    public List<User> getList(User model) { return crud.getList(model); }
    public User getById(User model) {return crud.getById(model); }
    public User update(User model) throws Exception { return crud.update(model); }
    public void delete(User model) throws Exception { crud.delete(model); }
    public User create(User model) throws Exception { return crud.create(model); }

}
