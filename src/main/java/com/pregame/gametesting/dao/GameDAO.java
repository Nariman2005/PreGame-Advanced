 package com.pregame.gametesting.dao;


 import com.pregame.gametesting.model.Game;
 import com.pregame.gametesting.model.GameDeveloper;
 import com.pregame.gametesting.util.DBConnectionManager;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types; // For setting nulls
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal; // For game size

 public class GameDAO extends BaseDAO { // Extend BaseDAO to inherit common DB methods

    private Game mapResultSetToGame(ResultSet rs) throws SQLException {
        Game game = new Game();
        game.setGameId(rs.getInt("GameID"));
        game.setTitle(rs.getString("Title"));
        game.setReleaseDate(rs.getDate("ReleaseDate"));
        game.setEsrbRating(rs.getString("ESRB")); // Column name is ESRB
        game.setType(rs.getString("Type"));
        game.setSize(rs.getBigDecimal("Size")); // Use getBigDecimal for DECIMAL column
        game.setVersion(rs.getString("Version"));
        game.setGameDeveloperId(rs.getInt("GameDeveloperID"));
        game.setDescription(rs.getString("DescriptionofGame")); // Map this column
        return game;
    }

    /**
     * Inserts a new game into the database and sets the auto-generated GameID on the game object.
     * @param game The Game object to insert.
     * @return The generated GameID if successful, -1 otherwise.
     * @throws SQLException if a database access error occurs.
     */
    public int insertGame(Game game) throws SQLException {
        // Added DescriptionofGame
        String sql = "INSERT INTO Game (Title, ReleaseDate, ESRB, Type, Size, Version, GameDeveloperID, DescriptionofGame) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;
        int gameId = -1;
    
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    
            stmt.setString(1, game.getTitle());
            if (game.getReleaseDate() != null) {
                stmt.setDate(2, new java.sql.Date(game.getReleaseDate().getTime()));
            } else {
                stmt.setNull(2, Types.DATE);
            }
            stmt.setString(3, game.getEsrbRating());
            stmt.setString(4, game.getType());
            stmt.setBigDecimal(5, game.getSize()); // Use setBigDecimal
            stmt.setString(6, game.getVersion());
            stmt.setInt(7, game.getGameDeveloperId());
            stmt.setString(8, game.getDescription()); // Set description
    
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    gameId = generatedKeys.getInt(1);
                    game.setGameId(gameId); // Set the ID back on the object
                }
            }
        } finally {
            closeResources(conn, stmt, generatedKeys);
        }
        return gameId;
    }
    
    public Game getGameById(int gameId) throws SQLException {
        String sql = "SELECT GameID, Title, ReleaseDate, ESRB, Type, Size, Version, GameDeveloperID, DescriptionofGame FROM Game WHERE GameID = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Game game = null;
    
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, gameId);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                game = this.mapResultSetToGame(rs);
            }
        } finally {
            closeResources(conn, stmt, rs);
        }
        return game;
    }

    public List<Game> getAllGames() throws SQLException {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT GameID, Title, ReleaseDate, ESRB, Type, Size, Version, GameDeveloperID, DescriptionofGame FROM Game";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
    
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
    
            while (rs.next()) {
                games.add(this.mapResultSetToGame(rs));
            }
        } finally {
            closeResources(conn, stmt, rs);
        }
        return games;
    }
    
    public boolean updateGame(Game game) throws SQLException {
        // Added DescriptionofGame
        String sql = "UPDATE Game SET Title=?, ReleaseDate=?, ESRB=?, Type=?, Size=?, Version=?, GameDeveloperID=?, DescriptionofGame=? WHERE GameID=?";
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;
    
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
    
            stmt.setString(1, game.getTitle());
            if (game.getReleaseDate() != null) {
                stmt.setDate(2, new java.sql.Date(game.getReleaseDate().getTime()));
            } else {
                stmt.setNull(2, Types.DATE);
            }
            stmt.setString(3, game.getEsrbRating());
            stmt.setString(4, game.getType());
            stmt.setBigDecimal(5, game.getSize()); // Use setBigDecimal
            stmt.setString(6, game.getVersion());
            stmt.setInt(7, game.getGameDeveloperId());
            stmt.setString(8, game.getDescription()); // Update description
            stmt.setInt(9, game.getGameId());
    
            success = stmt.executeUpdate() > 0;
        } finally {
            closeResources(conn, stmt, null);
        }
        return success;
    }
    
    public boolean deleteGame(int gameId) throws SQLException {
        String sql = "DELETE FROM Game WHERE GameID=?";
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;
    
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, gameId);
            success = stmt.executeUpdate() > 0;
        } finally {
            closeResources(conn, stmt, null);
        }
        return success;
    }
    
    public List<Game> getGamesByDeveloper(int developerId) throws SQLException {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT GameID, Title, ReleaseDate, ESRB, Type, Size, Version, GameDeveloperID, DescriptionofGame FROM Game WHERE GameDeveloperID = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
    
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, developerId);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                games.add(this.mapResultSetToGame(rs));
            }
        } finally {
            closeResources(conn, stmt, rs);
        }
        return games;
    }

    // --- Main method for testing (simplified and corrected) ---
