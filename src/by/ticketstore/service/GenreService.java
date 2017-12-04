package by.ticketstore.service;

import by.ticketstore.dao.GenreDao;
import by.ticketstore.dto.GenreInfoDto;
import java.util.List;
import java.util.stream.Collectors;

public class GenreService {

    private static GenreService INSTANCE = null;

    private GenreService() {
    }

    public List<GenreInfoDto> getAll() {
        return GenreDao.getInstance().getAll().stream()
                .map(genreEntity -> new GenreInfoDto(genreEntity.getId(), genreEntity.getName()))
                .collect(Collectors.toList());
    }

    public static GenreService getInstance() {
        if (INSTANCE == null) {
            synchronized (GenreService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GenreService();
                }
            }
        }
        return INSTANCE;
    }
}
