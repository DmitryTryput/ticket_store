package by.ticketstore.service;

import by.ticketstore.dao.CinemaDao;
import by.ticketstore.dto.CinemaBasicDto;
import by.ticketstore.entities.Cinema;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CinemaService {

    private static CinemaService INSTANCE = null;

    private CinemaService() {
    }

    public static CinemaService getInstance() {
        if (INSTANCE == null) {
            synchronized (CinemaService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CinemaService();
                }
            }
        }
        return INSTANCE;
    }

    public void add(CinemaBasicDto cinemaDto) {
        CinemaDao cinemaDao = CinemaDao.getInstance();
        cinemaDao.addCinema(new Cinema(cinemaDto.getTitle()));
    }

    public List<CinemaBasicDto> getAll() {
        return CinemaDao.getInstance().getAll().stream()
                .map(cinemaEntity ->
                        new CinemaBasicDto(cinemaEntity.getId(), cinemaEntity.getTitle()))
                .collect(Collectors.toList());
    }
}
