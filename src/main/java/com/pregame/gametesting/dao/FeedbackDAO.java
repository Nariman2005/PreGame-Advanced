 package com.pregame.gametesting.dao;
 
 import com.pregame.gametesting.model.Feedback;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.sql.Types; // For setting nulls
 import java.util.ArrayList;
 import java.util.List;
 
 public class FeedbackDAO extends BaseDAO {
 
    private Feedback mapResultSetToFeedback(ResultSet rs) throws SQLException {
        Feedback feedback = new Feedback();
        feedback.setGamerId(rs.getInt("Gamer_ID")); // Corrected: use setGamerId
        feedback.setGameId(rs.getInt("Game_ID"));   // Corrected: use setGameId
        feedback.setFeedbackText(rs.getString("FeedBackText"));
        feedback.setAttachments(rs.getBytes("Attachments"));
        feedback.setFeedbackDate(rs.getDate("FeedBackDate"));
        return feedback;
    }
 
    public boolean insertFeedback(Feedback feedback) throws SQLException {
        String sql = "INSERT INTO Feedback (Gamer_ID, Game_ID, FeedBackText, Attachments, FeedBackDate) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
 
            stmt.setInt(1, feedback.getGamerId());
            stmt.setInt(2, feedback.getGameId());
            stmt.setString(3, feedback.getFeedbackText());
 
            if (feedback.getAttachments() != null) {
                stmt.setBytes(4, feedback.getAttachments());
            } else {
                stmt.setNull(4, Types.BLOB);
            }
 
            if (feedback.getFeedbackDate() != null) {
                stmt.setDate(5, new java.sql.Date(feedback.getFeedbackDate().getTime()));
            } else {
                stmt.setNull(5, Types.DATE);
            }
 
            int affectedRows = stmt.executeUpdate();
            success = affectedRows > 0;
        } finally {
            closeResources(conn, stmt, null);
        }
        return success;
    }
 
    public Feedback getFeedback(int gamerId, int gameId) throws SQLException {
        String sql = "SELECT Gamer_ID, Game_ID, FeedBackText, Attachments, FeedBackDate FROM Feedback WHERE Gamer_ID = ? AND Game_ID = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Feedback feedback = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, gamerId);
            stmt.setInt(2, gameId);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                feedback = this.mapResultSetToFeedback(rs);
            }
        } finally {
            closeResources(conn, stmt, rs);
        }
        return feedback;
    }
 
    public List<Feedback> getFeedbacksByGame(int gameId) throws SQLException {
        List<Feedback> feedbacks = new ArrayList<>();
        String sql = "SELECT Gamer_ID, Game_ID, FeedBackText, Attachments, FeedBackDate FROM Feedback WHERE Game_ID = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, gameId);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                feedbacks.add(this.mapResultSetToFeedback(rs));
            }
        } finally {
            closeResources(conn, stmt, rs);
        }
        return feedbacks;
    }
 
    public List<Feedback> getFeedbacksByGamer(int gamerId) throws SQLException {
        List<Feedback> feedbacks = new ArrayList<>();
        String sql = "SELECT Gamer_ID, Game_ID, FeedBackText, Attachments, FeedBackDate FROM Feedback WHERE Gamer_ID = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, gamerId);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                feedbacks.add(this.mapResultSetToFeedback(rs));
            }
        } finally {
            closeResources(conn, stmt, rs);
        }
        return feedbacks;
    }
 
    public boolean updateFeedback(Feedback feedback) throws SQLException {
        String sql = "UPDATE Feedback SET FeedBackText=?, Attachments=?, FeedBackDate=? WHERE Gamer_ID=? AND Game_ID=?";
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, feedback.getFeedbackText());
            if (feedback.getAttachments() != null) {
                stmt.setBytes(2, feedback.getAttachments());
            } else {
                stmt.setNull(2, Types.BLOB);
            }
            if (feedback.getFeedbackDate() != null) {
                stmt.setDate(3, new java.sql.Date(feedback.getFeedbackDate().getTime()));
            } else {
                stmt.setNull(3, Types.DATE);
            }
            stmt.setInt(4, feedback.getGamerId());
            stmt.setInt(5, feedback.getGameId());
            
            success = stmt.executeUpdate() > 0;
        } finally {
            closeResources(conn, stmt, null);
        }
        return success;
    }
 
    public boolean deleteFeedback(int gamerId, int gameId) throws SQLException {
        String sql = "DELETE FROM Feedback WHERE Gamer_ID=? AND Game_ID=?";
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, gamerId);
            stmt.setInt(2, gameId);
            success = stmt.executeUpdate() > 0;
        } finally {
            closeResources(conn, stmt, null);
        }
        return success;
    }

