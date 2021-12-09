package hotel.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.interfaces.Claim;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import hotel.helper.AuthMiddleware;
import hotel.helper.BodyReader;
import hotel.helper.LocalDateAdapter;
import hotel.helper.RestError;
import hotel.helper.RestSuccess;
import hotel.model.Employee;
import hotel.model.Hotel;
import hotel.model.Reservation;
import hotel.model.dto.CreateReserveDTO;
import hotel.model.dto.CreateReserveResponseDTO;
import hotel.model.dto.EmployeeWSDTO;
import hotel.model.dto.HotelRoomsDTO;
import hotel.model.dto.RoomReservationDateDTO;
import hotel.model.dto.RoomsDTO;
import hotel.repository.HotelRepository;
import hotel.repository.postgres.HotelRepositoryPostgres;
import hotel.repository.postgres.RoomsRepositoryPostgres;
import hotel.service.EmployeeService;
import hotel.service.HotelService;
import hotel.service.HotelServiceImpl;
import hotel.service.ReservationService;
import hotel.service.RoomService;

@WebServlet(urlPatterns = "/reservations")
public class ReservationController extends HttpServlet {
    public ReservationService rs;
    
    public ReservationController() {
        super();
        this.rs = new ReservationService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
        .create();

        String hotelID = request.getParameter("hotel");
        String userEmail = request.getParameter("email");

        int hotelId = 0;
        try {
            if (hotelID != null) {
                hotelId = Integer.parseInt(hotelID);
            }
        } catch (Exception e) {
            RestError.WriteResponse(response, 400, e.toString());
            return;
        }
        List<Reservation> reservations = new ArrayList<>();

        try {
            if (hotelId != 0 && userEmail != null) {
                for (Reservation ur : rs.getUserReservations(userEmail)) {
                    for (Reservation cr : rs.getClerkReservations(hotelId)) {
                        if (ur.id == cr.id) {
                            reservations.add(cr);
                        }
                    }
                }
            } else if (hotelId != 0) {
                reservations = rs.getClerkReservations(hotelId);
            } else if (userEmail != null) {
                reservations = rs.getUserReservations(userEmail);
            }
            RestSuccess.WriteResponse(response, 200, gson.toJson(reservations));
        } catch (Exception e) {
            e.printStackTrace();
            RestError.WriteResponse(response, 500, e.toString());
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
                Map<String, Claim> claims = null;
                try {
                    String secret = getServletContext().getInitParameter("jwt-secret");
                    claims = AuthMiddleware.checkAuth(request, secret);
                } catch (Exception e) {
                    RestError.WriteResponse(response, 401, e.toString());;
                    return;
                }
                String position = "";
                position += claims.get("position").asString();
                if (!(position.equals("") || position.equals("desk-clerk"))) {
                    RestError.WriteResponse(response, 401, "Could be access only by desk-clerk or user");
                    return;
                }
                

                Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

                CreateReserveDTO crDTO = new CreateReserveDTO();
                try {
                    crDTO = gson.fromJson(BodyReader.getBody(request), CreateReserveDTO.class);
                    if (position.equals("") && !crDTO.email.equals(claims.get("username").asString())) {
                        RestError.WriteResponse(response, 401, "Users cannot permission to update token");
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    RestError.WriteResponse(response, 400, e.toString());
                }

                try {
                    Reservation resp = this.rs.createReservation(crDTO);
                    RestSuccess.WriteResponse(response, 201, gson.toJson(resp));
                } catch (Exception e) {
                    e.printStackTrace();
                    RestError.WriteResponse(response, 500, e.toString());
                }
    }

    protected void doUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                Map<String, Claim> claims = null;
                try {
                    String secret = getServletContext().getInitParameter("jwt-secret");
                    claims = AuthMiddleware.checkAuth(request, secret);
                } catch (Exception e) {
                    RestError.WriteResponse(response, 401, e.toString());;
                    return;
                }
                String position = "";
                position += claims.get("position").asString();
                if (!(position.equals("desk-clerk"))) {
                    RestError.WriteResponse(response, 401, "Could be access only by desk-clerk");
                    return;
                }

            }

}
