package com.pregame.gametesting.dao;

import com.pregame.gametesting.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDAO extends BaseDAO {

    /**
     * Authenticate a user by email and password
     *
     * @param email    User's email
     * @param password User's password
     * @return User object if authentication successful, null otherwise
     */
    public User authenticate(String email, String password) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        User user = null;

        try {
            conn = getConnection();

            // First check if user exists in any user table
            String userTypeQuery = "SELECT 'GAMER' as user_type FROM gamer WHERE email = ? " +
                    "UNION SELECT 'GameDeveloper' as user_type FROM gamedeveloper WHERE email = ? " +
                    "UNION SELECT 'TESTER' as user_type FROM tester WHERE email = ? " +
                    "LIMIT 1";

            stmt = conn.prepareStatement(userTypeQuery);
            stmt.setString(1, email);
            stmt.setString(2, email);
            stmt.setString(3, email);

            rs = stmt.executeQuery();

            if (rs.next()) {
                String userType = rs.getString("user_type");
                closeResultSet(rs);
                closeStatement(stmt);

                // Based on the user type, query the specific user table
                switch (userType) {
                    case "GAMER":
                        GamerDAO gamerDAO = new GamerDAO();
                        user = gamerDAO.getGamerByEmailAndPassword(email, password);
                        break;
                    case "DEVELOPER":
                        GameDeveloperDAO devDAO = new GameDeveloperDAO();
                        user = devDAO.getGameDeveloperByEmailAndPassword(email, password);
                        break;
                    case "TESTER":
                        TesterDAO testerDAO = new TesterDAO();
                        user = testerDAO.getTesterByEmailAndPassword(email, password);
                        break;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error authenticating user: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return user;
    }

    /**
     * Check if email is already registered
     *
     * @param email Email to check
     * @return true if email exists, false otherwise
     */
    public boolean emailExists(String email) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean exists = false;

        try {
            conn = getConnection();

            String query = "SELECT 1 FROM gamer WHERE email = ? " +
                    "UNION SELECT 1 FROM gamedeveloper WHERE email = ? " +
                    "UNION SELECT 1 FROM tester WHERE email = ? " +
                    "LIMIT 1";

            stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, email);
            stmt.setString(3, email);

            rs = stmt.executeQuery();
            exists = rs.next();

        } catch (SQLException e) {
            System.err.println("Error checking if email exists: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return exists;
    }

    /**
     * Check if username is already registered
     *
     * @param username Username to check
     * @return true if username exists, false otherwise
     */
    public boolean usernameExists(String username) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean exists = false;

        try {
            conn = getConnection();

            String query = "SELECT 1 FROM gamer WHERE username = ? " +
                    "UNION SELECT 1 FROM gamedeveloper WHERE username = ? " +
                    "UNION SELECT 1 FROM tester WHERE username = ? " +
                    "LIMIT 1";

            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, username);
            stmt.setString(3, username);

            rs = stmt.executeQuery();
            exists = rs.next();

        } catch (SQLException e) {
            System.err.println("Error checking if username exists: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return exists;
    }

    /**
     * Update the last login time for a user
     *
     * @param userId   User ID
     * @param userType Type of user (GAMER, DEVELOPER, TESTER)
     * @return true if update successful, false otherwise
//     */
//    public boolean updateLastLogin(int userId, String userType) {
//        Connection conn = null;
//        PreparedStatement stmt = null;
//        boolean success = false;
//
//        try {
//            conn = getConnection();
//
//            String tableName;
//            String idColumnName;
//
//            switch (userType) {
//                case User.TYPE_GAMER:
//                    tableName = "gamer";
//                    idColumnName = "gamer_id";
//                    break;
//                case User.TYPE_DEVELOPER:
//                    tableName = "gamedeveloper";
//                    idColumnName = "developer_id";
//                    break;
//                case User.TYPE_TESTER:
//                    tableName = "tester";
//                    idColumnName = "tester_id";
//                    break;
//                default:
//                    return false;
//            }
//
//            String query = "UPDATE " + tableName + " SET last_login_date = NOW() WHERE " + idColumnName + " = ?";
//
//            stmt = conn.prepareStatement(query);
//            stmt.setInt(1, userId);
//
//            int rowsAffected = stmt.executeUpdate();
//            success = (rowsAffected > 0);
//
//        } catch (SQLException e) {
//            System.err.println("Error updating last login: " + e.getMessage());
//        } finally {
//            closeStatement(stmt);
//            closeConnection(conn);
//        }
//
//        return success;
//    }

    /**
     * Get user by ID and type
     *
     * @param userId   User ID
     * @param userType Type of user (GAMER, DEVELOPER, TESTER)
     * @return User object if found, null otherwise
     */
    public User getUserById(int userId, String userType) throws SQLException {
        User user = null;

        switch (userType) {
            case User.TYPE_GAMER:
                GamerDAO gamerDAO = new GamerDAO();
                user = gamerDAO.getGamerById(userId);
                break;
            case User.TYPE_DEVELOPER:
                GameDeveloperDAO devDAO = new GameDeveloperDAO();
                user = devDAO.getGameDeveloperById(userId);
                break;
            case User.TYPE_TESTER:
                TesterDAO testerDAO = new TesterDAO();
                user = testerDAO.getTesterById(userId);
                break;
        }

        return user;
    }

    /**
     * Delete a user based on their ID and type
     *
     * @param userId   The ID of the user to delete
     * @param userType The type of user (GAMER, DEVELOPER, TESTER)
     * @return true if deletion was successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean deleteUser(int userId, String userType) throws SQLException {
        boolean success = false;

        switch (userType) {
            case User.TYPE_GAMER:
                GamerDAO gamerDAO = new GamerDAO();
                success = gamerDAO.deleteGamer(userId);
                break;
            case User.TYPE_DEVELOPER:
                GameDeveloperDAO devDAO = new GameDeveloperDAO();
                success = devDAO.deleteGameDeveloper(userId);
                break;
            case User.TYPE_TESTER:
                TesterDAO testerDAO = new TesterDAO();
                success = testerDAO.deleteTester(userId);
                break;
            default:
                throw new IllegalArgumentException("Invalid user type: " + userType);
        }

        return success;
    }

    /**
     * Insert a new user into the database based on the user type
     *
     * @param user The user object to insert (Gamer, GameDeveloper, or Tester)
     * @param userType The type of user (User.TYPE_GAMER, User.TYPE_DEVELOPER, User.TYPE_TESTER)
     * @return The generated user ID if successful, -1 otherwise
     * @throws SQLException if a database error occurs
     * @throws IllegalArgumentException if the user type is invalid or the user object class doesn't match the user type
     */

    /**
     * Insert a new user into the database based on the user type
     *
     * @param user     The user object to insert (Gamer, GameDeveloper, Tester, or Admin)
     * @param userType The type of user (User.TYPE_GAMER, User.TYPE_DEVELOPER, User.TYPE_TESTER, User.TYPE_ADMIN)
     * @return The generated user ID if successful, -1 otherwise
     * @throws SQLException             if a database error occurs
     * @throws IllegalArgumentException if the user type is invalid or the user object class doesn't match the user type
     */

    public int insertUser(User user, String userType) throws SQLException {
        int userId = -1;

        switch (userType) {
            case User.TYPE_GAMER:
                if (!(user instanceof Gamer)) {
                    throw new IllegalArgumentException("User object must be of type Gamer when userType is GAMER");
                }
                GamerDAO gamerDAO = new GamerDAO();
                gamerDAO.insertGamer((Gamer) user);
                userId = ((Gamer) user).getGamerId();
                break;

            case User.TYPE_DEVELOPER:
                if (!(user instanceof GameDeveloper)) {
                    throw new IllegalArgumentException("User object must be of type GameDeveloper when userType is DEVELOPER");
                }
                GameDeveloperDAO devDAO = new GameDeveloperDAO();
                userId = devDAO.insertGameDeveloper((GameDeveloper) user);
                break;

            case User.TYPE_TESTER:
                if (!(user instanceof Tester)) {
                    throw new IllegalArgumentException("User object must be of type Tester when userType is TESTER");
                }
                TesterDAO testerDAO = new TesterDAO();
                userId = testerDAO.insertTester((Tester) user);
                break;

            case User.TYPE_ADMIN:
                if (!(user instanceof Admin)) {
                    throw new IllegalArgumentException("User object must be of type Admin when userType is ADMIN");
                }
                AdminDAO adminDAO = new AdminDAO();
                userId = adminDAO.insertAdmin((Admin) user);
                break;

            default:
                throw new IllegalArgumentException("Invalid user type: " + userType);
        }

        return userId;
    }
}

