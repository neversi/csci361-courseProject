package hotel.model.dto;

import hotel.helper.HotelJWT;

public class TokenUserDTO {
    public HotelJWT tokens;
    public UserDTO user;

    public TokenUserDTO(HotelJWT tokens, UserDTO user) {
        this.tokens = tokens;
        this.user = user;
    }
}
