package by.ticketstore.service;

import by.ticketstore.dao.PersonDao;
import by.ticketstore.dto.PersonBasicDto;
import by.ticketstore.dto.ViewPersonBasicInfoDto;
import by.ticketstore.entities.Country;
import by.ticketstore.entities.Person;

import java.util.List;
import java.util.stream.Collectors;

public class PersonService {

    private static PersonService INSTANCE = null;

    private PersonService() {
    }

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

    public void add(PersonBasicDto personBasicDto) {
        PersonDao instance = PersonDao.getInstance();
        instance.addPerson(new Person(
                personBasicDto.getFirstName(),
                personBasicDto.getLastName(),
                personBasicDto.getDateOfBirth(),
                new Country(personBasicDto.getCountryDto().getId())
        ));
    }

    public List<ViewPersonBasicInfoDto> getAll() {
        return PersonDao.getInstance().getAll().stream()
                .map(personEntity -> new ViewPersonBasicInfoDto(personEntity.getId(), personEntity.getFirstName(),
                        personEntity.getLastName())).collect(Collectors.toList());
    }
}
