package hotel.model;

public class Hotel extends ModelSQL {
    public String tableName() {
        return "Hotel";
    }

    public int hotel_id;
    public String hotel_name;
    public String hotel_address;

    public Hotel() {
        this.hotel_address = "";
        this.hotel_name = "";
        this.hotel_id = 0;
    }
}