//    public static void main(String[] args) {
//        GameDAO gameDAO = new GameDAO();
//
//
//        int testDeveloperId = 1;
//        System.out.println("Using Developer ID: " + testDeveloperId + " for game tests.");
//
//
//        Game testGame = null;
//        int generatedGameId = -1;
//
//        try {
//            // 1. Test Insert
//            System.out.println("\n--- Testing Insert Game ---");
//            Game newGame = new Game(
//                    "Epic Adventure IV",
//                    new java.util.Date(),
//                    "M",
//                    "RPG",
//                    new BigDecimal("45.50"), // Size
//                    "1.0.0",
//                    "A grand new adventure in a vast world.", // Description
//                    testDeveloperId // Developer ID
//            );
//            generatedGameId = gameDAO.insertGame(newGame);
//            if (generatedGameId != -1) {
//                System.out.println("Game inserted successfully with ID: " + generatedGameId + " (Object ID: " + newGame.getGameId() + ")");
//                testGame = newGame; // Keep a reference
//            } else {
//                System.err.println("Game insertion failed.");
//                return; // Stop test if insert fails
//            }
//
//            // 2. Test Get Game By ID
//            System.out.println("\n--- Testing Get Game By ID (" + generatedGameId + ") ---");
//            Game retrievedGame = gameDAO.getGameById(generatedGameId);
//            if (retrievedGame != null) {
//                System.out.println("Retrieved: " + retrievedGame.getTitle() + ", Version: " + retrievedGame.getVersion() + ", Desc: " + retrievedGame.getDescription());
//            } else {
//                System.err.println("Failed to retrieve game by ID.");
//            }
//
//            // 3. Test Get All Games
//            System.out.println("\n--- Testing Get All Games ---");
//            List<Game> allGames = gameDAO.getAllGames();
//            System.out.println("Total games found: " + allGames.size());
//            for (Game g : allGames) {
//                if (g.getGameId() == generatedGameId) {
//                    System.out.println("Found our test game in list: " + g.getTitle());
//                }
//            }
//
//            // 4. Test Update Game
//            if (testGame != null) {
//                System.out.println("\n--- Testing Update Game (ID: " + testGame.getGameId() + ") ---");
//                testGame.setVersion("1.0.1");
//                testGame.setDescription("An updated grand adventure with new features!");
//                testGame.setSize(new BigDecimal("47.25"));
//                boolean updated = gameDAO.updateGame(testGame);
//                System.out.println("Update successful: " + updated);
//                if (updated) {
//                    Game verifiedUpdate = gameDAO.getGameById(testGame.getGameId());
//                    System.out.println("Verified Update - Version: " + verifiedUpdate.getVersion() + ", Desc: " + verifiedUpdate.getDescription() + ", Size: " + verifiedUpdate.getSize());
//                }
//            }
//
//            // 5. Test Get Games By Developer
//            System.out.println("\n--- Testing Get Games By Developer ID (" + testDeveloperId + ") ---");
//            List<Game> devGames = gameDAO.getGamesByDeveloper(testDeveloperId);
//            System.out.println("Found " + devGames.size() + " games for developer " + testDeveloperId);
//            for (Game g : devGames) {
//                System.out.println(" - " + g.getTitle() + " (ID: " + g.getGameId() + ")");
//            }
//
//
//            // 6. Test Delete Game
//            if (testGame != null) {
//                System.out.println("\n--- Testing Delete Game (ID: " + testGame.getGameId() + ") ---");
//                boolean deleted = gameDAO.deleteGame(testGame.getGameId());
//                System.out.println("Delete successful: " + deleted);
//                if (deleted) {
//                    Game shouldBeNull = gameDAO.getGameById(testGame.getGameId());
//                    System.out.println("Game retrieved after delete: " + (shouldBeNull == null ? "null (Correct)" : "Exists (Incorrect)"));
//                }
//            }
//
//        } catch (SQLException e) {
//            System.err.println("GameDAO Test SQL Error: " + e.getMessage());
//            e.printStackTrace();
//        } catch (Exception e) {
//            System.err.println("GameDAO Test General Error: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
}