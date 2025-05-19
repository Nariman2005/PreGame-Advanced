package com.pregame.gametesting.dao;


import com.pregame.gametesting.model.Tester;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class TesterDAO extends BaseDAO {
    public TesterDAO() {
        // Empty constructor - no need to create connection in constructor
    }

    private Tester mapResultSetToTester(ResultSet rs) throws SQLException {
        Tester tester = new Tester();
        tester.setTesterId(rs.getInt("TesterID"));
        tester.setName(rs.getString("Name"));
        tester.setPassword(rs.getString("Password"));
        tester.setAge(rs.getInt("Age"));
        tester.setCountryCode(rs.getString("CountryCode"));
        tester.setTelephone(rs.getString("Telephone"));
        tester.setEmail(rs.getString("email"));
        tester.setRank(rs.getString("Ranks"));
        return tester;
    }

    public int insertTester(Tester tester) throws SQLException {
        String sql = "INSERT INTO Tester (Name, Password, Age, CountryCode, Telephone, email, Ranks) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;
        int testerId = -1;
    
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, tester.getName());
            stmt.setString(2, tester.getPassword());
            stmt.setInt(3, tester.getAge());
            stmt.setString(4, tester.getCountryCode());
            stmt.setString(5, tester.getTelephone());
            stmt.setString(6, tester.getEmail());
            stmt.setString(7, tester.getRank());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    testerId = generatedKeys.getInt(1);
                    tester.setTesterId(testerId);
                }
            }
        } finally {
            closeResources(conn, stmt, generatedKeys);
        }
        
        return testerId;
    }

    public Tester getTesterById(int testerId) throws SQLException {
        String sql = "SELECT * FROM Tester WHERE TesterID = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Tester tester = null;
    
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, testerId);
            rs = stmt.executeQuery();
    
            if (rs.next()) {
                tester = this.mapResultSetToTester(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find Tester", e);
        } finally {
            closeResources(conn, stmt, rs);
        }
    
        return tester;
    }

    public List<Tester> getAllTesters() throws SQLException {
        List<Tester> testers = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
    
        try {
            conn = getConnection();
            String sql = "SELECT * FROM Tester";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
    
            while(rs.next()) {
                testers.add(this.mapResultSetToTester(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("getAllTesters Failed", e);
        } finally {
            closeResources(conn, stmt, rs);
        }
    
        return testers;
    }
    
    public boolean updateTester(Tester tester) throws SQLException {
        String sql = "UPDATE Tester SET Name=?, Password=?, Age=?, CountryCode=?, Telephone=?, email=?, Ranks=? WHERE TesterID=?";
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;
    
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, tester.getName());
            stmt.setString(2, tester.getPassword());
            stmt.setInt(3, tester.getAge());
            stmt.setString(4, tester.getCountryCode());
            stmt.setString(5, tester.getTelephone());
            stmt.setString(6, tester.getEmail());
            stmt.setString(7, tester.getRank());
            stmt.setInt(8, tester.getTesterId());
            success = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Update Failed", e);
        } finally {
            closeResources(conn, stmt, null);
        }
    
        return success;
    }
    
    public boolean deleteTester(int testerId) throws SQLException {
        String sql = "DELETE FROM Tester WHERE TesterID=?";
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;
    
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, testerId);
            success = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed To delete Tester", e);
        } finally {
            closeResources(conn, stmt, null);
        }
    
        return success;
    }
    
    public Tester getTesterByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM Tester WHERE email = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Tester tester = null;
    
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            rs = stmt.executeQuery();
    
            if (rs.next()) {
                tester = this.mapResultSetToTester(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed To getTesterByEmail", e);
        } finally {
            closeResources(conn, stmt, rs);
        }
    
        return tester;
    }
    
    /**
     * Get tester by email and password for authentication
     * 
     * @param email Tester's email
     * @param password Tester's password
     * @return Tester object if authentication successful, null otherwise
     */
    public Tester getTesterByEmailAndPassword(String email, String password) throws SQLException {
        String sql = "SELECT * FROM Tester WHERE email = ? AND Password = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Tester tester = null;
    
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
    
            if (rs.next()) {
                tester = this.mapResultSetToTester(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to authenticate tester", e);
        } finally {
            closeResources(conn, stmt, rs);
        }
    
        return tester;
    }



//    public static void main(String[] args) {
//        TesterDAO dao = new TesterDAO();
//
//        try {
//            // Test 1: Create a new tester
//            System.out.println("Testing tester creation...");
//            Tester newTester = new Tester();
//            newTester.setName("Test Tester");
//            newTester.setPassword("password123");
//            newTester.setAge(30);
//            newTester.setCountryCode("EG");
//            newTester.setTelephone("+201234567890");
//            newTester.setEmail("test.tester@example.com");
//            newTester.setRank("Junior");
//
//            // Insert the tester
//            dao.insertTester(newTester);
//            System.out.println("Tester created successfully");
//
//            // Test 2: Get all testers
//            System.out.println("\nGetting all testers:");
//            List<Tester> allTesters = dao.getAllTesters();
//            System.out.println("Found " + allTesters.size() + " testers");
//
//            // Find the ID of our test tester for further operations
//            int testerId = -1;
//            for (Tester t : allTesters) {
//                if (t.getEmail().equals("test.tester@example.com")) {
//                    testerId = t.getTesterId();
//                    System.out.println("Our test tester has ID: " + testerId);
//                    break;
//                }
//            }
//
//            if (testerId != -1) {
//                // Test 3: Get tester by ID
//                System.out.println("\nGetting tester by ID " + testerId);
//                Tester retrievedTester = dao.getTesterById(testerId);
//                if (retrievedTester != null) {
//                    System.out.println("Retrieved tester: " + retrievedTester.getName() +
//                                     ", Email: " + retrievedTester.getEmail() +
//                                     ", Rank: " + retrievedTester.getRank());
//
//                    // Test 4: Update tester
//                    System.out.println("\nUpdating tester...");
//                    retrievedTester.setRank("Senior");
//                    boolean updateResult = dao.updateTester(retrievedTester);
//                    System.out.println("Update result: " + updateResult);
//
//                    // Verify update
//                    Tester updatedTester = dao.getTesterById(testerId);
//                    System.out.println("After update - Rank: " + updatedTester.getRank());
//
//                    // Test 5: Get tester by email
//                    System.out.println("\nGetting tester by email...");
//                    Tester emailTester = dao.getTesterByEmail("test.tester@example.com");
//                    if (emailTester != null) {
//                        System.out.println("Found by email: " + emailTester.getName());
//                    } else {
//                        System.out.println("Tester not found by email");
//                    }
//
////                     Test 6: Delete tester
//                    System.out.println("\nDeleting tester...");
//                    boolean deleteResult = dao.deleteTester(testerId);
//                    System.out.println("Delete result: " + deleteResult);
//
//                    // Verify delete
//                    Tester shouldBeNull = dao.getTesterById(testerId);
//                    System.out.println("After delete - Tester exists: " + (shouldBeNull != null));
//                } else {
//                    System.out.println("Could not retrieve tester by ID");
//                }
//            } else {
//                System.out.println("Could not find test tester in the database");
//            }
//
//        } catch (SQLException e) {
//            System.err.println("Test failed with error:");
//            e.printStackTrace();
//        }
//    }
}
