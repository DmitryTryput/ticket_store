package by.ticketstore.entities;


import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Cinema {

    private Long id;
    private String title;
    private Set<CinemaHall> halls = new HashSet<>();

    public Cinema(String title) {
        this.title = title;
    }

    public Cinema(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}
