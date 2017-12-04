package servlet;

import by.ticketstore.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

import static util.ServletUtil.createViewPath;

@WebServlet("/add-value")
public class AddValueServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext()
                .getRequestDispatcher(createViewPath("add-value"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BigDecimal value = new BigDecimal(req.getParameter("value"));
        Long userId = (Long) req.getSession().getAttribute("id");
        BigDecimal newValue = UserService.getInstance().addValue(userId, value);
        req.getSession().setAttribute("value", newValue);
    }
}
