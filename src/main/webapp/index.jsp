<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Game Testing Platform - Connect Developers, Testers, and Gamers</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <header>
        <div class="container">
            <div class="logo">
                <h1>Game Testing Platform</h1>
            </div>
            <nav>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/">Home</a></li>
                    <li><a href="${pageContext.request.contextPath}/auth?action=login">Login</a></li>
                    <li><a href="${pageContext.request.contextPath}/auth?action=register">Register</a></li>
                    <li><a href="#about">About</a></li>
                    <li><a href="#contact">Contact</a></li>
                </ul>
            </nav>
        </div>
    </header>

    <section class="hero">
        <div class="container">
            <div class="hero-content">
                <h2>Connect. Test. Play.</h2>
                <p>The ultimate platform where game developers, testers, and gamers come together</p>
                <div class="hero-buttons">
                    <a href="${pageContext.request.contextPath}/auth?action=register" class="btn btn-primary">Get Started</a>
                    <a href="#learn-more" class="btn btn-secondary">Learn More</a>
                </div>
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
                </div>
                <div class="feature-card">
                    <div class="icon tester-icon"></div>
                    <h3>Testers</h3>
                    <p>Discover new games, provide professional feedback, earn certificates, and build your testing portfolio.</p>
                    <a href="${pageContext.request.contextPath}/auth?action=register&type=tester" class="btn btn-outline">Join as Tester</a>
                </div>
                <div class="feature-card">
                    <div class="icon gamer-icon"></div>
                    <h3>Gamers</h3>
                    <p>Play upcoming games before they hit the market, share your opinions, and help shape the future of gaming.</p>
                    <a href="${pageContext.request.contextPath}/auth?action=register&type=gamer" class="btn btn-outline">Join as Gamer</a>
                </div>
            </div>
        </div>
    </section>

    <section id="learn-more" class="about">
        <div class="container">
            <h2 class="section-title">Why Game Testing Platform?</h2>
            <div class="about-content">
                <div class="about-text">
                    <h3>Revolutionizing Game Development</h3>
                    <p>Our platform bridges the gap between developers, testers, and gamers, creating a collaborative ecosystem that benefits everyone involved in the gaming industry.</p>
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
                    <h2>Game Testing Platform</h2>
                    <p>Connect. Test. Play.</p>
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
                <p>&copy; 2024 Game Testing Platform. All rights reserved.</p>
            </div>
        </div>
    </footer>

    <script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>