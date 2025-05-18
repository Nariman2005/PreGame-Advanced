<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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
                    
                    <c:choose>
                        <c:when test="${empty sessionScope.user}">
                            <li><a href="${pageContext.request.contextPath}/auth?action=login">Login</a></li>
                            <li><a href="${pageContext.request.contextPath}/auth?action=register">Register</a></li>
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${sessionScope.user.userType eq 'GAMER'}">
                                    <li><a href="${pageContext.request.contextPath}/gamer/dashboard">Dashboard</a></li>
                                </c:when>
                                <c:when test="${sessionScope.user.userType eq 'DEVELOPER'}">
                                    <li><a href="${pageContext.request.contextPath}/developer/dashboard">Dashboard</a></li>
                                </c:when>
                                <c:when test="${sessionScope.user.userType eq 'TESTER'}">
                                    <li><a href="${pageContext.request.contextPath}/tester/dashboard">Dashboard</a></li>
                                </c:when>
                            </c:choose>
                            <li><a href="${pageContext.request.contextPath}/games">Games</a></li>
                            <li><a href="${pageContext.request.contextPath}/profile">Profile</a></li>
                            <li><a href="${pageContext.request.contextPath}/auth?action=logout">Logout</a></li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </nav>
        </div>
    </header>
    <main>
