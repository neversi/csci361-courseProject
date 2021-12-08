package hotel.model.dto;

import hotel.helper.HotelJWT;

public class TokenUserDTO extends HotelJWT {
    public String username, name, surname;
    public TokenUserDTO(HotelJWT tokens, UserDTO user) {
        super();
        this.access_token = tokens.access_token;
        this.refresh_token = tokens.refresh_token;
        this.username = user.username;
        this.name = user.name;
        this.surname = user.surname;
    }
}
