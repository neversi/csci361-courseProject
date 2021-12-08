package hotel.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import hotel.helper.RestError;
import hotel.helper.RestSuccess;
import hotel.model.Hotel;
import hotel.repository.HotelRepository;
import hotel.repository.postgres.HotelRepositoryPostgres;
import hotel.service.HotelService;
import hotel.service.HotelServiceImpl;

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
                    RestSuccess.WriteResponse(response, 200, "{}");
                } else {
                    Hotel h = getHotel.get();
                    RestSuccess.WriteResponse(response, 200, new Gson().toJson(h));
                }
            } catch (Exception e) {
                e.printStackTrace();
                RestError.WriteResponse(response, 500, e.toString());
            }
        }
        

    }
    
}
