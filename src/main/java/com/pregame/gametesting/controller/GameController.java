package com.pregame.gametesting.controller;

import com.pregame.gametesting.model.Game;
import com.pregame.gametesting.service.GameService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/games/*")
public class GameController extends HttpServlet {
    
    private GameService gameService;
    
    public GameController() {
        this.gameService = new GameService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        String action = pathInfo == null ? "browse" : pathInfo.substring(1);

        switch (action) {
            case "browse":
                showBrowsePage(request, response);
                break;
            case "details":
                showGameDetails(request, response);
                break;
            case "test":
                showTestGamePage(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/games/browse");
                break;
        }
    }

    private void showBrowsePage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get filter parameters
            String typeFilter = request.getParameter("type");
            String esrbFilter = request.getParameter("esrbRating");

            List<Game> games;

            // Apply filters if they exist
            if ((typeFilter != null && !typeFilter.isEmpty()) ||
                (esrbFilter != null && !esrbFilter.isEmpty())) {
                games = gameService.getFilteredGames(typeFilter, esrbFilter);
            } else {
                // Get all games if no filters
                games = gameService.getAllGames();
            }

            request.setAttribute("games", games);

            // Set the selected filter values for form persistence
            request.setAttribute("selectedType", typeFilter);
            request.setAttribute("selectedEsrb", esrbFilter);

        } catch (Exception e) {
            // Log the error
            System.err.println("Error retrieving games: " + e.getMessage());
            e.printStackTrace();

            // Create an empty list if there's an error
            request.setAttribute("games", new ArrayList<Game>());
            request.setAttribute("error", "An error occurred while retrieving games. Please try again later.");
        }
        
        // Forward to the Browse_page.jsp
        request.getRequestDispatcher("/WEB-INF/jsp/games/Browse_page.jsp").forward(request, response);
    }

    /**
     * Show the game details page for a specific game
     */
    private void showGameDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get the game ID from the request
            String gameIdParam = request.getParameter("id");

            if (gameIdParam == null || gameIdParam.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/games/browse");
                return;
            }

            int gameId = Integer.parseInt(gameIdParam);
            Game game = gameService.getGameById(gameId);

            if (game == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Game not found");
                return;
            }

            // Set the game in the request
            request.setAttribute("game", game);

            // Forward to the Game.jsp
            request.getRequestDispatcher("/WEB-INF/jsp/games/Game.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/games/browse");
        } catch (Exception e) {
            System.err.println("Error retrieving game details: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving game details");
        }
    }

    /**
     * Show the game testing page when a user clicks "Test Game"
     */
    private void showTestGamePage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get the game ID from the request
            String gameIdParam = request.getParameter("id");

            if (gameIdParam == null || gameIdParam.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/games/browse");
                return;
            }

            int gameId = Integer.parseInt(gameIdParam);
            Game game = gameService.getGameById(gameId);

            if (game == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Game not found");
                return;
            }

            // Set the game in the request
            request.setAttribute("game", game);

            // Check if user is logged in
            Object user = request.getSession().getAttribute("user");
            if (user == null) {
                // Redirect to login page with return URL
                response.sendRedirect(request.getContextPath() +
                    "/auth?action=login&returnUrl=" +
                    request.getContextPath() + "/games/test?id=" + gameId);
                return;
            }

            // Forward to the TestGame.jsp
            request.getRequestDispatcher("/WEB-INF/jsp/games/TestGame.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/games/browse");
        } catch (Exception e) {
            System.err.println("Error retrieving game for testing: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving game for testing");
        }
    }
}
