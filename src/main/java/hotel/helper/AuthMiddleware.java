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
    public static Map<String, Claim> checkAuth(HttpServletRequest request, String secret) 
        throws ServletException, IOException, JWTVerificationException {
        String token = request.getHeader("Authorization");
        if (token == null) {
            throw new JWTVerificationException("Authorization header is not provided");
        }

        String[] tParts = token.split(" ");
        if (!tParts[0].equals("Bearer")) {
            throw new JWTVerificationException("Not Bearer token");
        }

        token = tParts[1];

        return HotelJWT.verifyToken(token, secret);
    }
}
