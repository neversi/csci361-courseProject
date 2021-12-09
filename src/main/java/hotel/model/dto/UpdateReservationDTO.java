package hotel.model.dto;

import java.time.LocalDate;

public class UpdateReservationDTO {
    public int reservation_id;
    public int room_number;
    public LocalDate cin;
    public LocalDate cout;
}
