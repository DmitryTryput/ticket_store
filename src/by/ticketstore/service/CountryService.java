package by.ticketstore.service;

import by.ticketstore.dao.CountryDao;
import by.ticketstore.entities.Country;
import java.util.List;

public class CountryService {

    private static CountryService INSTANCE = null;

    private CountryService() {
    }

    public static CountryService getInstance() {
        if (INSTANCE == null) {
            synchronized (CountryService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CountryService();
                }
            }
        }
        return INSTANCE;
    }

    public List<Country> getAll() {
        return CountryDao.getInstance().getAll();
    }

}
