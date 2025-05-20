<%-- ../common/header.jsp --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><c:out value="${param.title != null ? param.title : 'Game Testing Platform'}" /> - PreGame Testing</title>

    <!-- ****** MAIN CSS FILE REFERENCE - CORRECTED TO style.css ****** -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">

    <!-- Optional: Link Font Awesome (if you use its icons in the header or elsewhere) -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"
          integrity="sha512-1ycn6IcaQQ40/MKBW2W4Rhis/DbILU74C1vSrLJxCq57o941Ym01SwNsOMqvEBFlcgUa6xLiPY/NS5R+E6ztJQ=="
          crossorigin="anonymous" referrerpolicy="no-referrer" />

    <%-- Page-specific CSS if provided as parameter --%>
    <c:if test="${param.pageSpecificCss != null}">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/${param.pageSpecificCss}.css">
    </c:if>
</head>
<body> <%-- The opening body tag is here --%>

<header class="main-header">
    <div class="container">
        <div class="header-content">
            <div class="logo">
                <h1><a href="${pageContext.request.contextPath}/">PreGame Testing</a></h1>
            </div>

            <nav class="main-nav" aria-label="Main navigation">
                <ul class="nav-links">
                    <li><a class="nav-link" href="${pageContext.request.contextPath}/games">Games</a></li>
                    <li><a class="nav-link" href="${pageContext.request.contextPath}/about">About</a></li>
                    <li><a class="nav-link" href="${pageContext.request.contextPath}/contact">Contact</a></li>
                </ul>
            </nav>

            <div class="auth-actions">
                <c:choose>
                    <c:when test="${sessionScope.user != null}">
                        <div class="user-dropdown">
                            <button class="user-dropdown-btn" aria-expanded="false" aria-controls="user-dropdown-content" type="button">
                                <img src="${pageContext.request.contextPath}/images/avatars/default.png"
                                     alt="${sessionScope.userName}'s avatar"
                                     class="user-avatar">
                                <span class="user-name">${sessionScope.userName}</span>
                                <i class="fas fa-chevron-down" style="font-size: 0.7em; margin-left: 5px;"></i>
                            </button>
                            <div class="user-dropdown-content" id="user-dropdown-content">
                                <ul>
                                    <li><a href="${pageContext.request.contextPath}/user/dashboard">My Dashboard</a></li>
                                    <li><a href="${pageContext.request.contextPath}/user/settings">Settings</a></li>
                                    <li><hr style="margin: 0.25rem 0; border-color: rgba(0,0,0,0.05);"></li>
                                    <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
                                </ul>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/auth?action=login" class="btn btn-secondary">Login</a>
                        <a href="${pageContext.request.contextPath}/register-choice" class="btn btn-primary">Register</a>
                    </c:otherwise>
                </c:choose>
            </div>

            <button class="mobile-menu-toggle" aria-label="Toggle menu" aria-expanded="false">
                <span></span>
                <span></span>
                <span></span>
            </button>
        </div>
    </div>
</header>
<%-- The header.jsp ends here. The content of gamer_register.jsp will be inserted after this. --%>

