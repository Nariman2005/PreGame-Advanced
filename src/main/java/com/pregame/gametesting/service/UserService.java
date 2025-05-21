package com.pregame.gametesting.service;

import com.pregame.gametesting.dao.UserDAO;
import com.pregame.gametesting.dao.GamerDAO;
import com.pregame.gametesting.dao.GameDeveloperDAO;
import com.pregame.gametesting.dao.TesterDAO;
import com.pregame.gametesting.dao.AdminDAO;
import com.pregame.gametesting.model.User;
import com.pregame.gametesting.model.Gamer;
import com.pregame.gametesting.model.GameDeveloper;
import com.pregame.gametesting.model.Tester;
import com.pregame.gametesting.model.Admin;

import java.sql.SQLException;

public class UserService {

    private UserDAO userDAO;
    private GamerDAO gamerDAO;
    private GameDeveloperDAO gameDeveloperDAO;
    private TesterDAO testerDAO;
    private AdminDAO adminDAO;

    public UserService() {
        this.userDAO = new UserDAO();
        this.gamerDAO = new GamerDAO();
        this.gameDeveloperDAO = new GameDeveloperDAO();
        this.testerDAO = new TesterDAO();
        this.adminDAO = new AdminDAO();
    }

    /**
     * Authenticate a user with email and password
     *
     * @param email User's email
     * @param password User's password
     * @return User object if authentication successful, null otherwise
     */
    public User authenticate(String email, String password) {
        // You might want to add password hashing here
        // password = PasswordUtil.hashPassword(password);

        return userDAO.authenticate(email, password);
    }

    /**
     * Register a new user
     *
     * @param user User object (Gamer, GameDeveloper, Tester, or Admin)
     * @return User ID if registration successful, -1 otherwise
     * @throws Exception if registration fails
     */
    public int registerUser(User user) throws Exception {
        // Validate user data
        if (!validateUserData(user)) {
            throw new IllegalArgumentException("Invalid user data");
        }

        // Check if email already exists
        if (userDAO.emailExists(user.getEmail())) {
            throw new Exception("Email already registered");
        }

        // Hash password
        // user.setPassword(PasswordUtil.hashPassword(user.getPassword()));

        // Insert user based on type
        try {
            return userDAO.insertUser(user, user.getUserType());
        } catch (SQLException e) {
            throw new Exception("Registration failed: " + e.getMessage());
        }
    }

    /**
     * Update user profile
     *
     * @param user User object with updated information
     * @return true if update successful, false otherwise
     * @throws Exception if update fails
     */
    public boolean updateUser(User user) throws Exception {
        // Validate user data
        if (!validateUserData(user)) {
            throw new IllegalArgumentException("Invalid user data");
        }

        try {
            boolean success = false;
            String userType = user.getUserType();

            switch (userType) {
                case User.TYPE_GAMER:
                    success = gamerDAO.updateGamer((Gamer) user);
                    break;
                case User.TYPE_DEVELOPER:
                    success = gameDeveloperDAO.updateGameDeveloper((GameDeveloper) user);
                    break;
                case User.TYPE_TESTER:
                    success = testerDAO.updateTester((Tester) user);
                    break;
                case User.TYPE_ADMIN:
                    success = adminDAO.updateAdmin((Admin) user);
                    break;
            }

            return success;
        } catch (SQLException e) {
            throw new Exception("Update failed: " + e.getMessage());
        }
    }

    /**
     * Delete user account
     *
     * @param userId User ID
     * @param userType Type of user
     * @return true if deletion successful, false otherwise
     * @throws Exception if deletion fails
     */
    public boolean deleteUser(int userId, String userType) throws Exception {
        try {
            return userDAO.deleteUser(userId, userType);
        } catch (SQLException e) {
            throw new Exception("Deletion failed: " + e.getMessage());
        }
    }

    /**
     * Get user by ID and type
     *
     * @param userId User ID
     * @param userType Type of user
     * @return User object if found, null otherwise
     * @throws Exception if retrieval fails
     */
    public User getUserById(int userId, String userType) throws Exception {
        try {
            return userDAO.getUserById(userId, userType);
        } catch (SQLException e) {
            throw new Exception("User retrieval failed: " + e.getMessage());
        }
    }

    private boolean validateUserData(User user) {
        // Validate email format
        if (user.getEmail() == null || !user.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            return false;
        }

        // Validate password (at least 8 characters)
        if (user.getPassword() == null || user.getPassword().length() < 8) {
            return false;
        }

        // Validate name
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            return false;
        }

        return true;
    }
