<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Date, java.text.SimpleDateFormat, java.util.List, java.util.ArrayList" %>

<%!
    // Declaration tag - variables and methods available throughout the page
    private static final String TAGLINE = "Connect. Test. Play.";
    private static final int ESTABLISHED_YEAR = 2022;

    // Method to calculate years since establishment
    private int getYearsActive() {
        return java.util.Calendar.getInstance().get(java.util.Calendar.YEAR) - ESTABLISHED_YEAR;
    }

    // Method to get greeting based on time of day
    private String getDayTimeGreeting() {
        int hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY);

        if (hour < 12) {
            return "Good morning";
        } else if (hour < 18) {
            return "Good afternoon";
        } else {
            return "Good evening";
        }
    }
%>

<%
    // Scriptlet tag - code executed at request time
    String redirectParam = request.getParameter("redirect");
    if (redirectParam != null && redirectParam.equals("games")) {
%>

<%
    }

    // Calculate statistics for display
    int totalDevelopers = 120; // Simulated data
    int totalTesters = 350;    // Simulated data
    int totalGames = 275;      // Simulated data

    // Generate current date for display
    SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy");
    String currentDate = dateFormat.format(new Date());

    // Check if user is logged in (simulated)
    String username = (String) session.getAttribute("username");
    boolean isLoggedIn = username != null;
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Game Testing Platform - Connect Developers, Testers, and Gamers</title>

    <!-- Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

    <!-- Main CSS -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
<!-- Header -->
<header class="main-header">
    <div class="container">
        <div class="header-content">
            <div class="logo">
                <a href="${pageContext.request.contextPath}/">
                    <h1>Game Testing Platform</h1>
                </a>
            </div>

            <nav class="main-nav">
                <ul class="nav-links">
                    <li><a href="${pageContext.request.contextPath}/" class="nav-link">Home</a></li>
                    <li><a href="${pageContext.request.contextPath}/games" class="nav-link">Games</a></li>
                    <li><a href="${pageContext.request.contextPath}/testers" class="nav-link">Testers</a></li>
                    <li><a href="${pageContext.request.contextPath}/developers" class="nav-link">Developers</a></li>
                    <li><a href="${pageContext.request.contextPath}/about" class="nav-link">About</a></li>
                </ul>
            </nav>

            <div class="auth-actions">
                <% if (isLoggedIn) { %>
                <div class="user-dropdown">
                    <button class="user-dropdown-btn">
                        <i class="fas fa-user-circle"></i> <%= username %>
                    </button>
                    <div class="user-dropdown-content">
                        <a href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
                        <a href="${pageContext.request.contextPath}/profile">Profile</a>
                        <a href="${pageContext.request.contextPath}/auth?action=logout">Logout</a>
                    </div>
                </div>
                <% } else { %>
                <a href="${pageContext.request.contextPath}/auth?action=login" class="btn btn-secondary">Login</a>
                <a href="${pageContext.request.contextPath}/auth?action=register" class="btn btn-primary">Register</a>
                <% } %>
            </div>

            <div class="mobile-menu-toggle">
                <span></span>
                <span></span>
                <span></span>
            </div>
        </div>
    </div>
</header>

