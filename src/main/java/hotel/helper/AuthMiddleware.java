package hotel.helper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;

public class AuthMiddleware {
    public static void checkAuth(HttpServletRequest request, HttpServletResponse response, String secret) 
        throws ServletException, IOException, JWTVerificationException {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            response.sendRedirect("/hotel/index.html");
            return;
        }

        for (Cookie c : cookies) {
            if (c.getName().equals("access_token")) {
                try {
                    HotelJWT.verifyToken(c.getValue(), secret);
                    break;
                } catch (JWTVerificationException e) {}
            }
        }
        Map<String, Claim> claims = new HashMap<>();
        for (Cookie c : cookies) {
            if (c.getName().equals("refresh_token")) {
                claims = HotelJWT.verifyToken(c.getValue(), secret);
                break;
            }
        }

        String[] tokens = HotelJWT.getTokens(claims, secret);
        if (tokens.length == 0) {
            response.sendError(500, "Error while creating tokens");
            return;
        }
        Cookie access_token = new Cookie("access_token", tokens[0]);
        Cookie refresh_token = new Cookie("refresh_token", tokens[1]);

        response.addCookie(access_token);
        response.addCookie(refresh_token);
    }
}
