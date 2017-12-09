package servlet;

import by.ticketstore.dto.CinemaHallDto;
import by.ticketstore.service.CinemaHallService;
import by.ticketstore.service.CinemaService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static util.ServletUtil.createViewPath;

@WebServlet("/add-cinemahall")
public class AddCinemaHallServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("cinemas", CinemaService.getInstance().getAll());
        getServletContext()
                .getRequestDispatcher(createViewPath("add-cinemahall"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String rowString = req.getParameter("rows");
        String stringSeats = req.getParameter("row_seats");
        if (isInput(title, rowString, stringSeats)) {
            Integer rows = Integer.valueOf(rowString);
            Integer rowSeats = Integer.valueOf(stringSeats);
            Long cinemaId = Long.valueOf(req.getParameter("cinemaId"));
            CinemaHallDto cinemaHallDto = new CinemaHallDto(title, rows, rowSeats, cinemaId);
            CinemaHallService.getInstance().add(cinemaHallDto);
            resp.sendRedirect("upcoming");
        } else {
            resp.sendRedirect("add-cinemahall");
        }
    }

    private boolean isInput(String title, String rowString, String stringSeats) {
        return !title.equals("") && !rowString.equals("") && !stringSeats.equals("");
    }
}
