package servlet;

import by.ticketstore.dto.TicketDto;
import by.ticketstore.dto.UserDto;
import by.ticketstore.service.BuyTicketsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.stream.Collectors;


@WebServlet("/confirm")
public class ConfirmServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] seats = req.getParameterValues("seat");
        if (seats != null && !isEnoughValue(req) && tryBuyTickets(req, seats)) {
            req.getSession().setAttribute("value", userValue(req));
            resp.sendRedirect("upcoming");
        } else {
            resp.sendRedirect(req.getHeader("Referer"));
        }
    }

    private boolean tryBuyTickets(HttpServletRequest req, String[] seats) {
        return BuyTicketsService.getInstance().buyTickets(
                new UserDto((Long) req.getSession().getAttribute("id"),
                        userValue(req)), Arrays.stream(seats)
                        .map(seat -> new TicketDto(Long.valueOf(seat))).collect(Collectors.toList()));
    }

    private boolean isEnoughValue(HttpServletRequest req) {
        return new BigDecimal(req.getParameter("price"))
                .multiply(new BigDecimal(req.getParameterValues("seat").length))
                .compareTo((BigDecimal) req.getSession().getAttribute("value")) >= 0;
    }

    private BigDecimal userValue(HttpServletRequest req) {
        BigDecimal value = ((BigDecimal) req.getSession().getAttribute("value"));
        return value.subtract(new BigDecimal(req.getParameter("price"))
                .multiply(new BigDecimal(req.getParameterValues("seat").length)));
    }
}
