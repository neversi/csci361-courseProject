package hotel.service;

import java.security.spec.ECFieldF2m;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import hotel.model.Guest;
import hotel.model.Hotel;
import hotel.model.Reservation;
import hotel.model.Room;
import hotel.model.RoomPrice;
import hotel.model.User;
import hotel.model.WeekEnum;
import hotel.model.dto.CreateReserveDTO;
import hotel.model.dto.CreateReserveResponseDTO;
import hotel.model.dto.RoomReservationDateDTO;
import hotel.model.dto.RoomsDTO;
import hotel.model.dto.UpdateReservationDTO;
import hotel.repository.EmployeeRepository;
import hotel.repository.GuestRepository;
import hotel.repository.ReservationRepository;
import hotel.repository.RoomPriceRepository;
import hotel.repository.RoomsRepository;
import hotel.repository.UserRepository;
import hotel.repository.postgres.EmployeeRepositoryPostgres;
import hotel.repository.postgres.GuestRepositoryPostgres;
import hotel.repository.postgres.ReservationRepositoryPostgres;
import hotel.repository.postgres.RoomPriceRepositoryPostgres;
import hotel.repository.postgres.RoomsRepositoryPostgres;
import hotel.repository.postgres.UserRepositoryPostgres;

import java.time.temporal.ChronoUnit;
import java.time.temporal.ChronoUnit.*;

public class ReservationService {
    public ReservationRepository rr;
    public GuestRepository gr;
    public RoomPriceRepository rrr;
    public UserRepository ur;
    public RoomsRepository roomR;
    public RoomService roomS;

    public ReservationService() {
        this.rr = new ReservationRepositoryPostgres();
        this.gr = new GuestRepositoryPostgres();
        this.ur = new UserRepositoryPostgres();
        this.rrr = new RoomPriceRepositoryPostgres();
        this.roomR = new RoomsRepositoryPostgres();
        this.roomS = new RoomService(roomR);
    }

    public List<Reservation> getUserReservations(String email) throws Exception {
        List<Reservation> reservations = new ArrayList<>();

        try {
            Guest guest = new Guest();
            guest.email = email;

            guest = gr.getOneByParam(guest, "email");
            if (guest == null) {
                throw new Exception("There is no such guest");
            }
            
            Reservation reserve = new Reservation();
            reserve.guest_id = guest.guest_id;

            reservations = rr.getListByParam(reserve, "guest_id");
            for (Reservation r : reservations) {

            }
        } finally {}
        
        return reservations;
    }

    public List<Reservation> getClerkReservations(int hotel_id) {
        List<Reservation> reservations = new ArrayList<>();

        try {
            Reservation reserve = new Reservation();

            reserve.hotel_id = hotel_id;
            reserve.check_out = LocalDate.now();

            reservations = rr.getListFromDate(reserve);
        } finally {}
        
        return reservations;
    }

