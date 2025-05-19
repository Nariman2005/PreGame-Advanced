<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Date, java.text.SimpleDateFormat, java.util.List, java.util.ArrayList" %>



<%!
    // Declaration tag - variables and methods available throughout the page

    private static final String TAGLINE = "Connect. Test. Play.";
    private static final int ESTABLISHED_YEAR = 2022;
    
    // Method to calculate years since establishment
    private int getYearsActive() {
        return java.util.Calendar.getInstance().get(java.util.Calendar.YEAR) - 2024;
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
    <jsp:forward page="/games">
        <jsp:param name="source" value="homepage" />
    </jsp:forward>
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
    <title><%="Game Testing Platform"  %> - Connect Developers, Testers, and Gamers</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <header>
        <div class="container">
            <div class="logo">
                <h1><%= "Game Testing Platform" %></h1>
            </div>
            <nav>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/">Home</a></li>
                    <% if (isLoggedIn) { %>
                        <li><a href="${pageContext.request.contextPath}/dashboard">Dashboard</a></li>
                        <li><a href="${pageContext.request.contextPath}/auth?action=logout">Logout</a></li>
                    <% } else { %>
                        <li><a href="${pageContext.request.contextPath}/auth?action=login">Login</a></li>
                        <li><a href="${pageContext.request.contextPath}/auth?action=register">Register</a></li>
                    <% } %>
                    <li><a href="#about">About</a></li>
                    <li><a href="#contact">Contact</a></li>
                </ul>
            </nav>
        </div>
    </header>

    <section class="hero">
        <div class="container">
            <div class="hero-content">
                <% if (isLoggedIn) { %>
                    <h2><%= getDayTimeGreeting() %>, <%= username %>!</h2>
                <% } else { %>
                    <h2><%=  "Connect. Test. Play."%></h2>
                <% } %>
                <p>The ultimate platform where game developers, testers, and gamers come together</p>
                <div class="hero-buttons">
                    <a href="${pageContext.request.contextPath}/auth?action=register" class="btn btn-primary">Get Started</a>
                    <a href="#learn-more" class="btn btn-secondary">Learn More</a>
                </div>
                <p class="date-display">Today is <%= currentDate %></p>
            </div>
        </div>
    </section>

    <section id="features" class="features">
        <div class="container">
            <h2 class="section-title">How It Works</h2>
            <div class="feature-cards">
                <div class="feature-card">
                    <div class="icon developer-icon"></div>
                    <h3>Game Developers</h3>
                    <p>Upload your games, receive valuable feedback, and connect with skilled testers to improve your creations.</p>
                    <a href="${pageContext.request.contextPath}/auth?action=register&type=developer" class="btn btn-outline">Join as Developer</a>
                    <p class="stat"><strong><%= totalDevelopers %></strong> active developers</p>
                </div>
                <div class="feature-card">
                    <div class="icon tester-icon"></div>
                    <h3>Testers</h3>
                    <p>Discover new games, provide professional feedback, earn certificates, and build your testing portfolio.</p>
                    <a href="${pageContext.request.contextPath}/auth?action=register&type=tester" class="btn btn-outline">Join as Tester</a>
                    <p class="stat"><strong><%= totalTesters %></strong> professional testers</p>
                </div>
                <div class="feature-card">
                    <div class="icon gamer-icon"></div>
                    <h3>Gamers</h3>
                    <p>Play upcoming games before they hit the market, share your opinions, and help shape the future of gaming.</p>
                    <a href="${pageContext.request.contextPath}/auth?action=register&type=gamer" class="btn btn-outline">Join as Gamer</a>
                    <p class="stat"><strong><%= totalGames %></strong> games available</p>
                </div>
            </div>
        </div>
    </section>

    <!-- Use JavaBean for featured game -->
    <jsp:useBean id="featuredGame" class="com.pregame.gametesting.model.Game" scope="request" />
    <jsp:setProperty name="featuredGame" property="title" value="Cosmic Adventures" />
    <jsp:setProperty name="featuredGame" property="description" value="An epic space exploration game with stunning visuals and immersive gameplay." />
    <jsp:setProperty name="featuredGame" property="esrbRating" value="T" />
    
    <section id="featured-game" class="featured-game">
        <div class="container">
            <h2 class="section-title">Featured Game</h2>
            <div class="featured-game-content">
                <div class="game-preview">
                    <img src="${pageContext.request.contextPath}/images/featured-game.jpg" alt="Featured Game">
                </div>
                <div class="game-details">
                    <h3><jsp:getProperty name="featuredGame" property="title" /></h3>
                    <p class="rating">ESRB: <jsp:getProperty name="featuredGame" property="esrbRating" /></p>
                    <p class="description"><jsp:getProperty name="featuredGame" property="description" /></p>
                    <a href="${pageContext.request.contextPath}/games/featured" class="btn btn-primary">Learn More</a>
                </div>
            </div>
        </div>
    </section>

    <section id="learn-more" class="about">
        <div class="container">
            <h2 class="section-title">Why <%= "Game Testing Platform"  %>?</h2>
            <div class="about-content">
                <div class="about-text">
                    <h3>Revolutionizing Game Development</h3>
                    <p>Our platform bridges the gap between developers, testers, and gamers, creating a collaborative ecosystem that benefits everyone involved in the gaming industry.</p>
                    <p>Established in <%= 2025 %> - <%= getYearsActive() %> years of connecting the gaming community!</p>
                    <ul class="benefits-list">
                        <li>Direct feedback from target audiences</li>
                        <li>Professional testing services</li>
                        <li>Early access to upcoming games</li>
                        <li>Community-driven improvement process</li>
                        <li>Certification for qualified testers</li>
                    </ul>
                </div>
                <div class="about-image">
                    <img src="${pageContext.request.contextPath}/images/platform-demo.jpg" alt="Platform demonstration">
                </div>
            </div>
        </div>
    </section>

    <section id="contact" class="contact">
        <div class="container">
            <h2 class="section-title">Contact Us</h2>
            <p class="contact-intro">Have questions or need assistance? Our team is here to help!</p>
            <div class="contact-info">
                <div class="contact-item">
                    <div class="icon email-icon"></div>
                    <h3>Email</h3>
                    <p>support@gametestingplatform.com</p>
                </div>
                <div class="contact-item">
                    <div class="icon phone-icon"></div>
                    <h3>Phone</h3>
                    <p>+1 (555) 123-4567</p>
                </div>
                <div class="contact-item">
                    <div class="icon address-icon"></div>
                    <h3>Address</h3>
                    <p>123 Gaming Street, Tech City, TC 98765</p>
                </div>
            </div>
        </div>
    </section>

    <footer>
        <div class="container">
            <div class="footer-content">
                <div class="footer-logo">
                    <h2><%= "PreGame Testing Platform"  %></h2>
                    <p><%= "Connect. Test. Play."%></p>
                </div>
                <div class="footer-links">
                    <h3>Quick Links</h3>
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/">Home</a></li>
                        <li><a href="${pageContext.request.contextPath}/auth?action=login">Login</a></li>
                        <li><a href="${pageContext.request.contextPath}/auth?action=register">Register</a></li>
                        <li><a href="#about">About</a></li>
                        <li><a href="#contact">Contact</a></li>
                    </ul>
                </div>
                <div class="footer-social">
                    <h3>Follow Us</h3>
                    <div class="social-icons">
                        <a href="#" class="social-icon facebook"></a>
                        <a href="#" class="social-icon twitter"></a>
                        <a href="#" class="social-icon instagram"></a>
                        <a href="#" class="social-icon linkedin"></a>
                    </div>
                </div>
            </div>
            <div class="footer-bottom">
                <p>&copy; <%= java.util.Calendar.getInstance().get(java.util.Calendar.YEAR) %> <%= "PreGame Testing Platform" %>. All rights reserved.</p>
            </div>
        </div>
    </footer>

    <script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>