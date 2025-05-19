<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    </main>
    
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
                <p>&copy; 2024 Game Testing Platform. All rights reserved.</p>
                <ul class="footer-legal">
                    <li><a href="${pageContext.request.contextPath}/terms">Terms of Service</a></li>
                    <li><a href="${pageContext.request.contextPath}/privacy">Privacy Policy</a></li>
                    <li><a href="${pageContext.request.contextPath}/cookies">Cookie Policy</a></li>
                </ul>
            </div>
        </div>
    </footer>
</body>
    <!-- App JavaScript - using absolute path with scriptlet to ensure it works in all contexts -->
    <script src="<%=request.getContextPath()%>/js/script.js"></script>
    
    <!-- Inline JavaScript backup in case external file fails to load -->
    <script>
        // Check if our main script loaded successfully
        if (typeof window.scriptLoaded === 'undefined') {
            console.log('External script did not load correctly, using backup code');
            
            // Mobile menu toggle
            const mobileMenuToggle = document.querySelector('.mobile-menu-toggle');
            const mainNav = document.querySelector('.main-nav');
            
            if (mobileMenuToggle && mainNav) {
                mobileMenuToggle.addEventListener('click', function() {
                    mainNav.classList.toggle('active');
                    this.classList.toggle('active');
                });
            }
            
            // User dropdown toggle
            const userDropdownBtn = document.querySelector('.user-dropdown-btn');
            if (userDropdownBtn) {
                userDropdownBtn.addEventListener('click', function() {
                    document.querySelector('.user-dropdown-content').classList.toggle('show');
                });
                
                // Close dropdown when clicking outside
                window.addEventListener('click', function(event) {
                    if (!event.target.matches('.user-dropdown-btn')) {
                        const dropdowns = document.getElementsByClassName('user-dropdown-content');
                        for (let i = 0; i < dropdowns.length; i++) {
                            const openDropdown = dropdowns[i];
                            if (openDropdown.classList.contains('show')) {
                                openDropdown.classList.remove('show');
                            }
                        }
                    }
                });
            }
        }
    </script>

</html>
