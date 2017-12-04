package by.ticketstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class SeanceAddDto {

    private Long movieId;
    private Long cinemaHallId;
    private LocalDate date;
    private LocalTime time;
    private BigDecimal price;
}
