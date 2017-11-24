package by.ticketstore.dto;


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
    private Set<Seance> seances = new HashSet<>();



}
