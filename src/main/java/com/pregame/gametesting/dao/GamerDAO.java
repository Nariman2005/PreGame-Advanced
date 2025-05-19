
package com.pregame.gametesting.dao;

import com.pregame.gametesting.model.Gamer;
import com.pregame.gametesting.util.DBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GamerDAO extends BaseDAO {
    private Gamer mapResultSetToGamer(ResultSet rs) throws SQLException {
        Gamer gamer = new Gamer();
        gamer.setGamerId(rs.getInt("GamerID"));
        gamer.setName(rs.getString("Name"));
        gamer.setPassword(rs.getString("Password"));
        gamer.setAge(rs.getInt("Age"));
        gamer.setCountryCode(rs.getString("CountryCode"));
        gamer.setTelephone(rs.getString("Telephone"));
        gamer.setLevel(rs.getInt("Level"));
        gamer.setEmail(rs.getString("email"));
        return gamer;
    }

    public void insertGamer(Gamer gamer) throws SQLException {
        String sql = "INSERT INTO Gamer (Name, Password, Age, CountryCode, Telephone, Level, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    
            stmt.setString(1, gamer.getName());
            stmt.setString(2, gamer.getPassword());
            stmt.setInt(3, gamer.getAge());
            stmt.setString(4, gamer.getCountryCode());
            stmt.setString(5, gamer.getTelephone());
            stmt.setInt(6, gamer.getLevel());
            stmt.setString(7, gamer.getEmail());
    
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating gamer failed, no rows affected.");
            }
    
            // Get the generated ID and set it to the gamer object
            generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                gamer.setGamerId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Insertion Failed", e);
        } finally {
            closeResources(conn, stmt, generatedKeys);
        }
    }

    public Gamer getGamerById(int gamerId) throws SQLException {
        String sql = "SELECT * FROM Gamer WHERE GamerID = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Gamer gamer = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, gamerId);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                gamer = mapResultSetToGamer(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find Gamer", e);
        } finally {
            closeResources(conn, stmt, rs);
        }
        
        return gamer;
    }

    public Gamer getGamerByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM Gamer WHERE email = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Gamer gamer = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                gamer = mapResultSetToGamer(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get Gamer by email", e);
        } finally {
            closeResources(conn, stmt, rs);
        }
        
        return gamer;
    }

    public List<Gamer> getAllGamers() throws SQLException {
        List<Gamer> gamers = new ArrayList<>();
        String sql = "SELECT * FROM Gamer";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                gamers.add(mapResultSetToGamer(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all Gamers", e);
        } finally {
            closeResources(conn, stmt, rs);
        }
        
        return gamers;
    }

    public boolean updateGamer(Gamer gamer) throws SQLException {
        String sql = "UPDATE Gamer SET Name=?, Password=?, Age=?, CountryCode=?, Telephone=?, Level=?, email=? WHERE GamerID=?";
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, gamer.getName());
            stmt.setString(2, gamer.getPassword());
            stmt.setInt(3, gamer.getAge());
            stmt.setString(4, gamer.getCountryCode());
            stmt.setString(5, gamer.getTelephone());
            stmt.setInt(6, gamer.getLevel());
            stmt.setString(7, gamer.getEmail());
            stmt.setInt(8, gamer.getGamerId());
            
            success = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update Gamer", e);
        } finally {
            closeResources(conn, stmt, null);
        }
        
        return success;
    }

    public boolean deleteGamer(int gamerId) throws SQLException {
        String sql = "DELETE FROM Gamer WHERE GamerID=?";
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, gamerId);
            success = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete Gamer", e);
        } finally {
            closeResources(conn, stmt, null);
        }
        
        return success;
    }


    public Gamer getGamerByEmailAndPassword(String email, String password) throws SQLException {
        String sql = "SELECT * FROM Gamer WHERE email = ? AND Password = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Gamer gamer = null;
    
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
    
            if (rs.next()) {
                gamer = mapResultSetToGamer(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to authenticate gamer", e);
        } finally {
            closeResources(conn, stmt, rs);
        }
    
        return gamer;
    }
        public static void main(String[] args) {
            GamerDAO dao = new GamerDAO();

            try {
                // Test insert
                Gamer newGamer = new Gamer("TestUser", "password123", "test@example.com");
                System.out.println("Inserting new gamer...");
                dao.insertGamer(newGamer);
                System.out.println("Inserted gamer with ID: " + newGamer.getGamerId());

                // Test get by ID
                System.out.println("\nTesting getGamerById...");
                Gamer retrievedGamer = dao.getGamerById(newGamer.getGamerId());
                System.out.println("Retrieved: " + retrievedGamer);

                // Test get by email
                System.out.println("\nTesting getGamerByEmail...");
                Gamer retrievedByEmail = dao.getGamerByEmail("test@example.com");
                System.out.println("Retrieved by email: " + retrievedByEmail);

                // Test update
                System.out.println("\nTesting update...");
                retrievedGamer.setLevel(2);
                retrievedGamer.setAge(26);
                boolean updated = dao.updateGamer(retrievedGamer);
                System.out.println("Update successful: " + updated);

                // Verify update
                Gamer afterUpdate = dao.getGamerById(retrievedGamer.getGamerId());
                System.out.println("After update: " + afterUpdate);

                // Test get all
                System.out.println("\nTesting getAllGamers...");
                List<Gamer> allGamers = dao.getAllGamers();
                System.out.println("Total gamers: " + allGamers.size());
                for (Gamer g : allGamers) {
                    System.out.println(" - " + g);
                }

                // Test delete
                System.out.println("\nTesting delete...");
                boolean deleted = dao.deleteGamer(newGamer.getGamerId());
                System.out.println("Delete successful: " + deleted);

                // Verify delete
                Gamer shouldBeNull = dao.getGamerById(newGamer.getGamerId());
                System.out.println("After delete, gamer exists: " + (shouldBeNull != null));

            } catch (SQLException e) {
                System.err.println("Test failed with error:");
                e.printStackTrace();
            }

    }
}