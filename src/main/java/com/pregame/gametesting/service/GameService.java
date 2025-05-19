package com.pregame.gametesting.service;

import com.pregame.gametesting.dao.GameDAO;
import com.pregame.gametesting.dao.GameDeveloperDAO;
import com.pregame.gametesting.model.Game;
import com.pregame.gametesting.model.GameDeveloper;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing game-related operations
 */
public class GameService {

    private GameDAO gameDAO;
    private GameDeveloperDAO gameDeveloperDAO;

    /**
     * Constructor for GameService
     */
    public GameService() {
        this.gameDAO = new GameDAO();
        this.gameDeveloperDAO = new GameDeveloperDAO();
    }

    /**
     * Create a new game
     *
     * @param game Game object to create
     * @return Game ID if creation successful, -1 otherwise
     * @throws Exception if creation fails
     */
    public int createGame(Game game) throws Exception {
        // Validate game data
        if (!validateGameData(game)) {
            throw new IllegalArgumentException("Invalid game data");
        }

        try {
            // Check if developer exists
            GameDeveloper developer = gameDeveloperDAO.getGameDeveloperById(game.getGameDeveloperId());
            if (developer == null) {
                throw new IllegalArgumentException("Developer does not exist");
            }

            // Create game
            return gameDAO.insertGame(game);

        } catch (SQLException e) {
            throw new Exception("Game creation failed: " + e.getMessage(), e);
        }
    }

    /**
     * Get a game by ID
     *
     * @param gameId Game ID
     * @return Game object if found, null otherwise
     * @throws Exception if retrieval fails
     */
    public Game getGameById(int gameId) throws Exception {
        try {
            Game game = gameDAO.getGameById(gameId);
            if (game != null) {
                // Load developer information
                GameDeveloper developer = gameDeveloperDAO.getGameDeveloperById(game.getGameDeveloperId());
                game.setDeveloper(developer);
            }
            return game;
        } catch (SQLException e) {
            throw new Exception("Game retrieval failed: " + e.getMessage(), e);
        }
    }

    /**
     * Get all games with developer information
     *
     * @return List of all games
     * @throws Exception if retrieval fails
     */
    public List<Game> getAllGames() throws Exception {
        try {
            List<Game> games = gameDAO.getAllGames();

            // Load developer information for each game
            for (Game game : games) {
                GameDeveloper developer = gameDeveloperDAO.getGameDeveloperById(game.getGameDeveloperId());
                game.setDeveloper(developer);
            }

            return games;
        } catch (SQLException e) {
            throw new Exception("Games retrieval failed: " + e.getMessage(), e);
        }
    }

    /**
     * Update an existing game
     *
     * @param game Game object with updated information
     * @return true if update successful, false otherwise
     * @throws Exception if update fails
     */
    public boolean updateGame(Game game) throws Exception {
        // Validate game data
        if (!validateGameData(game) || game.getGameId() <= 0) {
            throw new IllegalArgumentException("Invalid game data or game ID");
        }

        try {
            // Check if the game exists
            Game existingGame = gameDAO.getGameById(game.getGameId());
            if (existingGame == null) {
                throw new IllegalArgumentException("Game does not exist");
            }

            // Check if developer exists if it's being changed
            if (existingGame.getGameDeveloperId() != game.getGameDeveloperId()) {
                GameDeveloper developer = gameDeveloperDAO.getGameDeveloperById(game.getGameDeveloperId());
                if (developer == null) {
                    throw new IllegalArgumentException("Developer does not exist");
                }
            }

            // Update game
            return gameDAO.updateGame(game);

        } catch (SQLException e) {
            throw new Exception("Game update failed: " + e.getMessage(), e);
        }
    }

    /**
     * Delete a game
     *
     * @param gameId Game ID
     * @return true if deletion successful, false otherwise
     * @throws Exception if deletion fails
     */
    public boolean deleteGame(int gameId) throws Exception {
        try {
            // Check if the game exists
            Game existingGame = gameDAO.getGameById(gameId);
            if (existingGame == null) {
                throw new IllegalArgumentException("Game does not exist");
            }

            return gameDAO.deleteGame(gameId);
        } catch (SQLException e) {
            throw new Exception("Game deletion failed: " + e.getMessage(), e);
        }
    }

