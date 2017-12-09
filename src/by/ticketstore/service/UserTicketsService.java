package by.ticketstore.service;

import by.ticketstore.dao.TicketDao;
import by.ticketstore.dto.MovieBaseInfoDto;
import by.ticketstore.dto.SeanceBasicInfoDto;
import by.ticketstore.dto.UserTicketDto;
import by.ticketstore.entities.Ticket;

import java.util.List;

import java.util.stream.Collectors;

public class UserTicketsService {

    private static UserTicketsService INSTANCE = null;

    private UserTicketsService() {
    }


    public List<UserTicketDto> getUserTickets(Long id) {
        return TicketDao.getInstance().getUserTickets(id)
                .stream().map(this::getUserTicketDto)
                .collect(Collectors.toList());
    }

    private UserTicketDto getUserTicketDto(Ticket ticket) {
        return new UserTicketDto(ticket.getId(), ticket.getRow(), ticket.getSeat(),
                new MovieBaseInfoDto(ticket.getSeance().getMovie().getId(),
                        ticket.getSeance().getMovie().getTitle()),
                new SeanceBasicInfoDto(ticket.getSeance().getSeanceDate(),
                        ticket.getSeance().getSeanceTime(), ticket.getSeance().getPrice()));
    }

    public static UserTicketsService getInstance() {
        if (INSTANCE == null) {
            synchronized (UserTicketsService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserTicketsService();
                }
            }
        }
        return INSTANCE;
    }
}
