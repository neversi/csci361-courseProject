package hotel.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
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
        String[] hotelName = request.getParameterValues("hotel");

        

    }
    
}
