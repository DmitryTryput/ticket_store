package servlet;

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
        super.doPost(req, resp);
    }
}
