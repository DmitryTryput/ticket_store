package by.ticketstore.service;

import java.util.ArrayList;
import java.util.List;

public class TimeService {

    private static TimeService INSTANCE;
    private static List<Integer> hours = new ArrayList<>();
    private static List<Integer> minutes = new ArrayList<>();

    private TimeService() {}

    static {
        for (int i = 0; i <= 23; i++) {
            hours.add(i);
        }
        for (int i = 0; i <= 59; i++) {
            minutes.add(i);
        }

    }

    public static TimeService getInstance() {
        if (INSTANCE == null) {
            synchronized (TimeService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TimeService();
                }
            }
        }
        return INSTANCE;
    }

    public static List<Integer> getHours() {
        return hours;
    }

    public static List<Integer> getMinutes() {
        return minutes;
    }
}
