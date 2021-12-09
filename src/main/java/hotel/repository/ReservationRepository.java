package hotel.repository;

import java.time.LocalDate;
import java.util.List;

import hotel.model.Reservation;

public interface ReservationRepository extends ICRUDRepository<Reservation> {
    List<Reservation> getListFromDate(Reservation res);
}
