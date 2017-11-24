package by.ticketstore.service;

import by.ticketstore.dao.PersonDao;
import by.ticketstore.dto.CreatePerson;
import by.ticketstore.entities.Country;
import by.ticketstore.entities.Person;

public class PersonService {

    private static PersonService INSTANCE = null;

    private PersonService() {}

    public static PersonService getInstance() {
        if (INSTANCE == null) {
            synchronized (PersonService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PersonService();
                }
            }
        }
        return INSTANCE;
    }

    public void add(CreatePerson createPerson) {
        PersonDao instance = PersonDao.getInstance();
        instance.addPerson(new Person(
                createPerson.getFirstName(),
                createPerson.getLastName(),
                createPerson.getDateOfBirth(),
                new Country(createPerson.getCountryDto().getName())
        ));
    }
}
