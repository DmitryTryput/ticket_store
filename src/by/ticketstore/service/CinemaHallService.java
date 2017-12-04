package by.ticketstore.service;

import by.ticketstore.dao.CinemaDao;
import by.ticketstore.dao.CinemaHallDao;
import by.ticketstore.dto.CinemaBasicDto;
import by.ticketstore.dto.CinemaHallDto;
import by.ticketstore.entities.CinemaHall;

import java.util.List;
import java.util.stream.Collectors;

public class CinemaHallService {

    private static CinemaHallService INSTANCE = null;

    private CinemaHallService() {
    }

    public static CinemaHallService getInstance() {
        if (INSTANCE == null) {
            synchronized (CinemaHallService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CinemaHallService();
                }
            }
        }
        return INSTANCE;
    }

    public List<CinemaHallDto> getAllWithCinemas() {
        return CinemaHallDao.getInstance().getAllCinemaHallsWithCinemas().stream()
                .map(cinemaHallEntity -> {
                            CinemaHallDto cinemaHallDto = new CinemaHallDto(cinemaHallEntity.getTitle(),
                                    cinemaHallEntity.getRows(), cinemaHallEntity.getSeats(),
                                    cinemaHallEntity.getCinemaId());
                            cinemaHallDto.setId(cinemaHallEntity.getId());
                            cinemaHallDto.setCinema(cinemaHallEntity.getCinema());
                            return cinemaHallDto;
                        })
                .collect(Collectors.toList());
    }

    public void add(CinemaHallDto cinemaHallDto) {
        CinemaHallDao cinemaHallDao = CinemaHallDao.getInstance();
        cinemaHallDao.addCinemaHall(new CinemaHall(cinemaHallDto.getTitle(), cinemaHallDto.getRows(),
                cinemaHallDto.getSeats(), cinemaHallDto.getCinemaId()));
    }
}
