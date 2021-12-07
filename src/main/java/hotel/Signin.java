package hotel;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import hotel.helper.BodyReader;
import hotel.helper.CORSMiddleware;
import hotel.model.User;
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            CORSMiddleware.corsAllow(request, response);
            Gson gson = new Gson();

            if (request.getContentLength() < 10) {
                response.sendError(401, "Invalid data");
                return;
            }
            UserSignupDTO uDTO = null;
            try {
                uDTO = gson.fromJson(BodyReader.getBody(request), UserSignupDTO.class);
            } catch (Exception e) {
                response.sendError(401, "JSON: " + e);
                return;
            }
            
            User u = new User();
            u.setName(uDTO.username);
            try {
                Optional<User> uo = us.getUserByName(u);

                if (!uo.isEmpty()) {
                    response.sendError(401, "Such nickname is already exist");
                    return;
                }
            } catch (Exception e) {
                response.sendError(500, e.toString());
                return;
            }
            
            byte[] array = new byte[7];
            new Random().nextBytes(array);
            String salt = new String(array, Charset.forName("UTF-8"));
            
            uDTO.password += salt;

            MessageDigest md;
            try {
                md = MessageDigest.getInstance("MD5");
            } catch(NoSuchAlgorithmException e) {
                throw new ServletException("No Algorithm found");
            }

            byte[] hashPwd = md.digest(uDTO.password.getBytes("UTF-8"));

            uDTO.password = new String(hashPwd);

            User newUser = new User(uDTO, salt);

            try {
                us.createUser(newUser);
            } catch (Exception e) {
                response.sendError(500, e.toString());
                return;
            }
            
            response.setStatus(201);
    }
}
