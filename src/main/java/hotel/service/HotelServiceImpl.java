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
        return new ArrayList<>(); 
    }

    public Optional<Hotel> getHotelByName(Hotel hotel) {
        return Optional.empty();
    }

    public void addHotel(Hotel hotel) {
        
    }
    
}
