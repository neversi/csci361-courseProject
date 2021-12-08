package hotel.repository.postgres;

import java.sql.*;
import java.util.Optional;


import hotel.model.Hotel;
import hotel.repository.HotelRepository;

public class HotelRepositoryPostgres extends PostgresCRUD<Hotel> implements HotelRepository {
    public Optional<Hotel> getHotelByName(Hotel hotel) throws Exception {
        try{
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                String sql = "SELECT * FROM "  + hotel.tableName() + " WHERE " + "hotel_name = ?";
                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    hotel.setPlaceHolders(preparedStatement);
                    ResultSet result = preparedStatement.executeQuery();
                    if(hotel.readResultSet(result)) {
                        System.out.println("FOIND");
                        return Optional.of(hotel);
                    }
                    return Optional.empty();
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
            throw new Exception("HotelRepositoryPostgres.getHotelByName: " + e.toString());
        }
    }
}