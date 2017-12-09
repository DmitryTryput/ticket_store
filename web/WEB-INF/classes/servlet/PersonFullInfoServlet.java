package servlet;

import by.ticketstore.dao.PersonDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static util.ServletUtil.createViewPath;

@WebServlet("/person")
public class PersonFullInfoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String personId = req.getParameter("id");
        if(personId != null) {
            Long id = Long.valueOf(personId);
            req.setAttribute("person", PersonDao.getInstance().getFullInfo(id));
            getServletContext()
                    .getRequestDispatcher(createViewPath("person"))
                    .forward(req, resp);
        } else {
            resp.sendRedirect("persons");
        }
    }
}
