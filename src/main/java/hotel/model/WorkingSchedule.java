package hotel.model;

import java.sql.Time;
import java.time.LocalTime;

public class WorkingSchedule extends ModelSQL {
    public int id;
    public int hotel_id;
    public int employee_id;
    public Time froom;
    public Time too;

    public WorkingSchedule() {}

    public String tableName() {
        return "WorkingSchedule";
    }

    public String[] pKey() {
        return new String[]{
            "id"
        };
    }
}
