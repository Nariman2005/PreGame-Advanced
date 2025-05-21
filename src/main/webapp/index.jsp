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

    // Set the page title for the header
    request.setAttribute("pageTitle", "Game Testing Platform - Connect Developers, Testers, and Gamers");
%>

<!-- Include Header -->
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />

<!-- Main Content -->
<main>
    <!-- Hero Section -->
    <section style="background: linear-gradient(135deg, #3a6df0, #5c86f6); padding: 4rem 0; position: relative; overflow: hidden;">
        <!-- Background decorative elements -->
        <div style="position: absolute; width: 300px; height: 300px; background: rgba(255,255,255,0.05); border-radius: 50%; top: -100px; right: -100px;"></div>
        <div style="position: absolute; width: 200px; height: 200px; background: rgba(255,255,255,0.05); border-radius: 50%; bottom: -70px; left: 10%;"></div>

        <div class="container">
            <div style="display: flex; justify-content: space-between; align-items: center; gap: 2rem; flex-wrap: wrap;">
                <div style="max-width: 600px; color: white; position: relative; z-index: 2;">
                    <div style="display: inline-block; background: rgba(255,255,255,0.15); padding: 0.5rem 1rem; border-radius: 50px; margin-bottom: 1rem;">
                        <span style="font-weight: 500; font-size: 0.9rem;">🚀 #1 Game Testing Platform</span>
                    </div>

                    <% if (isLoggedIn) { %>
                    <h1 style="font-size: 2.8rem; font-weight: 800; margin-bottom: 1.5rem; line-height: 1.2;"><%= getDayTimeGreeting() %>, <span style="color: #ffffff; text-decoration: underline; text-decoration-color: rgba(255,255,255,0.3); text-decoration-thickness: 4px;"><%= username %></span>!</h1>
                    <% } else { %>
                    <h1 style="font-size: 2.8rem; font-weight: 800; margin-bottom: 1.5rem; line-height: 1.2;"><%=TAGLINE%></h1>
                    <% } %>

                    <p style="font-size: 1.2rem; margin-bottom: 2rem; line-height: 1.6; opacity: 0.9;">The ultimate platform where game developers, testers, and gamers come together to create exceptional gaming experiences.</p>

                    <div style="display: flex; gap: 1rem; margin-bottom: 2rem;">
                        <a href="${pageContext.request.contextPath}/auth?action=register" style="background: white; color: #3a6df0; padding: 0.8rem 1.5rem; border-radius: 8px; font-weight: 600; text-decoration: none; box-shadow: 0 5px 15px rgba(0,0,0,0.1); display: inline-flex; align-items: center; transition: transform 0.2s;">
                            <i class="fas fa-rocket" style="margin-right: 8px;"></i> Get Started
                        </a>
                        <a href="#learn-more" style="background: rgba(255,255,255,0.15); color: white; padding: 0.8rem 1.5rem; border-radius: 8px; font-weight: 600; text-decoration: none; display: inline-flex; align-items: center; transition: transform 0.2s;">
                            <i class="fas fa-info-circle" style="margin-right: 8px;"></i> Learn More
                        </a>
                    </div>

                    <div style="display: flex; align-items: center; font-size: 0.9rem; opacity: 0.8;">
                        <i class="fas fa-calendar-alt" style="margin-right: 6px;"></i>
                        <span>Today is <%= currentDate %></span>
                    </div>
                </div>

                <div style="position: relative; flex: 1; min-width: 300px; display: flex; justify-content: center;">
                    <div style="position: absolute; background: rgba(255,255,255,0.1); width: 300px; height: 300px; border-radius: 50%; top: 50%; left: 50%; transform: translate(-50%, -50%);"></div>
                    <img src="${pageContext.request.contextPath}/images/hero-image.png" alt="Gaming illustration" style="max-height: 400px; position: relative; z-index: 2; filter: drop-shadow(0 10px 15px rgba(0,0,0,0.2));">
                </div>
            </div>

            <!-- Trusted by companies section -->
            <div style="margin-top: 3rem; border-top: 1px solid rgba(255,255,255,0.1); padding-top: 1.5rem; text-align: center; color: white;">
                <p style="font-size: 0.9rem; margin-bottom: 1rem; opacity: 0.7;">TRUSTED BY LEADING GAME COMPANIES</p>
                <div style="display: flex; justify-content: center; gap: 2rem; align-items: center; flex-wrap: wrap;">
                    <div style="opacity: 0.7;">Unity</div>
                    <div style="opacity: 0.7;">Epic Games</div>
                    <div style="opacity: 0.7;">EA Sports</div>
                    <div style="opacity: 0.7;">Ubisoft</div>
                    <div style="opacity: 0.7;">Activision</div>
                </div>
            </div>
        </div>
    </section>

    <!-- Stats Section -->
    <section class="py-5 bg-light">
        <div class="container">
            <h2 class="text-center mb-4">Platform Statistics</h2>
            <div class="text-center mb-2">
                <div style="height: 3px; width: 80px; background-color: #3a6df0; margin: 0 auto; border-radius: 3px;"></div>
            </div>

            <div style="display: flex; flex-wrap: wrap; justify-content: center; gap: 30px; margin-top: 30px;">
                <!-- Developers Card -->
                <div style="background-color: white; border-radius: 10px; padding: 25px; text-align: center; box-shadow: 0 5px 15px rgba(0,0,0,0.08); flex: 1; min-width: 200px; max-width: 300px;">
                    <i class="fas fa-laptop-code" style="font-size: 40px; color: #3a6df0; margin-bottom: 15px;"></i>
                    <h3 style="font-size: 42px; font-weight: 700; color: #3a6df0; margin: 0;"><%= totalDevelopers %></h3>
                    <p style="text-transform: uppercase; font-weight: 600; color: #6e7891; letter-spacing: 1px; margin-top: 5px;">Game Developers</p>
                    <div style="margin-top: 15px;">
                        <span style="background-color: rgba(0,200,150,0.1); color: #00c896; padding: 5px 10px; border-radius: 4px; font-size: 12px;">
                            <i class="fas fa-arrow-up" style="margin-right: 5px;"></i>12% Growth
                        </span>
                    </div>
                </div>

                <!-- Testers Card -->
                <div style="background-color: white; border-radius: 10px; padding: 25px; text-align: center; box-shadow: 0 5px 15px rgba(0,0,0,0.08); flex: 1; min-width: 200px; max-width: 300px;">
                    <i class="fas fa-vial" style="font-size: 40px; color: #3a6df0; margin-bottom: 15px;"></i>
                    <h3 style="font-size: 42px; font-weight: 700; color: #3a6df0; margin: 0;"><%= totalTesters %></h3>
                    <p style="text-transform: uppercase; font-weight: 600; color: #6e7891; letter-spacing: 1px; margin-top: 5px;">Professional Testers</p>
                    <div style="margin-top: 15px;">
                        <span style="background-color: rgba(0,200,150,0.1); color: #00c896; padding: 5px 10px; border-radius: 4px; font-size: 12px;">
                            <i class="fas fa-arrow-up" style="margin-right: 5px;"></i>20% Growth
                        </span>
                    </div>
                </div>

                <!-- Games Card -->
                <div style="background-color: white; border-radius: 10px; padding: 25px; text-align: center; box-shadow: 0 5px 15px rgba(0,0,0,0.08); flex: 1; min-width: 200px; max-width: 300px;">
                    <i class="fas fa-gamepad" style="font-size: 40px; color: #3a6df0; margin-bottom: 15px;"></i>
                    <h3 style="font-size: 42px; font-weight: 700; color: #3a6df0; margin: 0;"><%= totalGames %></h3>
                    <p style="text-transform: uppercase; font-weight: 600; color: #6e7891; letter-spacing: 1px; margin-top: 5px;">Games Published</p>
                    <div style="margin-top: 15px;">
                        <span style="background-color: rgba(0,200,150,0.1); color: #00c896; padding: 5px 10px; border-radius: 4px; font-size: 12px;">
                            <i class="fas fa-arrow-up" style="margin-right: 5px;"></i>15% Growth
                        </span>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>

<!-- Include Footer -->
<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
</body>
</html>
