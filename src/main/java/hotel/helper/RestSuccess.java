package hotel.helper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialException;

public class RestSuccess {
    public static void WriteResponse(HttpServletResponse response, int sc, String body) throws SerialException, IOException {
        response.setHeader("Content-type", "application/json; charset=utf-8");
        Map<String, String> headers = new HashMap<>();
        headers.putAll(CORSMiddleware.corsAllow());
        for (String k : headers.keySet()) {
            response.setHeader(k, headers.get(k));
        }
        try (PrintWriter pw = response.getWriter()) {
            pw.write(body);
        }
        response.setStatus(sc);
    }
}