    public CreateReserveResponseDTO createReservation(CreateReserveDTO cDTO) throws Exception {

        try {
            Guest guest = new Guest();
            guest.email = cDTO.email;

            guest = this.gr.getOneByParam(guest, "email");
            if (guest == null) {
                guest = new Guest();
                guest.hotel_id = cDTO.hotel_id;
                guest.address = cDTO.address;
                guest.name = cDTO.name;
                guest.surname = cDTO.surname;
                guest.home_phone_number = cDTO.home_phone_number;
                guest.mobile_phone_number = cDTO.mobile_phone_number;
                guest.identification_number = Integer.parseInt(cDTO.identification_number);
                guest.identification_type = cDTO.identification_type;
                guest.email = cDTO.email;
                try {
                    guest = this.gr.create(guest);
                } catch (Exception e) {
                    throw new Exception("ReservationService.createReservation: " + e.toString());
                }
            }

            Reservation reserve = new Reservation();

            reserve.hotel_id = cDTO.hotel_id;
            reserve.room_number = cDTO.room_number;
            reserve.guest_id = guest.guest_id;
            reserve.check_in = cDTO.check_in;
            reserve.check_out = cDTO.check_out;
            reserve.day_of_week = cDTO.day_of_week;        

            int total_price = 0;
            Room room = new Room();
            room.room_number = cDTO.room_number;
            room = this.roomR.getOneByParam(room, "room_number");
            if (room == null) {
                throw new Exception("There is no such room");
            }
            RoomPrice rp = new RoomPrice();

            rp.hotel_id = cDTO.hotel_id;
            List<RoomPrice> rps = this.rrr.getListByParam(rp, "hotel_id");
            Map<WeekEnum, Integer> prices = new TreeMap<>();
            for (RoomPrice roomPrice : rps) {
                if (roomPrice.room_type.equals(room.room_type)) {
                    prices.put(WeekEnum.getDay(roomPrice.day_of_week), roomPrice.price);
                }
            }
            int dayCount = 0;
            WeekEnum currentDay = WeekEnum.getDay(reserve.day_of_week);
            while (dayCount < reserve.check_in.until(reserve.check_out, ChronoUnit.DAYS)) {
                dayCount += 1;
                total_price += prices.get(currentDay);
                currentDay = WeekEnum.addDay(currentDay);
            }
            reserve.total_price = total_price;
            try {
                reserve = this.rr.create(reserve);
            } catch (Exception e) {
                throw new Exception(e);
            }

            CreateReserveResponseDTO newcDTO = new CreateReserveResponseDTO();
            newcDTO.hotel_id = reserve.hotel_id;
            newcDTO.room_number = reserve.room_number;
            newcDTO.total_price = reserve.total_price;
            newcDTO.name = cDTO.name;
            newcDTO.surname = cDTO.surname;
            return newcDTO;
            } catch (Exception e) {
            throw new Exception("ReservationService.createReservation: " + e.toString());
        }
    }

    public Reservation updateReservation(UpdateReservationDTO uRes) throws Exception {

        Reservation oldRes = new Reservation();
        oldRes.id = uRes.reservation_id;

        oldRes = this.rr.getOneByParam(oldRes, "id");
        if (oldRes == null) {
            throw new Exception("There is no such reservation!");
        }

        RoomReservationDateDTO desiredRoom  = new RoomReservationDateDTO();
        desiredRoom.cin = uRes.cin;
        desiredRoom.cout = uRes.cout;
        desiredRoom.hotel_id = oldRes.hotel_id;

        List<RoomsDTO> freeRooms = this.roomS.getFreeRooms(desiredRoom);
        
        for (RoomsDTO room : freeRooms) {
            if (room.room_number == uRes.room_number) {
                oldRes.room_number = uRes.room_number;
                oldRes.check_in = uRes.cin;
                oldRes.check_out = uRes.cout;
                Reservation res = this.rr.update(oldRes);
                return res;
            }
        }

        ArrayList<LocalDate> checkDates = new ArrayList<>();

        if (uRes.room_number == oldRes.room_number) {
            if (oldRes.check_in.until(uRes.cin, ChronoUnit.DAYS) < 0) {
                checkDates.add(uRes.cin);
                checkDates.add(oldRes.check_in);
            }

            if (oldRes.check_out.until(uRes.cout, ChronoUnit.DAYS) > 0) {
                checkDates.add(oldRes.check_out);
                checkDates.add(uRes.cout);
            }

            int count = 1;
            System.out.println(checkDates.size());
            for (int i = 0; i < checkDates.size(); i += 2) {
                desiredRoom.cin = checkDates.get(i);
                desiredRoom.cout = checkDates.get(i + 1);

                freeRooms = this.roomS.getFreeRooms(desiredRoom);
                for (RoomsDTO room : freeRooms) {
                    if (room.room_number == uRes.room_number) {
                        count *= 2;
                        break;
                    }
                }
            }

            if (count >= checkDates.size()) {
                oldRes.room_number = uRes.room_number;
                oldRes.check_in = uRes.cin;
                oldRes.check_out = uRes.cout;
                Reservation res = this.rr.update(oldRes);
                return res;
            }
        }
        
        
        throw new Exception("There is no opportunity to change the reservation data");
    }

}
