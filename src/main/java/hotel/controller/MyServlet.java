package hotel.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.interfaces.Claim;

import hotel.helper.AuthMiddleware;
import hotel.helper.CORSMiddleware;

@WebServlet(urlPatterns = { "/myservlet" })
public class MyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public MyServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, Claim> claims = null;
        try {
            String secret = getServletContext().getInitParameter("jwt-secret");
            claims = AuthMiddleware.checkAuth(request, response, secret);
        } catch (Exception e) {
            response.sendError(401, e.toString());
            return;
        }
        CORSMiddleware.corsAllow(request, response);

        String jwtSecret = getServletContext().getInitParameter("jwt-secret");
        try {
            AuthMiddleware.checkAuth(request, response, jwtSecret);
        } catch(Exception e) {
            response.sendError(401, "Incorrect token");
            return;
        }
        String name = request.getParameter("id");
        PrintWriter pw = response.getWriter();
        pw.println("<html> <h1> Hello, " + claims.get("username").asString() + claims.get("name").asString() + "</h1></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}