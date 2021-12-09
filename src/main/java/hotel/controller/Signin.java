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
import hotel.helper.RestError;
import hotel.helper.RestSuccess;
import hotel.model.User;
import hotel.model.dto.TokenUserDTO;
import hotel.model.dto.UserDTO;
import hotel.model.dto.UserSignupDTO;
import hotel.service.UserService;
import hotel.service.UserServiceImpl;

@WebServlet(urlPatterns = "/signup")
public class Signin extends HttpServlet {
    public UserService us;
    public Random rand;
    public Signin() {
        super();
        us = new UserServiceImpl();
        rand = new Random(System.nanoTime());
    }

    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                try {
                RestSuccess.WriteResponse(response, 200, "");

                } catch (Exception e) {
                    e.printStackTrace();
                }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                try {
                RestSuccess.WriteResponse(response, 200, "");

                } catch (Exception e) {
                    e.printStackTrace();
                }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            Gson gson = new Gson();

            if (request.getContentLength() < 10) {
                response.sendError(401, "Invalid data");
                return;
            }
            UserSignupDTO uDTO;
            try {
                uDTO = gson.fromJson(BodyReader.getBody(request), UserSignupDTO.class);
            } catch (Exception e) {
                RestError.WriteResponse(response, 401, "JSON" + e);
                return;
            }
            
            User u = new User();
            u.email = uDTO.username;
            System.out.println(u.email);
            try {
                Optional<User> uo = us.getUserByName(u);

                if (!uo.isEmpty()) {
                    RestError.WriteResponse(response, 401, "Such email is already exist");
                    return;
                }
            } catch (Exception e) {
                RestError.WriteResponse(response, 500, e.toString());
                return;
            }
            
            String salt = RandomStringGenerator.getRandomString(19);
            
            uDTO.password += salt;

            MessageDigest md;
            try {
                md = MessageDigest.getInstance("MD5");
            } catch(NoSuchAlgorithmException e) {
                throw new ServletException("No Algorithm found");
            }

            byte[] hashPwd = md.digest(uDTO.password.getBytes("UTF-8"));

            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < hashPwd.length; i++) {
                String s = Integer.toHexString(0xff & hashPwd[i]);
                sb.append(s);
            }
            
            uDTO.password = sb.toString();

            User newUser = new User(uDTO, salt);
            try {
                us.createUser(newUser);
            } catch (Exception e) {
                RestError.WriteResponse(response, 500, e.toString());
                return;
            }

            Map<String, Object> claims = new HashMap<>();
            claims.put("username", uDTO.username);
            claims.put("name", uDTO.name);

            HotelJWT tokens = HotelJWT.getTokens(claims, getServletContext().getInitParameter("jwt-secret"));

            uDTO.password = "";
            TokenUserDTO info = new TokenUserDTO(tokens, (UserDTO) uDTO);
            try {
                String body = gson.toJson(info, TokenUserDTO.class);
                RestSuccess.WriteResponse(response, 201, body);
            } catch (Exception e) {
                RestError.WriteResponse(response, 501, e.toString());
                return;
            }
        }
}
