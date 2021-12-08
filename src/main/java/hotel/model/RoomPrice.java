package hotel.model;

public class RoomPrice extends ModelSQL {
    public int hotel_id;
    public String day_of_week;
    public String room_type;
    public int price;

    public RoomPrice() {}

    public String[] pKey() {
        return new String[]{
            "hotel_id",
            "day_of_week",
            "room_type"
        };
    }
}