//    public static void main(String[] args) {
//        FeedbackDAO dao = new FeedbackDAO();
//        System.out.println("Starting FeedbackDAO tests...");
//
//        // IMPORTANT: For testing, use Gamer and Game IDs that you know exist in your DB
//        // OR, ensure your test setup creates these prerequisite Gamer and Game entries.
//        // The error "Duplicate entry '1-1'" means this combination already exists.
//        // To re-run this test successfully, either:
//        // 1. Delete the existing row for (GamerID=1, GameID=1) from the Feedback table.
//        // 2. Use a different combination of GamerID and GameID for testing.
//
//        int testGamerId = 1; // Ensure Gamer with ID 1 exists
//        int testGameId = 2;  // Ensure Game with ID 2 exists, and Feedback (1,2) does NOT exist.
//
//        Feedback newFeedback = new Feedback(
//                testGamerId,
//                testGameId,
//                "Feedback for Game " + testGameId + " by Gamer " + testGamerId + ". Controls are smooth.",
//                new byte[]{0x0A, 0x0B, 0x0C}, // Sample binary data
//                new java.util.Date() // Current date
//        );
//
//        Feedback retrievedFeedback = null;
//        boolean inserted = false;
//
//        try {
//            // 0. Attempt to delete if it exists from a previous failed run (optional, for easier re-testing)
//            System.out.println("Attempting to pre-delete feedback for (" + testGamerId + "," + testGameId + ") if it exists...");
//            boolean preDeleted = dao.deleteFeedback(testGamerId, testGameId);
//            System.out.println("Pre-deletion result: " + preDeleted);
//
//
//            // 1. Test Insert
//            System.out.println("\nTesting insertFeedback for (" + testGamerId + "," + testGameId + ")");
//            inserted = dao.insertFeedback(newFeedback);
//            if (inserted) {
//                System.out.println("Feedback inserted successfully.");
//            } else {
//                System.err.println("Feedback insertion failed (no rows affected, but no SQL exception). This is unusual for an INSERT.");
//            }
//
//            // 2. Test Get Feedback (only if inserted)
//            if (inserted) {
//                System.out.println("\nTesting getFeedback for (" + testGamerId + "," + testGameId + ")");
//                retrievedFeedback = dao.getFeedback(testGamerId, testGameId);
//                if (retrievedFeedback != null) {
//                    System.out.println("Retrieved: " + retrievedFeedback.getFeedbackText());
//                } else {
//                    System.err.println("Failed to retrieve feedback that was just inserted!");
//                }
//            }
//
//            // 3. Test Get Feedbacks By Game
//            System.out.println("\nTesting getFeedbacksByGame (GameID: " + testGameId + ")");
//            List<Feedback> gameFeedbacks = dao.getFeedbacksByGame(testGameId);
//            System.out.println("Found " + gameFeedbacks.size() + " feedbacks for Game ID " + testGameId + ".");
//            for (Feedback fb : gameFeedbacks) {
//                if (fb.getGamerId() == testGamerId) {
//                    System.out.println(" - Our test feedback found: " + fb.getFeedbackText());
//                }
//            }
//
//
//            // 4. Test Update Feedback (only if retrieved)
//            if (retrievedFeedback != null) {
//                System.out.println("\nTesting updateFeedback for (" + testGamerId + "," + testGameId + ")");
//                retrievedFeedback.setFeedbackText("Updated: The game is even better after the latest patch!");
//                retrievedFeedback.setFeedbackDate(new java.util.Date(System.currentTimeMillis() - 200000)); // slightly older date
//                boolean updated = dao.updateFeedback(retrievedFeedback);
//                System.out.println("Update successful: " + updated);
//                if (updated) {
//                    Feedback verifiedUpdate = dao.getFeedback(testGamerId, testGameId);
//                    System.out.println("Verified Update - Text: '" + verifiedUpdate.getFeedbackText() + "', Date: " + verifiedUpdate.getFeedbackDate());
//                }
//            }
//
//            // 5. Test Delete Feedback (only if inserted)
//            if (inserted) {
//                System.out.println("\nTesting deleteFeedback for (" + testGamerId + "," + testGameId + ")");
//                boolean deleted = dao.deleteFeedback(testGamerId, testGameId);
//                System.out.println("Delete successful: " + deleted);
//                if (deleted) {
//                    Feedback shouldBeNull = dao.getFeedback(testGamerId, testGameId);
//                    System.out.println("Feedback retrieved after delete: " + (shouldBeNull == null ? "null (Correct)" : "Exists (Incorrect)"));
//                }
//            }
//
//        } catch (SQLException e) {
//            System.err.println("\n--- FeedbackDAO Test SQL Error ---");
//            e.printStackTrace();
//            if (e.getMessage().contains("Duplicate entry")) {
//                System.err.println("TIP: The Gamer_ID/Game_ID combination already exists. Manually delete it from the Feedback table or use different IDs for testing.");
//            } else if (e.getMessage().contains("foreign key constraint fails")) {
//                System.err.println("TIP: A foreign key constraint failed. Ensure that Gamer with ID " + testGamerId + " and Game with ID " + testGameId + " exist in their respective tables.");
//            }
//        }
//        System.out.println("\nFeedbackDAO tests finished.");
//    }
}