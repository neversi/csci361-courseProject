package hotel.helper;

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

public class CORSMiddleware {

    public CORSMiddleware() {}
    public static void corsAllow(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, UPDATE");

        }
    }