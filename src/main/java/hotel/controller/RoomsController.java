package hotel.controller;

import java.io.IOException;
import java.time.LocalDate;
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
    
    public RoomsController() {
        super();

        rs = new RoomService(new RoomsRepositoryPostgres());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
        .create();

        String toReserve = request.getParameter("reserve");
        String hotelID = request.getParameter("hotel");

        if (hotelID == null) {
            RestError.WriteResponse(response, 400, "hotel ID is not provided");
        }

        int hotelId = 0;

        try {
            hotelId = Integer.parseInt(hotelID);
        } catch (Exception e) {
            RestError.WriteResponse(response, 400, e.toString());
        }
        
        if (toReserve == null) {
            // RoomsDTO room = gson.fromJson(BodyReader.getBody(request), RoomsDTO.class);
            try {
                List<RoomsDTO> rooms = rs.getAllRooms(hotelId);
                RestSuccess.WriteResponse(response, 200, gson.toJson(rooms));
            } catch (Exception e) {
                RestError.WriteResponse(response, 500, e.toString());
            }
        } else {
            if (toReserve.equals("free")) {
                try {
                    RoomReservationDateDTO reservation = new RoomReservationDateDTO();
                    reservation = gson.fromJson(BodyReader.getBody(request), RoomReservationDateDTO.class);
                    reservation.hotel_id = hotelId;

                    List<RoomsDTO> rooms = rs.getFreeRooms(reservation);

                    RestSuccess.WriteResponse(response, 200, gson.toJson(rooms));

                } catch (Exception e) {
                    RestError.WriteResponse(response, 400, e.toString());
                }

            }
            if (toReserve.equals("busy")) {
            }
        }
    }
}
