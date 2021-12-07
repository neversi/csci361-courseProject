package hotel.helper;

import java.util.Date;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

public class HotelJWT {

    private String access_token;
    private String refresh_token;

    public HotelJWT(String at, String rt) {
        this.access_token = at;
        this.refresh_token = rt;
    }
    
    public static HotelJWT getTokens(Map<String, ?> claims, String secret) {
        Algorithm algo = Algorithm.HMAC256(secret);
        
        Date accessExp = new Date();
        accessExp.setTime(accessExp.getTime() + (long) 3.6e6);
        String accessToken = JWT.create().withExpiresAt(accessExp).withPayload(claims).sign(algo);
        Date refreshExp = new Date();
        refreshExp.setTime(refreshExp.getTime() + (long) 8.64e7);
        String refreshToken = JWT.create().withExpiresAt(refreshExp).withPayload(claims).sign(algo);

        return new HotelJWT(accessToken, refreshToken);
    }

    public static Map<String, Claim> verifyToken(String token, String secret) throws JWTVerificationException {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm).acceptExpiresAt(10).build();

            DecodedJWT jwt = verifier.verify(token);
            
            return jwt.getClaims();
        } catch (JWTVerificationException e) {
            throw e;
        }
    }
}
