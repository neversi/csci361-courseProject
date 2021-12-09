import java.time.LocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.ChronoUnit;

public class Main {

    enum WeekEnum {
        MON,
        TUE,
        WED,
        THU,
        FRI,
        SAT,
        SUN
    }
    public static void main(String[] args) {
        WeekEnum current = WeekEnum.MON;
        LocalDate cin = LocalDate.now();
        LocalDate cout = LocalDate.now().plusDays(1);
        System.out.println(cout.until(cin, ChronoUnit.DAYS));
    }
}