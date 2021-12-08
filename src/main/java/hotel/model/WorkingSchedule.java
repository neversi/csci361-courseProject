package hotel.model;

import java.time.LocalTime;

public class WorkingSchedule extends ModelSQL {
    public int id;
    public int hotel_id;
    public int employee_id;
    public LocalTime froom;
    public LocalTime too;

    public WorkingSchedule() {}

    public String[] pKey() {
        return new String[]{
            "id"
        };
    }
}
