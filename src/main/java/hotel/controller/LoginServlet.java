package hotel.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import org.apache.commons.codec.binary.Base64;

import hotel.helper.CORSMiddleware;
import hotel.helper.HotelJWT;
import hotel.model.User;
import hotel.model.dto.TokenUserDTO;
import hotel.model.dto.UserDTO;
import hotel.service.UserService;
import hotel.service.UserServiceImpl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 2L;
    public UserService us;
    public LoginServlet() {
        super();
        us = new UserServiceImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            Gson gson = new Gson();
            String basicAuth = request.getHeader("Authorization");
            if (basicAuth == null) {
                response.sendError(401, "Authorization is not provided");
                return;
            }
            String[] authValues = basicAuth.split(" ");
            if (authValues.length != 2 || !authValues[0].equals("Basic")) {
                response.sendError(401, "Malformed Authorization header provided, Need Basic Auth");
                return;
            }
            String[] decodedInfo = new String(Base64.decodeBase64(authValues[1])).split(":");
            // getting username and password from user
            String username = decodedInfo[0];
            String password = decodedInfo[1];

            User u = new User();
            u.setUsername(username);
            try {
                Optional<User> uo = us.getUserByName(u);

                if (uo.isEmpty()) {
                    response.sendError(401, "Such email \"" + username + "\" doesn't exist");
                    return;
                }
                u = uo.get();
            } catch(Exception e) {
                response.sendError(500, e.toString());
                return;
            }
            
            String salt = u.getSalt();

            password += salt;

            MessageDigest md;
            try {
                md = MessageDigest.getInstance("MD5");
            } catch(NoSuchAlgorithmException e) {
                throw new ServletException("No Algorithm found");
            }

            byte[] hashPwd = md.digest(password.getBytes("UTF-8"));

            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < hashPwd.length; i++) {
                String s = Integer.toHexString(0xff & hashPwd[i]);
                sb.append(s);
            }
            String curPwd = sb.toString();
            System.out.println(curPwd +" " + u.getPwd());
            try {
                if (!new String(curPwd).equals(u.getPwd())) {
                    response.sendError(401, "Incorrect password");
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(500, e.toString());
                return;
            }

            Map<String, Object> claims = new HashMap<>();
            claims.put("username", username);
            claims.put("name", u.getName());

            HotelJWT tokens = HotelJWT.getTokens(claims, getServletContext().getInitParameter("jwt-secret"));

            UserDTO uDTO = new UserDTO();
            uDTO.username = username;
            uDTO.name = u.getName();
            uDTO.surname = u.getSurname();

            TokenUserDTO info = new TokenUserDTO(tokens, uDTO);
            try (PrintWriter pw = response.getWriter()) {
                pw.write(gson.toJson(info, TokenUserDTO.class));
            } catch (Exception e) {
                response.sendError(501, e.toString());
                return;
            }
            response.setStatus(201);
    }

}