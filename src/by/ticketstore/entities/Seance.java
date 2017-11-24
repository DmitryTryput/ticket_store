package by.ticketstore.entities;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class Seance {

    private Long id;
    private Film film;
    private CinemaHall cinemaHall;
    private LocalDate seanceDate;
    private LocalTime seanceTime;
    private BigDecimal price;

}
