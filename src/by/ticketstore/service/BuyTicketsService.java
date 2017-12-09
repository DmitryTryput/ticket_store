package by.ticketstore.service;

import by.ticketstore.dao.SeanceDao;
import by.ticketstore.dao.TicketDao;
import by.ticketstore.dto.TicketDto;
import by.ticketstore.dto.UserDto;
import by.ticketstore.entities.Seance;
import by.ticketstore.entities.Ticket;
import by.ticketstore.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BuyTicketsService {

    private static BuyTicketsService INSTANCE = null;

    private BuyTicketsService() {
    }


    public Seance getSeanceTickets(Long id) throws IllegalArgumentException {
        Optional<Seance> seanceOptional = SeanceDao.getInstance().buyTickets(id);

        if (!seanceOptional.isPresent()) {
            throw new IllegalArgumentException("Данный сеанс не существует");
        }
        return seanceOptional.get();
    }

    public boolean buyTickets(UserDto userDto, List<TicketDto> ticketDto) {
        return TicketDao.getInstance()
                .buyTickets(new User(userDto.getId(), userDto.getValue()), ticketDto.stream()
                        .map(ticketDtos -> new Ticket(ticketDtos.getId())).collect(Collectors.toList()));
    }


    public static BuyTicketsService getInstance() {
        if (INSTANCE == null) {
            synchronized (BuyTicketsService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new BuyTicketsService();
                }
            }
        }
        return INSTANCE;
    }

}
