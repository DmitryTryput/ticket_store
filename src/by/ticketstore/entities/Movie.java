package by.ticketstore.entities;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.sql.Date;
import java.time.Year;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class Movie {

    private Long id;
    private @NonNull String title;
    private @NonNull Year createDate;
    private @NonNull Country country;;
    private Set<Genre> genres = new HashSet<>();
    private @NonNull Person director;
    private Set<Person> actors = new HashSet<>();
    private Set<Review> reviews = new LinkedHashSet<>();

    public Movie(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Movie(Long id) {
        this.id = id;
    }
}
