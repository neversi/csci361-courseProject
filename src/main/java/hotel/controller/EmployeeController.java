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
import hotel.model.Employee;
import hotel.model.Hotel;
import hotel.model.dto.HotelRoomsDTO;
import hotel.model.dto.RoomReservationDateDTO;
import hotel.model.dto.RoomsDTO;
import hotel.repository.HotelRepository;
import hotel.repository.postgres.HotelRepositoryPostgres;
import hotel.repository.postgres.RoomsRepositoryPostgres;
import hotel.service.EmployeeService;
import hotel.service.HotelService;
import hotel.service.HotelServiceImpl;
import hotel.service.RoomService;

@WebServlet(urlPatterns = "/employees")
public class EmployeeController extends HttpServlet {
    public EmployeeService es;
    
    public EmployeeController() {
        super();

        
        this.es = new EmployeeService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
        .create();

        String hotelID = request.getParameter("hotel");

        int hotelId = 0;

        try {
            if (hotelID != null) {
                hotelId = Integer.parseInt(hotelID);
            } else {
                RestError.WriteResponse(response, 400, "hotel param is not present");
            }
        } catch (Exception e) {
            RestError.WriteResponse(response, 400, e.toString());
            return;
        }
        System.out.println(hotelId);
        List<Employee> emps = new ArrayList<>();
        try {
            emps = es.getEmployeesByHotel(hotelId);
        } finally {}

        try {
            RestSuccess.WriteResponse(response, 200, gson.toJson(emps));
        } catch (Exception e) {
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
                try {
                RestSuccess.WriteResponse(response, 200, "");

                } catch (Exception e) {
                    e.printStackTrace();
                }
    }

}
