package com.pregame.gametesting.controller;

import com.pregame.gametesting.util.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/auth/*")
public class AuthController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter(Constants.PARAM_ACTION);

        if (action == null) {
            // Default to login if no action specified
            action = Constants.ACTION_LOGIN;
        }

        switch (action) {
            case Constants.ACTION_LOGIN:
                // Forward to login page
                request.getRequestDispatcher(Constants.PATH_LOGIN).forward(request, response);
                break;
            case Constants.ACTION_REGISTER:
                // Get the type parameter
                String type = request.getParameter(Constants.PARAM_TYPE);
                if (type == null) {
                    // If no type, show registration choice page
                    request.getRequestDispatcher(Constants.PATH_REGISTER_CHOICE).forward(request, response);
                } else {
                    // Otherwise, show specific registration page
                    switch (type.toUpperCase()) {
                        case Constants.USER_TYPE_GAMER:
                            request.getRequestDispatcher(Constants.PATH_REGISTER_GAMER).forward(request, response);
                            break;
                        case Constants.USER_TYPE_DEVELOPER:
                            request.getRequestDispatcher(Constants.PATH_REGISTER_DEVELOPER).forward(request, response);
                            break;
                        case Constants.USER_TYPE_TESTER:
                            request.getRequestDispatcher(Constants.PATH_REGISTER_TESTER).forward(request, response);
                            break;
                        default:
                            // Unknown type, show registration choice
                            request.getRequestDispatcher(Constants.PATH_REGISTER_CHOICE).forward(request, response);
                    }
                }
                break;
            case Constants.ACTION_LOGOUT:
                // Invalidate session and redirect to home
                request.getSession().invalidate();
                response.sendRedirect(request.getContextPath() + "/");
                break;
            default:
                // Unknown action, redirect to home
                response.sendRedirect(request.getContextPath() + "/");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter(Constants.PARAM_ACTION);

        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        if (Constants.ACTION_LOGIN.equals(action)) {
            // Get login parameters
            String username = request.getParameter(Constants.PARAM_USERNAME);
            String password = request.getParameter(Constants.PARAM_PASSWORD);

            // TODO: Implement login logic with your UserService

            // For now, just show an error message
            request.setAttribute(Constants.ATTR_ERROR_MESSAGE, "Login functionality not implemented yet");
            request.getRequestDispatcher(Constants.PATH_LOGIN).forward(request, response);
        } else if (Constants.ACTION_REGISTER.equals(action)) {
            // TODO: Implement registration logic

            // For now, redirect to login
            request.setAttribute(Constants.ATTR_INFO_MESSAGE, "Registration functionality not implemented yet");
            request.getRequestDispatcher(Constants.PATH_LOGIN).forward(request, response);
        } else {
            // Unknown action
            response.sendRedirect(request.getContextPath() + "/");
        }
    }
}