<!-- Main Content -->
<main>
    <!-- Hero Section -->
    <section class="bg-primary py-4">
        <div class="container">
            <div class="d-flex justify-content-between align-items-center gap-3">
                <div class="text-light" style="max-width: 600px;">
                    <% if (isLoggedIn) { %>
                    <h1 class="mb-2"><%= getDayTimeGreeting() %>, <%= username %>!</h1>
                    <% } else { %>
                    <h1 class="mb-2"><%=TAGLINE%></h1>
                    <% } %>
                    <p class="fs-lg mb-3">The ultimate platform where game developers, testers, and gamers come together to create exceptional gaming experiences.</p>
                    <div class="d-flex gap-2 mb-3">
                        <a href="${pageContext.request.contextPath}/auth?action=register" class="btn bg-light text-primary fw-medium">Get Started</a>
                        <a href="#learn-more" class="btn btn-secondary">Learn More</a>
                    </div>
                    <p class="fs-sm text-light">Today is <%= currentDate %></p>
                </div>
                <div class="d-none d-md-block">
                    <img src="${pageContext.request.contextPath}/images/hero-image.png" alt="Gaming illustration" style="max-height: 400px;">
                </div>
            </div>
        </div>
    </section>

    <!-- Stats Section -->
    <section class="py-3 bg-light">
        <div class="container">
            <div class="d-flex justify-content-center gap-4 py-2">
                <div class="text-center">
                    <h2 class="mb-0 fw-bold text-primary"><%= totalDevelopers %></h2>
                    <p class="fs-sm text-muted mb-0">Developers</p>
                </div>
                <div class="text-center">
                    <h2 class="mb-0 fw-bold text-primary"><%= totalTesters %></h2>
                    <p class="fs-sm text-muted mb-0">Testers</p>
                </div>
                <div class="text-center">
                    <h2 class="mb-0 fw-bold text-primary"><%= totalGames %></h2>
                    <p class="fs-sm text-muted mb-0">Games</p>
                </div>
            </div>
        </div>
    </section>

    <!-- Features Section -->
    <section id="features" class="py-4">
        <div class="container">
            <h2 class="text-center mb-4">How It Works</h2>
            <div class="d-flex flex-column flex-md-row gap-3">
                <div class="card">
                    <div class="card-body text-center">
                        <div class="mb-3">
                            <i class="fas fa-code fs-lg text-primary" style="font-size: 3rem;"></i>
                        </div>
                        <h3 class="mb-2">Game Developers</h3>
                        <p class="mb-3">Upload your games, receive valuable feedback, and connect with skilled testers to improve your creations.</p>
                        <a href="${pageContext.request.contextPath}/auth?action=register&type=developer" class="btn btn-primary">Join as Developer</a>
                    </div>
                </div>

                <div class="card">
                    <div class="card-body text-center">
                        <div class="mb-3">
                            <i class="fas fa-bug fs-lg text-primary" style="font-size: 3rem;"></i>
                        </div>
                        <h3 class="mb-2">Testers</h3>
                        <p class="mb-3">Discover new games, provide professional feedback, earn certificates, and build your testing portfolio.</p>
                        <a href="${pageContext.request.contextPath}/auth?action=register&type=tester" class="btn btn-primary">Join as Tester</a>
                    </div>
                </div>

                <div class="card">
                    <div class="card-body text-center">
                        <div class="mb-3">
                            <i class="fas fa-gamepad fs-lg text-primary" style="font-size: 3rem;"></i>
                        </div>
                        <h3 class="mb-2">Gamers</h3>
                        <p class="mb-3">Play upcoming games before they hit the market, share your opinions, and help shape the future of gaming.</p>
                        <a href="${pageContext.request.contextPath}/auth?action=register&type=gamer" class="btn btn-primary">Join as Gamer</a>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Use JavaBean for featured game -->
    <jsp:useBean id="featuredGame" class="com.pregame.gametesting.model.Game" scope="request" />
    <jsp:setProperty name="featuredGame" property="title" value="Cosmic Adventures" />
    <jsp:setProperty name="featuredGame" property="description" value="An epic space exploration game with stunning visuals and immersive gameplay." />
    <jsp:setProperty name="featuredGame" property="esrbRating" value="T" />

    <!-- Featured Game Section -->
    <section id="featured-game" class="py-4 bg-light">
        <div class="container">
            <h2 class="text-center mb-4">Featured Game</h2>
            <div class="card game-card mb-3">
                <div class="card-img">
                    <img src="${pageContext.request.contextPath}/images/featured-game.jpg" alt="Featured game" onerror="this.src='https://via.placeholder.com/800x450?text=Game+Screenshot'">
                    <span class="card-badge"><jsp:getProperty name="featuredGame" property="esrbRating" /></span>
                </div>
                <div class="card-body">
                    <h3 class="game-title"><jsp:getProperty name="featuredGame" property="title" /></h3>
                    <div class="game-developer mb-2">
                        <span>By <a href="#">Stellar Studios</a></span>
                    </div>
                    <div class="game-meta mb-2">
                        <span><i class="fas fa-calendar-alt"></i> Coming Soon</span>
                        <span><i class="fas fa-tags"></i> Space, Adventure</span>
                    </div>
                    <p class="game-description"><jsp:getProperty name="featuredGame" property="description" /></p>
                    <a href="${pageContext.request.contextPath}/games/featured" class="btn btn-primary">Learn More</a>
                </div>
            </div>
        </div>
    </section>

    <!-- About Section -->
    <section id="learn-more" class="py-4">
        <div class="container">
            <h2 class="text-center mb-4">Why Game Testing Platform?</h2>
            <div class="d-flex flex-column flex-md-row gap-3 align-items-center">
                <div>
                    <h3 class="mb-2">Revolutionizing Game Development</h3>
                    <p class="mb-2">Our platform bridges the gap between developers, testers, and gamers, creating a collaborative ecosystem that benefits everyone involved in the gaming industry.</p>
                    <p class="mb-2">Established in <%= ESTABLISHED_YEAR %> - <%= getYearsActive() %> years of connecting the gaming community!</p>
                    <ul class="mb-3">
                        <li class="mb-1"><i class="fas fa-check text-success"></i> Direct feedback from target audiences</li>
                        <li class="mb-1"><i class="fas fa-check text-success"></i> Professional testing services</li>
                        <li class="mb-1"><i class="fas fa-check text-success"></i> Early access to upcoming games</li>
                        <li class="mb-1"><i class="fas fa-check text-success"></i> Community-driven improvement process</li>
                        <li class="mb-1"><i class="fas fa-check text-success"></i> Certification for qualified testers</li>
                    </ul>
                </div>
                <div class="mt-3 mt-md-0">
                    <img src="${pageContext.request.contextPath}/images/platform-demo.jpg" alt="Platform demonstration" class="rounded shadow" onerror="this.src='https://via.placeholder.com/500x300?text=Platform+Demo'" style="max-width: 100%;">
                </div>
            </div>
        </div>
    </section>

    <!-- Contact Section -->
    <section id="contact" class="py-4 bg-light">
        <div class="container">
            <h2 class="text-center mb-2">Contact Us</h2>
            <p class="text-center mb-4">Have questions or need assistance? Our team is here to help!</p>
            <div class="d-flex flex-column flex-md-row gap-3 justify-content-center">
                <div class="card text-center">
                    <div class="card-body">
                        <div class="mb-2">
                            <i class="fas fa-envelope text-primary" style="font-size: 2rem;"></i>
                        </div>
                        <h3 class="fs-lg mb-1">Email</h3>
                        <p class="mb-0">support@gametestingplatform.com</p>
                    </div>
                </div>

                <div class="card text-center">
                    <div class="card-body">
                        <div class="mb-2">
                            <i class="fas fa-phone-alt text-primary" style="font-size: 2rem;"></i>
                        </div>
                        <h3 class="fs-lg mb-1">Phone</h3>
                        <p class="mb-0">+1 (555) 123-4567</p>
                    </div>
                </div>

                <div class="card text-center">
                    <div class="card-body">
                        <div class="mb-2">
                            <i class="fas fa-map-marker-alt text-primary" style="font-size: 2rem;"></i>
                        </div>
                        <h3 class="fs-lg mb-1">Address</h3>
                        <p class="mb-0">123 Gaming Street, Tech City, TC 98765</p>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>

