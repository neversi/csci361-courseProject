package hotel;

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
import org.apache.commons.codec.binary.Base64;

import hotel.helper.HotelJWT;
import hotel.model.User;
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
                    response.sendError(401, "Such username \"" + username + "\" doesn't exist");
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

            try {
                if (!new String(hashPwd).equals(u.getPwd())) {
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
            claims.put("role", new String[]{"user", "manager"});

            String[] tokens = HotelJWT.getTokens(claims, getServletContext().getInitParameter("jwt-secret"));
            if (tokens.length == 0) {
                response.sendError(500, "Error while creating tokens");
                return;
            }
            Cookie access_token = new Cookie("access_token", tokens[0]);
            Cookie refresh_token = new Cookie("refresh_token", tokens[1]);

            response.addCookie(access_token);
            response.addCookie(refresh_token);

            response.setStatus(201);
    }

}