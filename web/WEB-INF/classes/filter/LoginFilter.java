package filter;

import by.ticketstore.service.UrlService;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest servletRequest1 = (HttpServletRequest) servletRequest;
        HttpSession session = servletRequest1.getSession();
        if (session.getAttribute("id") != null) {
            if (session.getAttribute("role").equals("Администратор")) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else if (UrlService.getInstance().getUserUrls().contains(servletRequest1.getRequestURI())) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                ((HttpServletResponse) servletResponse).sendRedirect("/upcoming");
            }
        } else if (UrlService.getInstance().getUninitializedUrls().contains(servletRequest1.getRequestURI())) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse).sendRedirect("/login");
        }
    }

    @Override
    public void destroy() {
    }
}
