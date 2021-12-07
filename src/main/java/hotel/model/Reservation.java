package hotel.model;

import java.time.LocalDate;

public class Reservation extends ModelSQL {
    public int hotel_id;
    public int room_number;
    public int guest_id;
    public LocalDate check_in;
    public LocalDate check_out;
    public String day_of_week;
    public int total_price;

    public Reservation() {}
}