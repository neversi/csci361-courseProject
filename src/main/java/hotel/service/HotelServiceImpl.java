package hotel.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import hotel.model.Hotel;
import hotel.repository.HotelRepository;


public class HotelServiceImpl implements HotelService {
    
    public HotelRepository hr;

    public HotelServiceImpl(HotelRepository hr) {
        this.hr = hr;
    }

    public List<Hotel> listHotels() { 
        List<Hotel> hotels = hr.getList(new Hotel());

        return hotels;
    }

    public Optional<Hotel> getHotelByName(Hotel hotel) {
        if (hotel.hotel_name.equals("")) {
            return Optional.empty();
        }
        try {
            Optional<Hotel> newHotel = hr.getHotelByName(hotel);
            return newHotel;
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void addHotel(Hotel hotel) {
        if (hotel.hotel_name.isEmpty()) {
            return;
        }
        
        if (hotel.hotel_address.isEmpty() || hotel.hotel_name.isEmpty()) {

        }
    }
    
}
