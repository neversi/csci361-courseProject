package hotel.model;

public class RoomType extends ModelSQL {
    public int hotel_id;
    public String room_type;
    public int size;
    public int capacity;

    public RoomType() {

    }

    public String[] pKey() {
        return new String[]{
            "hotel_id",
            "room_type"
        };
    }
}

