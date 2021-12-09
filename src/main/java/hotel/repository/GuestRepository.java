package hotel.repository;

import java.util.Optional;

import hotel.model.Guest;

public interface GuestRepository extends ICRUDRepository<Guest> {
    Optional<Guest> getByEmail(Guest guest);
}
