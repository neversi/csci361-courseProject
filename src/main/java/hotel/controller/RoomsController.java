package hotel.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import hotel.helper.BodyReader;
import hotel.helper.LocalDateAdapter;
import hotel.helper.RestError;
import hotel.helper.RestSuccess;
import hotel.model.Hotel;
import hotel.model.dto.HotelRoomsDTO;
import hotel.model.dto.RoomReservationDateDTO;
import hotel.model.dto.RoomsDTO;
import hotel.repository.HotelRepository;
import hotel.repository.postgres.HotelRepositoryPostgres;
import hotel.repository.postgres.RoomsRepositoryPostgres;
import hotel.service.HotelService;
import hotel.service.HotelServiceImpl;
import hotel.service.RoomService;

@WebServlet(urlPatterns = "/rooms")
public class RoomsController extends HttpServlet {

    public RoomService rs;
    public HotelService hs;
    
    public RoomsController() {
        super();

        rs = new RoomService(new RoomsRepositoryPostgres());
        HotelRepository hr = new HotelRepositoryPostgres();
        this.hs = new HotelServiceImpl(hr);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
        .create();

        String toReserve = request.getParameter("reserve");
        String hotelID = request.getParameter("hotel");

        int hotelId = 0;

        try {
            if (hotelID != null) {
                hotelId = Integer.parseInt(hotelID);
            }
        } catch (Exception e) {
            RestError.WriteResponse(response, 400, e.toString());
            return;
        }
        
        if (toReserve == null) {
            // RoomsDTO room = gson.fromJson(BodyReader.getBody(request), RoomsDTO.class);
            try {
                List<RoomsDTO> rooms = rs.getAllRooms(hotelId);
                RestSuccess.WriteResponse(response, 200, gson.toJson(rooms));
                return;
            } catch (Exception e) {
                RestError.WriteResponse(response, 500, e.toString());
                return;
            }
        } else {
            if (toReserve.equals("free")) {
                try {
                    List<HotelRoomsDTO> hotelRooms = new ArrayList<>();
                    RoomReservationDateDTO reservation = new RoomReservationDateDTO();
                    reservation = gson.fromJson(BodyReader.getBody(request), RoomReservationDateDTO.class);
                    if (hotelId != 0) {
                        reservation.hotel_id = hotelId;
                        HotelRoomsDTO hR = new HotelRoomsDTO();
                        hR.hotel_id = hotelId;
                        hR.rooms = rs.getFreeRooms(reservation);
                        hotelRooms.add(hR);
                    } else {
                        List<Hotel> hotels = hs.listHotels();
                        for (Hotel h : hotels) {
                            reservation.hotel_id = h.hotel_id;
                            HotelRoomsDTO hR = new HotelRoomsDTO();
                            hR.hotel_id = h.hotel_id;
                            hR.rooms = rs.getFreeRooms(reservation);
                            hotelRooms.add(hR);
                        }
                    }
                    System.out.println(hotelRooms);
                    RestSuccess.WriteResponse(response, 200, gson.toJson(hotelRooms));
                } catch (Exception e) {
                    e.printStackTrace();
                    RestError.WriteResponse(response, 400, e.toString());
                }

            }
            if (toReserve.equals("busy")) {
            }
        }
    }

    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                try {
                RestSuccess.WriteResponse(response, 200, "");

                } catch (Exception e) {
                    e.printStackTrace();
                }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                try {
                RestSuccess.WriteResponse(response, 200, "");

                } catch (Exception e) {
                    e.printStackTrace();
                }
    }
}
