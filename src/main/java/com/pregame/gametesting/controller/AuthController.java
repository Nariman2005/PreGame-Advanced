package com.pregame.gametesting.controller;


import com.pregame.gametesting.model.User;
import com.pregame.gametesting.model.Gamer;
import com.pregame.gametesting.model.GameDeveloper;
import com.pregame.gametesting.model.Tester;
import com.pregame.gametesting.model.Admin;
import com.pregame.gametesting.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/auth/*")
public class AuthController extends HttpServlet {

    private UserService userService;

    public AuthController() {
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "login"; // Default action
        }
        
        switch (action) {
            case "login":
                showLoginForm(request, response);
                break;
            case "register":
                String type = request.getParameter("type");
                showRegistrationForm(request, response, type);
                break;
            case "logout":
                logout(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        
        switch (action) {
            case "login":
                processLogin(request, response);
                break;
            case "register":
                processRegistration(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/");
                break;
        }
    }
    
    private void showLoginForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/auth/login.jsp").forward(request, response);
    }
    
    private void showRegistrationForm(HttpServletRequest request, HttpServletResponse response, String userType)
            throws ServletException, IOException {
        
        if (userType == null) {
            // Show registration options page
            request.getRequestDispatcher("/WEB-INF/jsp/auth/register_choice.jsp").forward(request, response);
            return;
        }
        
        // Set user type in request for form population
        request.setAttribute("userType", userType);
        
        switch (userType) {
            case "gamer":
                request.getRequestDispatcher("/WEB-INF/jsp/auth/gamer_register.jsp").forward(request, response);
                break;
            case "developer":
                request.getRequestDispatcher("/WEB-INF/jsp/auth/developer_register.jsp").forward(request, response);
                break;
            case "tester":
                request.getRequestDispatcher("/WEB-INF/jsp/auth/tester_register.jsp").forward(request, response);
                break;
            default:
                // Invalid user type, redirect to registration choice
                response.sendRedirect(request.getContextPath() + "/auth?action=register");
                break;
        }
    }
    
    private void processLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        // Validate input
        if (email == null || password == null || email.trim().isEmpty() || password.trim().isEmpty()) {
            request.setAttribute("error", "Email and password are required");
            request.getRequestDispatcher("/WEB-INF/jsp/auth/login.jsp").forward(request, response);
            return;
        }
        
        try {
            User user = userService.authenticate(email, password);
            
            if (user != null) {
                // Authentication successful
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("userType", user.getUserType());
                session.setAttribute("userId", user.getId());
                session.setAttribute("userName", user.getName());
                
                // Update last login time
//                userService.updateLastLogin(user.getId(), user.getUserType());
                
                // Ensure redirect to browse page after successful login
                response.sendRedirect(request.getContextPath() + "/games/browse");
            } else {
                // Authentication failed
                request.setAttribute("error", "Invalid email or password");
                request.getRequestDispatcher("/WEB-INF/jsp/auth/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Login failed: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/auth/login.jsp").forward(request, response);
        }
    }
    
    private void processRegistration(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String userType = request.getParameter("userType");
        
        if (userType == null) {
            response.sendRedirect(request.getContextPath() + "/auth?action=register");
            return;
        }
        
        try {
            User user = createUserFromRequest(request, userType);
            
            if (user != null) {
                int userId = userService.registerUser(user);
                
                if (userId > 0) {
                    // Registration successful
                    request.setAttribute("success", "Registration successful! Please log in.");
                    request.getRequestDispatcher("/WEB-INF/jsp/auth/login.jsp").forward(request, response);
                } else {
                    // Registration failed
                    request.setAttribute("error", "Registration failed");
                    showRegistrationForm(request, response, userType);
                }
            } else {
                // Invalid user data
                request.setAttribute("error", "Invalid registration data");
                showRegistrationForm(request, response, userType);
            }
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            showRegistrationForm(request, response, userType);
        } catch (Exception e) {
            request.setAttribute("error", "Registration failed: " + e.getMessage());
            showRegistrationForm(request, response, userType);
        }
    }
    
    private User createUserFromRequest(HttpServletRequest request, String userType) {
        // Get common user fields
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String countryCode = request.getParameter("countryCode");
        String telephone = request.getParameter("telephone");
        
        // Try to parse age
        int age = 0;
        try {
            age = Integer.parseInt(request.getParameter("age"));
        } catch (NumberFormatException e) {
            // Age will remain 0
        }
        
        // Create specific user type
        User user = null;
        
        switch (userType) {
            case "gamer":
                Gamer gamer = new Gamer(name, password, email);
                gamer.setAge(age);
                gamer.setCountryCode(countryCode);
                gamer.setTelephone(telephone);
                
                // Try to parse level
                int level = 1; // Default level
                try {
                    level = Integer.parseInt(request.getParameter("level"));
                } catch (NumberFormatException e) {
                    // Level will remain 1
                }
                gamer.setLevel(level);
                
                user = gamer;
                break;
                
            case "developer":
                GameDeveloper developer = new GameDeveloper(name, password, email);
                developer.setAge(age);
                developer.setCountryCode(countryCode);
                developer.setTelephone(telephone);
                developer.setCompanyName(request.getParameter("companyName"));
                
                user = developer;
                break;
                
            case "tester":
                Tester tester = new Tester(name, password, email);
                tester.setAge(age);
                tester.setCountryCode(countryCode);
                tester.setTelephone(telephone);
                tester.setRank(request.getParameter("rank"));
                
                user = tester;
                break;
        }
        
        return user;
    }
    
    private void logout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        
        response.sendRedirect(request.getContextPath() + "/");
    }


}
