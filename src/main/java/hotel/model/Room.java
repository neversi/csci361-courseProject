package hotel.model;

public class Room extends ModelSQL {
    public int hotel_id;
    public int room_number;
    public int flr;
    public String room_type;
    public boolean isempty;
    public boolean cleaned;

    public Room() {}

    public String[] pKey() {
        return new String[]{
            "hotel_id",
            "room_number"
        };
    }
    
}
