package ru.javawebinar.topjava.web;

import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Sidorov Alexander on 08.08.17.
 * e-mail: asidorov84@gmail.com
 */
public class MealServlet extends HttpServlet {
    private final static Logger logger = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("redirect to meals");
        response.sendRedirect("meals.jsp");
    }
}
