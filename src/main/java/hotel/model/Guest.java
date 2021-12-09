package hotel.model;

public class Guest extends ModelSQL {
    public int hotel_id;
    public int guest_id;
    public String identification_type;
    public int identification_number;
    public String address;
    public String email;
    public String name;
    public String surname;
    public String home_phone_number;
    public String mobile_phone_number;

    public Guest() {}

    public String tableName() {
        return "Guest";
    }
    
    public String[] pKey() {
        return new String[]{
            "guest_id"
        };
    }
    
}
