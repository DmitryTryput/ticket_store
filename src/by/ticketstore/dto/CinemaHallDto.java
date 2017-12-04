package by.ticketstore.dto;


import by.ticketstore.entities.Cinema;
import by.ticketstore.entities.Seance;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class CinemaHallDto {

    private Long id;
    private String title;
    private Integer rows;
    private Integer seats;
    private Long cinemaId;
    private Cinema cinema;
    private Set<Seance> seances = new HashSet<>();

    public CinemaHallDto(String title, Integer rows, Integer seats, Long cinemaId) {
        this.title = title;
        this.rows = rows;
        this.seats = seats;
        this.cinemaId = cinemaId;
    }
}
