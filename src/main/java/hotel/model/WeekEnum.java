package hotel.model;

public enum WeekEnum {
    MON,
    TUE,
    WED,
    THU,
    FRI,
    SAT,
    SUN;

    public static WeekEnum addDay(WeekEnum we) {
        if (we.equals(WeekEnum.SUN)) {
            return WeekEnum.MON;
        }
        WeekEnum[] wks = WeekEnum.values();
        for (WeekEnum w: wks) {
            if (w.equals(we)) {
                return wks[w.ordinal() + 1];
            }
        }
        return WeekEnum.MON;
    }

    public static WeekEnum getDay(String w) {
        for (WeekEnum t: WeekEnum.values()) {
            if (w.equals(t.toString())) {
                return t;
            }
        }
        return WeekEnum.MON;
    }
}

