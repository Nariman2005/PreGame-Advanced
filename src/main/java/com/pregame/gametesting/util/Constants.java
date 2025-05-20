package com.pregame.gametesting.util;

/**
 * Application-wide constants
 */
public class Constants {

    // Session attributes
    public static final String SESSION_USER = "currentUser";
    public static final String SESSION_USER_ID = "userId";
    public static final String SESSION_USER_TYPE = "userType";

    // User types
    public static final String USER_TYPE_GAMER = "GAMER";
    public static final String USER_TYPE_DEVELOPER = "DEVELOPER";
    public static final String USER_TYPE_TESTER = "TESTER";
    public static final String USER_TYPE_ADMIN = "ADMIN";

    // Request parameters
    public static final String PARAM_ACTION = "action";
    public static final String PARAM_ID = "id";
    public static final String PARAM_TYPE = "type";
    public static final String PARAM_EMAIL = "email";
    public static final String PARAM_USERNAME = "username";
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_CONFIRM_PASSWORD = "confirmPassword";
    public static final String PARAM_FIRST_NAME = "firstName";
    public static final String PARAM_LAST_NAME = "lastName";

    // Action values
    public static final String ACTION_LOGIN = "login";
    public static final String ACTION_LOGOUT = "logout";
    public static final String ACTION_REGISTER = "register";
    public static final String ACTION_LIST = "list";
    public static final String ACTION_VIEW = "view";
    public static final String ACTION_EDIT = "edit";
    public static final String ACTION_DELETE = "delete";
    public static final String ACTION_SUBMIT = "submit";

    // Upload directories
    public static final String UPLOAD_DIR_GAMES = "games";
    public static final String UPLOAD_DIR_PROFILES = "profiles";

    // Messages for request attributes
    public static final String ATTR_ERROR_MESSAGE = "errorMessage";
    public static final String ATTR_SUCCESS_MESSAGE = "successMessage";
    public static final String ATTR_INFO_MESSAGE = "infoMessage";

    // Pagination
    public static final int DEFAULT_PAGE_SIZE = 10;

    // Game statuses
    public static final String GAME_STATUS_DRAFT = "DRAFT";
    public static final String GAME_STATUS_PENDING = "PENDING";
    public static final String GAME_STATUS_PUBLISHED = "PUBLISHED";
    public static final String GAME_STATUS_ARCHIVED = "ARCHIVED";

    // Review statuses
    public static final String REVIEW_STATUS_PENDING = "PENDING";
    public static final String REVIEW_STATUS_APPROVED = "APPROVED";
    public static final String REVIEW_STATUS_REJECTED = "REJECTED";

    // Paths
    public static final String PATH_HOME = "/index.jsp";
    public static final String PATH_LOGIN = "/WEB-INF/jsp/auth/login.jsp";
    public static final String PATH_REGISTER_CHOICE = "/WEB-INF/jsp/auth/register_choice.jsp";
    public static final String PATH_REGISTER_GAMER = "/WEB-INF/jsp/auth/register_gamer.jsp";
    public static final String PATH_REGISTER_DEVELOPER = "/WEB-INF/jsp/auth/register_developer.jsp";
    public static final String PATH_REGISTER_TESTER = "/WEB-INF/jsp/auth/register_tester.jsp";
    public static final String PATH_DASHBOARD_GAMER = "/WEB-INF/jsp/gamer/dashboard.jsp";
    public static final String PATH_DASHBOARD_DEVELOPER = "/WEB-INF/jsp/developer/dashboard.jsp";
    public static final String PATH_DASHBOARD_TESTER = "/WEB-INF/jsp/tester/dashboard.jsp";
}
