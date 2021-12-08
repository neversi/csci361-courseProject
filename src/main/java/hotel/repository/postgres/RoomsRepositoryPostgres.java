package hotel.repository.postgres;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import hotel.model.Reservation;
import hotel.model.Room;
import hotel.repository.RoomsRepository;

public class RoomsRepositoryPostgres extends PostgresCRUD<Room> implements RoomsRepository {
    public List<Room> getFreeRooms(Reservation reservation) {
        List<Room> rooms = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                String sql = "SELECT R.* FROM Room R WHERE R.hotel_id = ? AND NOT EXISTS (SELECT R.room_number FROM  Reservation RE WHERE RE.room_number = R.room_number " +
                                "AND RE.hotel_id = R.hotel_id AND ((RE.check_out > ? AND RE.check_in <= ?) OR (RE.check_out >= ? AND RE.check_in < ?)));";
                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setInt(1, reservation.hotel_id);
                    preparedStatement.setDate(2, java.sql.Date.valueOf(reservation.check_in));
                    preparedStatement.setDate(3, java.sql.Date.valueOf(reservation.check_in));
                    preparedStatement.setDate(4, java.sql.Date.valueOf(reservation.check_out));
                    preparedStatement.setDate(5, java.sql.Date.valueOf(reservation.check_out));
                    ResultSet resultSet = preparedStatement.executeQuery();
                    Room cR = new Room();
                    while(cR.readResultSet(resultSet)) {
                        Room newR = (Room) cR.clone();
                        rooms.add(newR);
                    }
                    return rooms;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return rooms;
    }
}
