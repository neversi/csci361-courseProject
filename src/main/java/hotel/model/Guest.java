package hotel.model;

public class Guest extends ModelSQL {
    public int hotel_id;
    public int guest_id;
    public String identification_type;
    public String identification_number;
    public String address;
    public String name;
    public String surname;
    public String home_phone_number;
    public String mobile_phone_number;

    public Guest() {}
}
