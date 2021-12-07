package hotel.model;

import java.time.LocalTime;

public class WorkingSchedule extends ModelSQL {
    public int hotel_id;
    public int employee_id;
    public LocalTime froom;
    public LocalTime too;

    public WorkingSchedule() {}
}
