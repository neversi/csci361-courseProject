package hotel.repository.postgres;

import java.util.Optional;
import java.sql.*;

import hotel.model.Guest;
import hotel.repository.GuestRepository;

public class GuestRepositoryPostgres extends PostgresCRUD<Guest> implements GuestRepository {

    public UserRepositoryPostgres ur;

    public GuestRepositoryPostgres() {
        super();
        ur = new UserRepositoryPostgres();
    }
    
    public Optional<Guest> getByEmail(Guest guest) {

        try{
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                String sql = "SELECT * FROM "  + guest.tableName() + " WHERE email = ?";
                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    guest.setPlaceHolders(preparedStatement);
                    ResultSet result = preparedStatement.executeQuery();
                    int count = 0;

                    while(guest.readResultSet(result)) {
                        count += 1;
                    }
                    if (count == 0) {
                        return Optional.empty();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
            // throw new Exception("UserRepositoryPostgres.getUserByName: " + e.toString());
        } 
        
        return Optional.of(guest);
    }
}
