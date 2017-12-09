package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;



@WebFilter("/*")
public class LocaleFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest servletRequest1 = (HttpServletRequest) servletRequest;
        HttpSession session = servletRequest1.getSession();
        if (session.getAttribute("locale") != null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            session.setAttribute("locale", "en_US");
            filterChain.doFilter(servletRequest, servletResponse);

        }
  }

    @Override
    public void destroy() {

    }
}
