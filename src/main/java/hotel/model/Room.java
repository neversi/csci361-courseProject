package hotel.model;

public class Room extends ModelSQL {
    public int hotel_id;
    public int room_number;
    public int flr;
    public String room_type;
    public String isempty;
    public String cleaned;

    public Room() {}

    public String[] pKey() {
        return new String[]{
            "hotel_id",
            "room_number"
        };
    }

    public String tableName() {
        return "Room";
    }
    
}
