package by.ticketstore.entities;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;


@Data
@RequiredArgsConstructor
public class Seance {

    private Long id;
    private @NonNull Movie movie;
    private @NonNull CinemaHall cinemaHall;
    private @NonNull LocalDate seanceDate;
    private @NonNull LocalTime seanceTime;
    private @NonNull BigDecimal price;
    private Map<Integer, Map<Integer, Ticket>> rowSeatTickets = new HashMap<>();


    public Seance(Long id, Movie movie, LocalDate seanceDate, LocalTime seanceTime, BigDecimal price) {
        this.id = id;
        this.movie = movie;
        this.seanceDate = seanceDate;
        this.seanceTime = seanceTime;
        this.price = price;
    }
}
