package hotel.model;

public class HotelPhoneNumber extends ModelSQL {
    public int hotel_id;
    public String h_phone_number;

    public HotelPhoneNumber() {
        this.hotel_id = 0;
        this.h_phone_number = "";
    }

    public String[] pKey() {
        return new String[]{
            "hotel_id",
            "room_number"
        };
    }
}
