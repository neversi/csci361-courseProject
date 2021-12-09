package hotel.service;

import java.util.ArrayList;
import java.util.List;

import hotel.model.Hotel;
import hotel.model.Reservation;
import hotel.model.Room;
import hotel.model.dto.RoomReservationDateDTO;
import hotel.model.dto.RoomsDTO;
import hotel.repository.HotelRepository;
import hotel.repository.RoomsRepository;
import hotel.repository.postgres.HotelRepositoryPostgres;

public class RoomService {
    public RoomsRepository rr;
    public HotelRepository hr;

    public RoomService (RoomsRepository rr) {
        this.rr = rr;
        this.hr = new HotelRepositoryPostgres();
    }

    public List<RoomsDTO> getAllRooms(int hotelID) {
        Hotel hotel = new Hotel();
        return new ArrayList<>();
    }
    

    public List<RoomsDTO> getFreeRooms(RoomReservationDateDTO rDTO) {
        List<RoomsDTO> rooms = new ArrayList<>();
        Reservation reservation = new Reservation();
        reservation.check_in = rDTO.cin;
        reservation.check_out = rDTO.cout;
        reservation.hotel_id = rDTO.hotel_id;

        try {
            Hotel hotel = new Hotel();
            hotel.hotel_id = reservation.hotel_id;
            hotel = hr.getById(hotel);
            if (hotel == null) {
                return new ArrayList<>();
            }

            List<Room> availableRooms = rr.getFreeRooms(reservation);
            
            for (Room r : availableRooms) {
                RoomsDTO freeRDTO = new RoomsDTO();
                freeRDTO.floor = r.flr;
                freeRDTO.type = r.room_type;
                freeRDTO.room_number = r.room_number;
                rooms.add(freeRDTO);
            }
            return rooms;
        } finally {}
    }

}
