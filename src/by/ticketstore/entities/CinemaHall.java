package by.ticketstore.entities;

import by.ticketstore.dto.CinemaBasicDto;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class CinemaHall {

    private Long id;
    private String title;
    private Integer rows;
    private Integer seats;
    private CinemaBasicDto cinema;
    private Set<Seance> seances = new HashSet<>();

}
