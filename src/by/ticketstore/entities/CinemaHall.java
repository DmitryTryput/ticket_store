package by.ticketstore.entities;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class CinemaHall {

    private Long id;
    private String title;
    private Integer rows;
    private Integer seats;
    private Long cinemaId;
    private Cinema cinema;
    private Set<Seance> seances = new HashSet<>();

    public CinemaHall(String title, Integer rows, Integer seats, Long cinemaId) {
        this.title = title;
        this.rows = rows;
        this.seats = seats;
        this.cinemaId = cinemaId;
    }

    public CinemaHall(Long id) {
        this.id = id;
    }

    public CinemaHall(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}
