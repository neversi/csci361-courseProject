package hotel.model.dto;

import java.time.LocalDate;

import hotel.model.WeekEnum;

public class CreateReserveDTO {
    public int hotel_id;
    public int room_number;

    public String email;
    public LocalDate check_out;
    public LocalDate check_in;
    public String identification_type;
    public String identification_number;
    public String address;
    public String name;
    public String surname;
    public String home_phone_number;
    public String mobile_phone_number;
    public String day_of_week;
}
