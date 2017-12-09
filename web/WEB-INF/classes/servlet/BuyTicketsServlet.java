package servlet;

import by.ticketstore.entities.Seance;
import by.ticketstore.service.BuyTicketsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static util.ServletUtil.createViewPath;

@WebServlet("/buy-tickets")
public class BuyTicketsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        try {
            Seance seanceTickets = BuyTicketsService.getInstance().getSeanceTickets(id);
                req.setAttribute("tickets", seanceTickets);
                getServletContext()
                        .getRequestDispatcher(createViewPath("buy-tickets"))
                        .forward(req, resp);
        } catch (IllegalArgumentException e) {
            getServletContext()
                    .getRequestDispatcher(createViewPath("upcoming"))
                    .forward(req, resp);
        }
    }
}
