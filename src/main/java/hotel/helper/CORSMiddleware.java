package hotel.helper;

import java.util.HashMap;
import java.util.Map;

public class CORSMiddleware {

    public CORSMiddleware() {}

    public static Map<String, String> corsAllow() {
            Map<String, String> headers = new HashMap<>();
            headers.put("Access-Control-Allow-Origin", "*");
            headers.put("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,PATCH,OPTIONS");
            headers.put("Access-Control-Allow-Headers", "*");
            headers.put("Content-type", "application/json; charset=utf-8");
            // access-control-allow-methods,access-control-allow-origin,content-type
            return headers;
        }
    }