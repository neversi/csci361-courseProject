package hotel.service;

import java.util.List;
import java.util.Optional;

import hotel.model.Hotel;

public interface HotelService {
    List<Hotel> listHotels();
    Optional<Hotel> getHotelByName(Hotel hotel);
    void addHotel(Hotel hotel) throws Exception;
}