//
//    public static void main(String[] args) {
//        UserService userService = new UserService();
//
//        try {
//            // Test registration for different user types
////            System.out.println("===== TESTING USER REGISTRATION =====");
////
////            // 1. Register a Gamer
////            System.out.println("\nRegistering a Gamer...");
////            Gamer gamer = new Gamer("Nariman", "yala1222222", "nariman.adel2005@gmail.com");
////            gamer.setAge(20);
////            gamer.setCountryCode("EG");
////            gamer.setTelephone("01141006682");
////            gamer.setLevel(1);
////
////            int gamerId = userService.registerUser(gamer);
////            System.out.println("Gamer registered with ID: " + gamerId);
//
//            // 2. Register a Tester
////            System.out.println("\nRegistering a Tester...");
////            Tester tester = new Tester("Eyad", "password12334567", "eyad@gmail.com");
////            tester.setAge(21);
////            tester.setCountryCode("Eg");
////            tester.setTelephone("011298923");
////            tester.setRank("Junior");
////
////            int testerId = userService.registerUser(tester);
////            System.out.println("Tester registered with ID: " + testerId);
//
//            // 3. Register a Game Developer
////            System.out.println("\nRegistering a Game Developer...");
////            GameDeveloper developer = new GameDeveloper("TestDev", "password123", "eyad.yasser@gamil.com");
////            developer.setAge(35);
////            developer.setCountryCode("EG");
////            developer.setTelephone("555-123-4567");
////            developer.setCompanyName("Test Studio");
////
////            int developerId = userService.registerUser(developer);
////            System.out.println("Developer registered with ID: " + developerId);
//
////            // 4. Register an Admin
////            System.out.println("\nRegistering an Admin...");
////            Admin admin = new Admin("admin@test.com", "TestAdmin", "password123", "Administrator", "Full");
////            admin.setAge(40);
////            admin.setCountryCode("AU");
////            admin.setTelephone("111-222-3333");
////
////            int adminId = userService.registerUser(admin);
////            System.out.println("Admin registered with ID: " + adminId);
//
//            // Test authentication
////            System.out.println("\n===== TESTING USER AUTHENTICATION =====");
////
////            System.out.println("\nAuthenticating Gamer...");
////            User authenticatedGamer = userService.authenticate("gamer@test.com", "password123");
////            System.out.println("Gamer authentication: " + (authenticatedGamer != null ? "Success" : "Failed"));
////            if (authenticatedGamer != null) {
////                System.out.println("Authenticated user type: " + authenticatedGamer.getUserType());
////            }
////
////            System.out.println("\nAuthenticating with wrong password...");
////            User failedAuth = userService.authenticate("gamer@test.com", "wrongpassword");
////            System.out.println("Authentication with wrong password: " + (failedAuth == null ? "failed" : " succeeded"));
//
////            // Test user retrieval
////            System.out.println("\n===== TESTING USER RETRIEVAL =====");
////             int gamerId = 1; // Replace with actual ID
////            int testerId = 2; // Replace with actual ID
////            System.out.println("\nRetrieving Gamer by ID...");
////            User retrievedGamer = userService.getUserById(gamerId, User.TYPE_GAMER);
////            System.out.println("Retrieved Gamer: " + (retrievedGamer != null ? retrievedGamer.getName() : "Not found"));
////
////            System.out.println("\nRetrieving Tester by ID...");
////            User retrievedTester = userService.getUserById(testerId, User.TYPE_TESTER);
////            System.out.println("Retrieved Tester: " + (retrievedTester != null ? retrievedTester.getName() : "Not found"));
//
////            // Test user update
////            System.out.println("\n===== TESTING USER UPDATE =====");
////
////            System.out.println("\nUpdating Gamer...");
////            if (retrievedGamer != null) {
////                Gamer gamerToUpdate = (Gamer) retrievedGamer;
////                gamerToUpdate.setLevel(2);
////                gamerToUpdate.setAge(26);
////                boolean updateSuccess = userService.updateUser(gamerToUpdate);
////                System.out.println("Gamer update: " + (updateSuccess ? "Success" : "Failed"));
////
////                // Verify update
////                User updatedGamer = userService.getUserById(gamerId, User.TYPE_GAMER);
////                System.out.println("Updated Gamer level: " + ((Gamer)updatedGamer).getLevel());
////            }
////
////            // Test last login update
////            System.out.println("\n===== TESTING LAST LOGIN UPDATE =====");
////
////            System.out.println("\nUpdating Gamer's last login...");
////            boolean loginUpdateSuccess = userService.updateLastLogin(gamerId, User.TYPE_GAMER);
////            System.out.println("Last login update: " + (loginUpdateSuccess ? "Success" : "Failed"));
////
////            // Test user deletion
////            System.out.println("\n===== TESTING USER DELETION =====");
////
//////            System.out.println("\nDeleting Gamer...");
////            boolean gamerDeleteSuccess = userService.deleteUser(gamerId, User.TYPE_GAMER);
////            System.out.println("Gamer deletion: " + (gamerDeleteSuccess ? "Success" : "Failed"));
////
////            System.out.println("\nDeleting Tester...");
////            boolean testerDeleteSuccess = userService.deleteUser(testerId, User.TYPE_TESTER);
////            System.out.println("Tester deletion: " + (testerDeleteSuccess ? "Success" : "Failed"));
////
////            System.out.println("\nDeleting Game Developer...");
////            boolean developerDeleteSuccess = userService.deleteUser(developerId, User.TYPE_DEVELOPER);
////            System.out.println("Developer deletion: " + (developerDeleteSuccess ? "Success" : "Failed"));
////
////            System.out.println("\nDeleting Admin...");
////            boolean adminDeleteSuccess = userService.deleteUser(adminId, User.TYPE_ADMIN);
////            System.out.println("Admin deletion: " + (adminDeleteSuccess ? "Success" : "Failed"));
////
////            // Verify deletion
////            System.out.println("\nVerifying Gamer deletion...");
////            User deletedGamer = userService.getUserById(gamerId, User.TYPE_GAMER);
////            System.out.println("Gamer exists after deletion: " + (deletedGamer != null));
//
//        } catch (Exception e) {
//            System.err.println("Test failed with error:");
//            e.printStackTrace();
//        }
//    }

}