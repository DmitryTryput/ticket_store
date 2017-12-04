package by.ticketstore.service;

import by.ticketstore.dao.SeanceDao;
import by.ticketstore.dto.SeanceAddDto;
import by.ticketstore.entities.CinemaHall;
import by.ticketstore.entities.Movie;
import by.ticketstore.entities.Seance;

import java.time.LocalDate;

public class SeanceService {

    private static SeanceService INSTANCE = null;

    private SeanceService() {
    }

    public static SeanceService getInstance() {
        if (INSTANCE == null) {
            synchronized (SeanceService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SeanceService();
                }
            }
        }
        return INSTANCE;
    }

    public void add(SeanceAddDto seanceAddDto) {
        SeanceDao.getInstance().addSeance(new Seance(new Movie(seanceAddDto.getMovieId()),
                new CinemaHall(seanceAddDto.getCinemaHallId()),
                seanceAddDto.getDate(), seanceAddDto.getTime(), seanceAddDto.getPrice()));
    }

}
