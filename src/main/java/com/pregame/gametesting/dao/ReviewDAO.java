package com.pregame.gametesting.dao;

import com.pregame.gametesting.model.Review;
import com.pregame.gametesting.util.DBConnectionManager;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

    public class ReviewDAO extends BaseDAO{    private Review mapResultSetToReview(ResultSet rs) throws SQLException {
        Review review = new Review();
        review.setReviewText(rs.getString("FeedBackText")); // Using correct column name from DB
        review.setAttachments(rs.getBytes("Attachments"));
        review.setReviewDate(rs.getDate("reviewDate"));
        review.setTesterId(rs.getInt("Tester_ID"));
        review.setGameId(rs.getInt("Game_ID"));
        return review;
    }
    public void insertReview(Review review) throws SQLException {
        String sql = "INSERT INTO Review (Tester_ID, Game_ID, FeedBackText, Attachments, reviewDate) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;

        try {
            conn = DBConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, review.getTesterId());
            stmt.setInt(2, review.getGameId());
            stmt.setString(3, review.getReviewText());
            stmt.setBytes(4, review.getAttachments());
            stmt.setDate(5, new Date(review.getReviewDate().getTime()));

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating review failed, no rows affected.");
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage()); // Log the actual SQL error
            throw new RuntimeException("Insertion Failed: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public Review getReview(int testerId, int gameId) throws SQLException {
        String sql = "SELECT * FROM Review WHERE Tester_ID = ? AND Game_ID = ?";
        Connection conn = null;
        Review review = null;

        try {
            conn = DBConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, testerId);
            stmt.setInt(2, gameId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    review = this.mapResultSetToReview(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find Review: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return review;
    }

    public List<Review> getReviewsByGame(int gameId) throws SQLException {
        List<Review> reviews = new ArrayList<>();
        Connection conn = null;

        try {
            conn = DBConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Review WHERE Game_ID = ?");
            stmt.setInt(1, gameId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    reviews.add(this.mapResultSetToReview(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("getReviewsByGame Failed: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return reviews;
    }

    public List<Review> getReviewsByTester(int testerId) throws SQLException {
        List<Review> reviews = new ArrayList<>();
        Connection conn = null;

        try {
            conn = DBConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Review WHERE Tester_ID = ?");
            stmt.setInt(1, testerId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    reviews.add(this.mapResultSetToReview(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("getReviewsByTester Failed: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return reviews;
    }    public boolean updateReview(Review review) throws SQLException {
        String sql = "UPDATE Review SET FeedBackText=?, Attachments=?, reviewDate=? WHERE Tester_ID=? AND Game_ID=?";
        Connection conn = null;

        try {
            conn = DBConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, review.getReviewText());
            stmt.setBytes(2, review.getAttachments());
            stmt.setDate(3, new Date(review.getReviewDate().getTime()));
            stmt.setInt(4, review.getTesterId());
            stmt.setInt(5, review.getGameId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Update Failed: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public boolean deleteReview(int testerId, int gameId) throws SQLException {
        String sql = "DELETE FROM Review WHERE Tester_ID=? AND Game_ID=?";
        Connection conn = null;

        try {
            conn = DBConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, testerId);
            stmt.setInt(2, gameId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed To delete Review: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

//    public static void main(String[] args) {
//        ReviewDAO dao = new ReviewDAO();
//
//        try {
//            // Create a sample review for testing
//            Review review = new Review();
//
//            review.setTesterId(1);
//            review.setGameId(2);
//            review.setReviewText("This game has good graphics but needs improvements in gameplay.");
//            review.setAttachments(new byte[] { 1, 2, 3, 4 }); // Sample binary data
//            review.setReviewDate(new java.util.Date()); // Current date
//
//            // Test insert
//            System.out.println("Testing insertReview...");
//            dao.insertReview(review);
//            System.out.println("Review inserted successfully");
//
//            // Test get review
//            System.out.println("\nTesting getReview...");
//            Review retrieved = dao.getReview(1, 2); // Use same IDs as inserted
//            if (retrieved != null) {
//                System.out.println("Retrieved review: " + retrieved.getReviewText());
//            } else {
//                System.out.println("No review found");
//            }            // Test get reviews by game
//            System.out.println("\nTesting getReviewsByGame...");
//            List<Review> gameReviews = dao.getReviewsByGame(2); // Added required parameter
//            System.out.println("Found " + gameReviews.size() + " reviews for game ID 2");
//
//            // Test get reviews by tester
//            System.out.println("\nTesting getReviewsByTester...");
//            List<Review> testerReviews = dao.getReviewsByTester(1);
//            System.out.println("Found " + testerReviews.size() + " reviews by tester ID 2");
//
//            // Test update
//            System.out.println("\nTesting updateReview...");
//            if (retrieved != null) {
//                retrieved.setReviewText("Updated review: gameplay is better after the patch");
//                boolean updated = dao.updateReview(retrieved);
//                System.out.println("Review update result: " + updated);
//            }
//
//            // Test delete
//            System.out.println("\nTesting deleteReview...");
//            boolean deleted = dao.deleteReview(1, 2);
//            System.out.println("Review deletion result: " + deleted);
//
//        } catch (SQLException e) {
//            System.err.println("Test failed with error:");
//            e.printStackTrace();
//        }
//    }
}
