package hotel.repository;

import java.util.Optional;

import hotel.model.Hotel;

public interface HotelRepository extends ICRUDRepository <Hotel> {
    Optional<Hotel> getHotelByName(Hotel hotel) throws Exception;
}
