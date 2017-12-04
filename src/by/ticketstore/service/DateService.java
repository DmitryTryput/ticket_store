package by.ticketstore.service;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DateService {

    private static DateService INSTANCE;
    private static List<Integer> days = new ArrayList<>();
    private static List<Integer> months = new ArrayList<>();
    private static List<Integer> years = new ArrayList<>();

    private DateService() {}

    static {
        for (int i = 1; i <= 31; i++) {
            days.add(i);
        }
        for (int i = 1; i <= 12; i++) {
            months.add(i);
        }
        for (int i = 0; i < 100; i++) {
            years.add(LocalDate.now().getYear() + 1 - i);
        }
    }

    public static DateService getInstance() {
        if (INSTANCE == null) {
            synchronized (DateService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DateService();
                }
            }
        }
        return INSTANCE;
    }

    public List<Integer> getDays() {
        return days;
    }

    public List<Integer> getMonths() {
        return months;
    }

    public List<Integer> getYears() {
        return years;
    }
}