    /**
     * Get games by developer
     *
     * @param developerId Developer ID
     * @return List of games by the developer
     * @throws Exception if retrieval fails
     */
    public List<Game> getGamesByDeveloper(int developerId) throws Exception {
        try {
            // Check if developer exists
            GameDeveloper developer = gameDeveloperDAO.getGameDeveloperById(developerId);
            if (developer == null) {
                throw new IllegalArgumentException("Developer does not exist");
            }

            List<Game> games = gameDAO.getGamesByDeveloper(developerId);

            // Set developer object for each game
            for (Game game : games) {
                game.setDeveloper(developer);
            }

            return games;
        } catch (SQLException e) {
            throw new Exception("Games retrieval failed: " + e.getMessage(), e);
        }
    }

    /**
     * Filter games by various criteria
     *
     * @param type       Game type (genre)
     * @param esrbRating ESRB rating
     * @param minSize    Minimum game size
     * @param maxSize    Maximum game size
     * @return Filtered list of games
     * @throws Exception if filtering fails
     */
    public List<Game> filterGames(String type, String esrbRating, BigDecimal minSize, BigDecimal maxSize) throws Exception {
        try {
            List<Game> allGames = gameDAO.getAllGames();
            List<Game> filteredGames = new ArrayList<>();

            for (Game game : allGames) {
                boolean matches = true;

                // Filter by type if specified
                if (type != null && !type.isEmpty() && !type.equalsIgnoreCase(game.getType())) {
                    matches = false;
                }

                // Filter by ESRB rating if specified
                if (esrbRating != null && !esrbRating.isEmpty() && !esrbRating.equalsIgnoreCase(game.getEsrbRating())) {
                    matches = false;
                }

                // Filter by minimum size if specified
                if (minSize != null && (game.getSize() == null || game.getSize().compareTo(minSize) < 0)) {
                    matches = false;
                }

                // Filter by maximum size if specified
                if (maxSize != null && (game.getSize() == null || game.getSize().compareTo(maxSize) > 0)) {
                    matches = false;
                }

                if (matches) {
                    // Load developer information
                    GameDeveloper developer = gameDeveloperDAO.getGameDeveloperById(game.getGameDeveloperId());
                    game.setDeveloper(developer);

                    filteredGames.add(game);
                }
            }

            return filteredGames;
        } catch (SQLException e) {
            throw new Exception("Game filtering failed: " + e.getMessage(), e);
        }
    }

    /**
     * Search games by title or description
     *
     * @param searchTerm Search term
     * @return List of games matching the search term
     * @throws Exception if search fails
     */
    public List<Game> searchGames(String searchTerm) throws Exception {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            throw new IllegalArgumentException("Search term cannot be empty");
        }

