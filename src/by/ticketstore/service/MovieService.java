package by.ticketstore.service;

import by.ticketstore.dao.MovieDao;
import by.ticketstore.dao.PersonDao;
import by.ticketstore.dto.*;
import by.ticketstore.entities.Country;
import by.ticketstore.entities.Genre;
import by.ticketstore.entities.Movie;
import by.ticketstore.entities.Person;

import java.util.List;
import java.util.stream.Collectors;

public class MovieService {

    private static MovieService INSTANCE = null;

    private MovieService() {
    }

    public Long add(MovieAddDto movieDto) {
        Movie movie = new Movie(movieDto.getTitle(), movieDto.getCreateDate(),
                new Country(movieDto.getCountry().getId()), new Person(movieDto.getDirector().getId()));
        for (PersonBasicDto person : movieDto.getActors()) {
            movie.getActors().add(new Person(person.getId()));
        }
        for (GenreInfoDto genre : movieDto.getGenres()) {
            movie.getGenres().add(new Genre(genre.getId()));
        }
        return MovieDao.getInstance().addMovie(movie);
    }

    public List<MovieBaseInfoDto> getAll() {
        return MovieDao.getInstance().getAll().stream()
                .map(movieEntity -> new MovieBaseInfoDto(movieEntity.getId(), movieEntity.getTitle()))
                .collect(Collectors.toList());
    }

    public static MovieService getInstance() {
        if (INSTANCE == null) {
            synchronized (MovieService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieService();
                }
            }
        }
        return INSTANCE;
    }
}
