<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${param.title} - Game Testing Platform</title>
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
                    
                    <% if (session.getAttribute("user") == null) { %>
                        <li><a href="${pageContext.request.contextPath}/auth?action=login">Login</a></li>
                        <li><a href="${pageContext.request.contextPath}/auth?action=register">Register</a></li>
                    <% } else { %>
                        <% String userType = (String) session.getAttribute("userType"); %>
                        <% if ("GAMER".equals(userType)) { %>
                            <li><a href="${pageContext.request.contextPath}/gamer/dashboard">Dashboard</a></li>
                        <% } else if ("DEVELOPER".equals(userType)) { %>
                            <li><a href="${pageContext.request.contextPath}/developer/dashboard">Dashboard</a></li>
                        <% } %>
                        <li><a href="${pageContext.request.contextPath}/games">Games</a></li>
                        <li><a href="${pageContext.request.contextPath}/profile">Profile</a></li>
                        <li><a href="${pageContext.request.contextPath}/auth?action=logout">Logout</a></li>
                    <% } %>
                </ul>
            </nav>
        </div>
    </header>

</body>
</html>
