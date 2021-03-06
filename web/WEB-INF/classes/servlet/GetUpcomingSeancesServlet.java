package servlet;

import by.ticketstore.dao.SeanceDao;
import com.sun.xml.internal.bind.v2.TODO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


import static util.ServletUtil.createViewPath;

@WebServlet("/upcoming")
public class GetUpcomingSeancesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("seances",SeanceDao.getInstance().getUpcomingAll());
        getServletContext()
                .getRequestDispatcher(createViewPath("upcoming"))
                .forward(req, resp);
    }

}