        try {
            List<Game> allGames = gameDAO.getAllGames();
            List<Game> searchResults = new ArrayList<>();
            String term = searchTerm.toLowerCase();

            for (Game game : allGames) {
                if ((game.getTitle() != null && game.getTitle().toLowerCase().contains(term)) ||
                        (game.getDescription() != null && game.getDescription().toLowerCase().contains(term))) {

                    // Load developer information
                    GameDeveloper developer = gameDeveloperDAO.getGameDeveloperById(game.getGameDeveloperId());
                    game.setDeveloper(developer);

                    searchResults.add(game);
                }
            }

            return searchResults;
        } catch (SQLException e) {
            throw new Exception("Game search failed: " + e.getMessage(), e);
        }
    }

    /**
     * Release a new version of a game
     *
     * @param gameId      Game ID
     * @param newVersion  New version number
     * @param description Updated game description
     * @return true if release successful, false otherwise
     * @throws Exception if release fails
     */
    public boolean releaseNewVersion(int gameId, String newVersion, String description) throws Exception {
        try {
            // Get the existing game
            Game game = gameDAO.getGameById(gameId);
            if (game == null) {
                throw new IllegalArgumentException("Game does not exist");
            }

            // Update game version and description
            game.setVersion(newVersion);
            if (description != null && !description.trim().isEmpty()) {
                game.setDescription(description);
            }

            // Save the updated game
            return gameDAO.updateGame(game);
        } catch (SQLException e) {
            throw new Exception("Game version update failed: " + e.getMessage(), e);
        }
    }

    /**
     * Validate game data
     *
     * @param game Game to validate
     * @return true if data is valid, false otherwise
     */
    private boolean validateGameData(Game game) {
        if (game == null) {
            return false;
        }

        // Title is required
        if (game.getTitle() == null || game.getTitle().trim().isEmpty()) {
            return false;
        }

        // Version is required
        if (game.getVersion() == null || game.getVersion().trim().isEmpty()) {
            return false;
        }

        // Developer ID is required
        if (game.getGameDeveloperId() <= 0) {
            return false;
        }

        // ESRB rating format check (if provided)
        if (game.getEsrbRating() != null && !game.getEsrbRating().matches("^(E|E10\\+|T|M|AO|RP)$")) {
            return false;
        }

        // Size must be positive (if provided)
        if (game.getSize() != null && game.getSize().compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }

        return true;
    }

    /**
     * Get all game types (genres) available in the system
     *
     * @return List of distinct game types
     * @throws Exception if retrieval fails
     */
    public List<String> getAllGameTypes() throws Exception {
        try {
            List<Game> allGames = gameDAO.getAllGames();
            List<String> types = new ArrayList<>();

            for (Game game : allGames) {
                String type = game.getType();
                if (type != null && !type.isEmpty() && !types.contains(type)) {
                    types.add(type);
                }
            }

            return types;
        } catch (SQLException e) {
            throw new Exception("Game types retrieval failed: " + e.getMessage(), e);
        }
    }

    /**
     * Get all ESRB ratings available in the system
     *
     * @return List of distinct ESRB ratings
     * @throws Exception if retrieval fails
     */
    public List<String> getAllEsrbRatings() throws Exception {
        try {
            List<Game> allGames = gameDAO.getAllGames();
            List<String> ratings = new ArrayList<>();

            for (Game game : allGames) {
                String rating = game.getEsrbRating();
                if (rating != null && !rating.isEmpty() && !ratings.contains(rating)) {
                    ratings.add(rating);
                }
            }

            return ratings;
        } catch (SQLException e) {
            throw new Exception("ESRB ratings retrieval failed: " + e.getMessage(), e);
        }
    }
    /**
     * Test method for GameService functionality
     */


    /**
     * Test method for GameService functionality
     */
    public static void main(String[] args) {
        GameService gameService = new GameService();
        GameDeveloperDAO gameDeveloperDAO = new GameDeveloperDAO();

        try {
            // First, find an existing developer to use
            int testDeveloperId = 1; // You may need to adjust this ID
            GameDeveloper developer = null;

            try {
                developer = gameDeveloperDAO.getGameDeveloperById(testDeveloperId);
                System.out.println("Using existing developer: " + developer.getName() + " (ID: " + testDeveloperId + ")");
            } catch (Exception e) {
                System.err.println("Could not find developer with ID " + testDeveloperId);
                System.err.println("Please ensure a valid developer exists in the database");
                return;
            }

            // 1. Test creating a new game
            System.out.println("\n===== TESTING GAME CREATION =====");
            Game newGame = new Game();
            newGame.setTitle("Test Game From Service");
            newGame.setDescription("A game created via the GameService layer");
            newGame.setVersion("1.0.0");
            newGame.setEsrbRating("T");
            newGame.setType("Action");
            newGame.setSize(new BigDecimal("15.75"));
            newGame.setGameDeveloperId(testDeveloperId);
            newGame.setReleaseDate(new java.util.Date());

            System.out.println("Creating new game: " + newGame.getTitle());
            int gameId = gameService.createGame(newGame);
            System.out.println("Game created with ID: " + gameId);

            // 2. Test getting a game by ID
            System.out.println("\n===== TESTING GAME RETRIEVAL =====");
            Game retrievedGame = gameService.getGameById(gameId);
            System.out.println("Retrieved game: " + retrievedGame.getTitle());
            System.out.println("Game details:");
            System.out.println(" - ID: " + retrievedGame.getGameId());
            System.out.println(" - Title: " + retrievedGame.getTitle());
            System.out.println(" - Description: " + retrievedGame.getDescription());
            System.out.println(" - Version: " + retrievedGame.getVersion());
            System.out.println(" - ESRB: " + retrievedGame.getEsrbRating());
            System.out.println(" - Type: " + retrievedGame.getType());
            System.out.println(" - Size: " + retrievedGame.getSize() + " GB");
            System.out.println(" - Developer ID: " + retrievedGame.getGameDeveloperId());

            if (retrievedGame.getDeveloper() != null) {
                System.out.println(" - Developer Name: " + retrievedGame.getDeveloper().getName());
            }

            // 3. Test getting all games
            System.out.println("\n===== TESTING ALL GAMES RETRIEVAL =====");
            List<Game> allGames = gameService.getAllGames();
            System.out.println("Total games in system: " + allGames.size());
            for (Game game : allGames) {
                String developerName = game.getDeveloper() != null ? game.getDeveloper().getName() : "Unknown";
                System.out.println(" - " + game.getTitle() + " (by " + developerName + ")");
            }

            // 4. Test getting games by developer
            System.out.println("\n===== TESTING GAMES BY DEVELOPER =====");
            List<Game> devGames = gameService.getGamesByDeveloper(testDeveloperId);
            System.out.println("Games by developer " + testDeveloperId + ": " + devGames.size());
            for (Game game : devGames) {
                System.out.println(" - " + game.getTitle() + " (ID: " + game.getGameId() + ")");
            }

            // 5. Test updating a game
            System.out.println("\n===== TESTING GAME UPDATE =====");
            retrievedGame.setVersion("1.0.1");
            retrievedGame.setDescription("Updated description with new features");
            retrievedGame.setSize(new BigDecimal("16.50"));

            boolean updateSuccess = gameService.updateGame(retrievedGame);
            System.out.println("Game update successful: " + updateSuccess);

            // Verify update
            Game afterUpdate = gameService.getGameById(gameId);
            System.out.println("After update:");
            System.out.println(" - Version: " + afterUpdate.getVersion());
            System.out.println(" - Description: " + afterUpdate.getDescription());
            System.out.println(" - Size: " + afterUpdate.getSize() + " GB");

            // 6. Test releasing a new version
            System.out.println("\n===== TESTING GAME VERSION RELEASE =====");
            boolean versionSuccess = gameService.releaseNewVersion(gameId, "1.1.0",
                    "Major update with exciting new features and bug fixes");
            System.out.println("Version release successful: " + versionSuccess);

            // Verify version update
            Game afterVersionUpdate = gameService.getGameById(gameId);
            System.out.println("After version update:");
            System.out.println(" - Version: " + afterVersionUpdate.getVersion());
            System.out.println(" - Description: " + afterVersionUpdate.getDescription());

            // 7. Test search function
            System.out.println("\n===== TESTING GAME SEARCH =====");
            String searchTerm = "Test Game";
            List<Game> searchResults = gameService.searchGames(searchTerm);
            System.out.println("Search results for '" + searchTerm + "': " + searchResults.size());
            for (Game game : searchResults) {
                String developerName = game.getDeveloper() != null ? game.getDeveloper().getName() : "Unknown";
                System.out.println(" - " + game.getTitle() + " (by " + developerName + ")");
            }

            // 8. Test filter function
            System.out.println("\n===== TESTING GAME FILTERING =====");
            List<Game> filteredGames = gameService.filterGames("Action", "T",
                    new BigDecimal("10.0"), new BigDecimal("20.0"));
            System.out.println("Filtered games: " + filteredGames.size());
            for (Game game : filteredGames) {
                String developerName = game.getDeveloper() != null ? game.getDeveloper().getName() : "Unknown";
                System.out.println(" - " + game.getTitle() + " (by " + developerName + ")");
                System.out.println("   Type: " + game.getType() + ", ESRB: " + game.getEsrbRating() +
                        ", Size: " + game.getSize() + " GB");
            }

            // 9. Test getting metadata
            System.out.println("\n===== TESTING GAME METADATA =====");
            List<String> gameTypes = gameService.getAllGameTypes();
            System.out.println("Game types in system: " + gameTypes);

            List<String> esrbRatings = gameService.getAllEsrbRatings();
            System.out.println("ESRB ratings in system: " + esrbRatings);

            // 10. Test deleting a game
            System.out.println("\n===== TESTING GAME DELETION =====");
            boolean deleteSuccess = gameService.deleteGame(gameId);
            System.out.println("Game deletion successful: " + deleteSuccess);

            // Verify deletion
            try {
                Game deletedGame = gameService.getGameById(gameId);
                System.out.println("After deletion, game exists: " + (deletedGame != null));
            } catch (Exception e) {
                System.out.println("Game successfully deleted and not found in database");
            }

            // 11. Test validation error handling
            System.out.println("\n===== TESTING ERROR HANDLING =====");
            try {
                Game invalidGame = new Game();
                // Missing required fields
                gameService.createGame(invalidGame);
            } catch (Exception e) {
                System.out.println("Expected error caught: " + e.getMessage());
            }

            try {
                gameService.getGameById(-1); // Invalid ID
            } catch (Exception e) {
                System.out.println("Expected error caught: " + e.getMessage());
            }

        } catch (Exception e) {
            System.err.println("GameService test failed with error:");
            e.printStackTrace();
        }
    }
}