package servlet;

import by.ticketstore.dao.SeanceDao;
import by.ticketstore.dao.TicketDao;
import by.ticketstore.service.SeanceService;
import by.ticketstore.service.UserTicketsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static util.ServletUtil.createViewPath;

@WebServlet("/user")
public class PrivateOfficeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Long id =(Long) session.getAttribute("id");
        req.setAttribute("tickets", UserTicketsService.getInstance().getUserTickets(id));
        getServletContext()
                .getRequestDispatcher(createViewPath("user"))
                .forward(req, resp);
    }
}
