package hotel.repository.postgres;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.sql.*;

import hotel.model.Reservation;
import hotel.repository.ReservationRepository;

public class ReservationRepositoryPostgres extends PostgresCRUD<Reservation> implements ReservationRepository {
    
    public List<Reservation> getListFromDate(Reservation model) {
        ArrayList<Reservation> objects = new ArrayList<>();

        try{
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                String sql = "SELECT * FROM "  + model.tableName() + " WHERE hotel_id = ? and check_out >= ?";
                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    model.setPlaceHolders(preparedStatement);
                    ResultSet result = preparedStatement.executeQuery();
                    while(model.readResultSet(result)){
                        Reservation newModel = (Reservation) model.clone();
                        objects.add(newModel);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } 
        
        return objects;
    }
    
}
