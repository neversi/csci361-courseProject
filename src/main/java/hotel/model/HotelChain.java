package hotel.model;

public class HotelChain extends ModelSQL {
    public int hotel_id;
    public String hotel_name;

    public HotelChain() {
        this.hotel_id = 0;
        this.hotel_name = "";
    }

    public String[] pKey() {
        return new String[]{
            "hotel_id"
        };
    }
}


