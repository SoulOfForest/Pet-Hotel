/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import dao.UserDAO;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.User;

/**
 *
 * @author ViruSs0209
 */
public class RoleFilter implements Filter {

    private HttpServletRequest httpRequest;
    private static final String[] userRoleRequiredURLs = {
        "/statistics",
        "/users",
        "/logs",
        "/settings",
        "/audit",
    };

    private static final String[] empRoleRequiredURLs = {
        "/statistics",
        "/logs",
        "/settings",
        "/audit"
    };

    private boolean isRoleRequired(String[] requiredURLs) {
        String requestURL = httpRequest.getRequestURL().toString();

        for (String loginRequiredURL : requiredURLs) {
            if (requestURL.contains(loginRequiredURL)) {
                return true;
            }
        }

        return false;
    }

    ;
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        httpResponse.setHeader("Pragma", "no-cache");

        HttpSession session = httpRequest.getSession();

        User user = (User) session.getAttribute("user");

        if (user != null) {
            if (user.getRole().equals("pet owner") && isRoleRequired(userRoleRequiredURLs)) {
                httpRequest.getRequestDispatcher("/bookings").forward(request, response);
                return;
            } 
            
            if (user.getRole().equals("employee") && isRoleRequired(empRoleRequiredURLs)) {
                httpRequest.getRequestDispatcher("/users").forward(request, response);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {

    }

}
