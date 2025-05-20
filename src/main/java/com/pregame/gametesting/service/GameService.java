package com.pregame.gametesting.service;

import com.pregame.gametesting.dao.GameDAO;
import com.pregame.gametesting.model.Game;

import java.sql.SQLException;
import java.util.List;

/**
 * Service class for game-related operations.
 */
public class GameService {
    private GameDAO gameDAO;
    
    public GameService() {
        this.gameDAO = new GameDAO();
    }
    
    /**
     * Get all games from the database.
     * 
     * @return List of all games
     * @throws Exception if retrieval fails
     */
    public List<Game> getAllGames() throws Exception {
        try {
            return gameDAO.getAllGames();
        } catch (SQLException e) {
            throw new Exception("Failed to retrieve games: " + e.getMessage(), e);
        }
    }
    
    /**
     * Get a game by its ID.
     * 
     * @param gameId Game ID
     * @return Game object if found, null otherwise
     * @throws Exception if retrieval fails
     */
    public Game getGameById(int gameId) throws Exception {
        try {
            return gameDAO.getGameById(gameId);
        } catch (SQLException e) {
            throw new Exception("Failed to retrieve game: " + e.getMessage(), e);
        }
    }
    
    /**
     * Get games by developer ID.
     * 
     * @param developerId Developer ID
     * @return List of games by the developer
     * @throws Exception if retrieval fails
     */
    public List<Game> getGamesByDeveloper(int developerId) throws Exception {
        try {
            return gameDAO.getGamesByDeveloper(developerId);
        } catch (SQLException e) {
            throw new Exception("Failed to retrieve developer's games: " + e.getMessage(), e);
        }
    }
    
    /**
     * Add a new game.
     * 
     * @param game Game object to add
     * @return Generated game ID if successful, -1 otherwise
     * @throws Exception if addition fails
     */
    public int addGame(Game game) throws Exception {
        try {
            return gameDAO.insertGame(game);
        } catch (SQLException e) {
            throw new Exception("Failed to add game: " + e.getMessage(), e);
        }
    }
    
    /**
     * Update an existing game.
     * 
     * @param game Game object to update
     * @return true if update successful, false otherwise
     * @throws Exception if update fails
     */
    public boolean updateGame(Game game) throws Exception {
        try {
            return gameDAO.updateGame(game);
        } catch (SQLException e) {
            throw new Exception("Failed to update game: " + e.getMessage(), e);
        }
    }
    
    /**
     * Delete a game.
     * 
     * @param gameId Game ID to delete
     * @return true if deletion successful, false otherwise
     * @throws Exception if deletion fails
     */
    public boolean deleteGame(int gameId) throws Exception {
        try {
            return gameDAO.deleteGame(gameId);
        } catch (SQLException e) {
            throw new Exception("Failed to delete game: " + e.getMessage(), e);
        }
    }

    /**
     * Get games by developer ID.
     *
     * @param developerId Developer ID
     * @return List of games by the specified developer
     * @throws Exception if retrieval fails
     */
    public List<Game> getGamesByDeveloperId(int developerId) throws Exception {
        try {
            return gameDAO.getGamesByDeveloperId(developerId);
        } catch (SQLException e) {
            throw new Exception("Failed to retrieve developer games: " + e.getMessage(), e);
        }
    }

    /**
     * Get filtered games by type and/or ESRB rating.
     *
     * @param type Game type (can be null or empty)
     * @param esrbRating ESRB rating (can be null or empty)
     * @return List of games matching the filter criteria
     * @throws Exception if retrieval fails
     */
    public List<Game> getFilteredGames(String type, String esrbRating) throws Exception {
        try {
            return gameDAO.getFilteredGames(type, esrbRating);
        } catch (SQLException e) {
            throw new Exception("Failed to retrieve filtered games: " + e.getMessage(), e);
        }
    }
}

