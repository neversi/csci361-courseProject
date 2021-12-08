package hotel.helper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

public class RestError {

    public static void WriteResponse(HttpServletResponse response, int sc, String reason)
    throws ServletException, IOException {
        Map<String, String> headers = new HashMap<>();
        headers.putAll(CORSMiddleware.corsAllow());
        for (String k : headers.keySet()) {
            response.setHeader(k, headers.get(k));
        }
        response.sendError(sc, reason);
    }
}
