<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${param.title} | PreGame Testing Platform</title>
    
    <!-- External CSS -->
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    
    <!-- App CSS - using absolute path to ensure it works in all contexts -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/styles.css">
    
    <!-- Make sure resources are not cached during development -->
    
    <!-- Test script for debugging -->
    <script src="<%=request.getContextPath()%>/js/script.js"></script>
    
    <!-- Inline style to verify rendering -->
    <style>
        .css-test-indicator {
            position: fixed;
            bottom: 10px;
            left: 10px;
            background: blue;
            color: white;
            padding: 5px 10px;
            border-radius: 3px;
            z-index: 1000;
        }
    </style>
</head>
<body>
    <!-- CSS loading indicator -->
    <div class="css-test-indicator">CSS Loaded</div>
    
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
                    <% if (session.getAttribute("user") != null) { %>
                        <div class="user-dropdown">
                            <button class="user-dropdown-btn">
                                <i class="fas fa-user-circle"></i> <%= session.getAttribute("userName") %>
                            </button>
                            <div class="user-dropdown-content">
                                <a href="${pageContext.request.contextPath}/<%= session.getAttribute("userType") %>/dashboard">Dashboard</a>
                                <a href="${pageContext.request.contextPath}/<%= session.getAttribute("userType") %>/profile">Profile</a>
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
</body>
</html>

    