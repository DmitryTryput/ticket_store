package servlet;

import by.ticketstore.dto.CinemaBasicDto;
import by.ticketstore.service.CinemaService;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


import static util.ServletUtil.createViewPath;

@WebServlet("/add-cinema")
public class AddCinemaServlet  extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext()
                .getRequestDispatcher(createViewPath("add-cinema"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
           String title = req.getParameter("title");
        if(!title.equals("")) {
           CinemaBasicDto cinema = new CinemaBasicDto(title);
        CinemaService cinemaService = CinemaService.getInstance();
        cinemaService.add(cinema);
            resp.sendRedirect("upcoming");
        } else {
            resp.sendRedirect("add-cinema");
        }
    }
}
