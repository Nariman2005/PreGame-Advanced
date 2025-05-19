package com.pregame.gametesting.controller;

import com.pregame.gametesting.model.Game;
import com.pregame.gametesting.model.GameDeveloper;
import com.pregame.gametesting.service.GameService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // List all games
                List<Game> games = gameService.getAllGames();
                request.setAttribute("games", games);
                request.getRequestDispatcher("/WEB-INF/jsp/games/list.jsp").forward(request, response);
            } else if (pathInfo.startsWith("/view/")) {
                // View a specific game
                int gameId = Integer.parseInt(pathInfo.substring(6));
                Game game = gameService.getGameById(gameId);

                if (game != null) {
                    request.setAttribute("game", game);
                    request.getRequestDispatcher("/WEB-INF/jsp/games/view.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } else if (pathInfo.equals("/new")) {
                // Show form to create a new game
                request.getRequestDispatcher("/WEB-INF/jsp/games/form.jsp").forward(request, response);
            } else if (pathInfo.equals("/search")) {
                // Search for games
                String searchTerm = request.getParameter("term");
                List<Game> searchResults = gameService.searchGames(searchTerm);

                request.setAttribute("games", searchResults);
                request.setAttribute("searchTerm", searchTerm);
                request.getRequestDispatcher("/WEB-INF/jsp/games/search.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            throw new ServletException("Error processing request", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // Create a new game
                Game game = new Game();
                populateGameFromRequest(game, request);

                int gameId = gameService.createGame(game);

                if (gameId > 0) {
                    response.sendRedirect(request.getContextPath() + "/games/view/" + gameId);
                } else {
                    request.setAttribute("error", "Failed to create game");
                    request.setAttribute("game", game);
                    request.getRequestDispatcher("/WEB-INF/jsp/games/form.jsp").forward(request, response);
                }
            } else if (pathInfo.startsWith("/edit/")) {
                // Update an existing game
                int gameId = Integer.parseInt(pathInfo.substring(6));
                Game game = gameService.getGameById(gameId);

                if (game != null) {
                    populateGameFromRequest(game, request);
                    boolean success = gameService.updateGame(game);

                    if (success) {
                        response.sendRedirect(request.getContextPath() + "/games/view/" + gameId);
                    } else {
                        request.setAttribute("error", "Failed to update game");
                        request.setAttribute("game", game);
                        request.getRequestDispatcher("/WEB-INF/jsp/games/form.jsp").forward(request, response);
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } else if (pathInfo.startsWith("/delete/")) {
                // Delete a game
                int gameId = Integer.parseInt(pathInfo.substring(8));
                boolean success = gameService.deleteGame(gameId);

                if (success) {
                    response.sendRedirect(request.getContextPath() + "/games");
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            throw new ServletException("Error processing request", e);
        }
    }

    private void populateGameFromRequest(Game game, HttpServletRequest request) throws ParseException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String version = request.getParameter("version");
        String esrbRating = request.getParameter("esrbRating");
        String type = request.getParameter("type");
        String sizeStr = request.getParameter("size");
        String developerIdStr = request.getParameter("developerId");
        String releaseDateStr = request.getParameter("releaseDate");

        game.setTitle(title);
        game.setDescription(description);
        game.setVersion(version);
        game.setEsrbRating(esrbRating);
        game.setType(type);

        if (sizeStr != null && !sizeStr.isEmpty()) {
            game.setSize(new BigDecimal(sizeStr));
        }

        if (developerIdStr != null && !developerIdStr.isEmpty()) {
            game.setGameDeveloperId(Integer.parseInt(developerIdStr));
        }

        if (releaseDateStr != null && !releaseDateStr.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date releaseDate = dateFormat.parse(releaseDateStr);
            game.setReleaseDate(releaseDate);
        }
    }
}