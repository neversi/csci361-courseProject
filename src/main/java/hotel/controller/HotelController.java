package hotel.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import hotel.helper.BodyReader;
import hotel.helper.CORSMiddleware;
import hotel.helper.HotelJWT;
import hotel.helper.RandomStringGenerator;
import hotel.helper.RestError;
import hotel.helper.RestSuccess;
import hotel.model.Hotel;
import hotel.model.User;
import hotel.model.dto.TokenUserDTO;
import hotel.model.dto.UserDTO;
import hotel.model.dto.UserSignupDTO;
import hotel.repository.HotelRepository;
import hotel.repository.postgres.HotelRepositoryPostgres;
import hotel.service.HotelService;
import hotel.service.HotelServiceImpl;
import hotel.service.UserService;
import hotel.service.UserServiceImpl;

@WebServlet(urlPatterns = "/hotels")
public class HotelController extends HttpServlet {
    public HotelService hs;
    
    public HotelController() {
        super();

        HotelRepository hr = new HotelRepositoryPostgres();
        
        this.hs = new HotelServiceImpl(hr);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String hotelName = request.getParameter("hotel");

        if (hotelName == null) {
            try {
                List<Hotel> hotels = hs.listHotels();
                RestSuccess.WriteResponse(response, 200, new Gson().toJson(hotels));
            } catch (Exception e) {
                RestError.WriteResponse(response, 500, e.toString());
            }
        } else {
            try {
                Hotel hotel = new Hotel();
                hotel.hotel_name = hotelName;
                Optional<Hotel> getHotel = hs.getHotelByName(hotel);
                if (getHotel.isEmpty()) {
                    RestSuccess.WriteResponse(response, 200, "");
                } else {
                    RestSuccess.WriteResponse(response, 200, new Gson().toJson(getHotel));
                }
            } catch (Exception e) {
                RestError.WriteResponse(response, 500, e.toString());
            }
        }
        

    }
    
}