<!-- Footer -->
<footer class="main-footer">
    <div class="footer-top">
        <div class="container">
            <div class="footer-grid">
                <div class="footer-brand">
                    <h2 class="footer-logo">Game Testing Platform</h2>
                    <p class="footer-tagline">Connect. Test. Play.</p>
                    <p class="footer-description">
                        The premier platform connecting game developers with testers and gamers
                        to create better gaming experiences.
                    </p>
                </div>

                <div class="footer-links">
                    <h3>Quick Links</h3>
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/">Home</a></li>
                        <li><a href="${pageContext.request.contextPath}/games">Games</a></li>
                        <li><a href="${pageContext.request.contextPath}/testers">Testers</a></li>
                        <li><a href="${pageContext.request.contextPath}/developers">Developers</a></li>
                        <li><a href="${pageContext.request.contextPath}/about">About</a></li>
                        <li><a href="${pageContext.request.contextPath}/contact">Contact</a></li>
                    </ul>
                </div>

                <div class="footer-links">
                    <h3>For Gamers</h3>
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/games/upcoming">Upcoming Games</a></li>
                        <li><a href="${pageContext.request.contextPath}/games/testing">Games in Testing</a></li>
                        <li><a href="${pageContext.request.contextPath}/gamer/dashboard">Gamer Dashboard</a></li>
                        <li><a href="${pageContext.request.contextPath}/auth?action=register&type=gamer">Join as Gamer</a></li>
                    </ul>
                </div>

                <div class="footer-links">
                    <h3>For Developers</h3>
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/developer/publishing">Publish a Game</a></li>
                        <li><a href="${pageContext.request.contextPath}/developer/analytics">Analytics</a></li>
                        <li><a href="${pageContext.request.contextPath}/developer/resources">Resources</a></li>
                        <li><a href="${pageContext.request.contextPath}/auth?action=register&type=developer">Join as Developer</a></li>
                    </ul>
                </div>

                <div class="footer-social">
                    <h3>Connect With Us</h3>
                    <div class="social-icons">
                        <a href="#" class="social-icon" aria-label="Facebook"><i class="fab fa-facebook-f"></i></a>
                        <a href="#" class="social-icon" aria-label="Twitter"><i class="fab fa-twitter"></i></a>
                        <a href="#" class="social-icon" aria-label="Instagram"><i class="fab fa-instagram"></i></a>
                        <a href="#" class="social-icon" aria-label="LinkedIn"><i class="fab fa-linkedin-in"></i></a>
                        <a href="#" class="social-icon" aria-label="Discord"><i class="fab fa-discord"></i></a>
                    </div>
                    <div class="newsletter">
                        <h4>Subscribe to our newsletter</h4>
                        <form action="${pageContext.request.contextPath}/subscribe" method="post" class="newsletter-form">
                            <input type="email" name="email" placeholder="Your email address" required>
                            <button type="submit" class="btn btn-primary">Subscribe</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="footer-bottom">
        <div class="container">
            <p>&copy; <%= java.util.Calendar.getInstance().get(java.util.Calendar.YEAR) %> Game Testing Platform. All rights reserved.</p>
            <ul class="footer-legal">
                <li><a href="${pageContext.request.contextPath}/terms">Terms of Service</a></li>
                <li><a href="${pageContext.request.contextPath}/privacy">Privacy Policy</a></li>
                <li><a href="${pageContext.request.contextPath}/cookies">Cookie Policy</a></li>
            </ul>
        </div>
    </div>
</footer>

<!-- JavaScript -->
<script src="<%= request.getContextPath() %>/js/script.js"></script>
</body>
</html>