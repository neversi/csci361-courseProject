package hotel.repository;

import java.util.List;

import hotel.model.Reservation;
import hotel.model.Room;

public interface RoomsRepository extends ICRUDRepository<Room> {
    List<Room> getFreeRooms(Reservation reservation);
}
