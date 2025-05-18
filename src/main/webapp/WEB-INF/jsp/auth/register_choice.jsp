<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Register" />
</jsp:include>

<section class="register-choice-section">
    <div class="container">
        <h2 class="section-title">Join as a...</h2>
        
        <div class="register-options">
            <div class="register-option">
                <div class="icon gamer-icon"></div>
                <h3>Gamer</h3>
                <p>Play upcoming games, provide valuable feedback, and help shape the future of gaming.</p>
                <a href="${pageContext.request.contextPath}/auth?action=register&type=gamer" class="btn btn-primary">Register as Gamer</a>
            </div>
            
            <div class="register-option">
                <div class="icon developer-icon"></div>
                <h3>Game Developer</h3>
                <p>Upload your games, receive feedback, and connect with skilled testers to improve your creations.</p>
                <a href="${pageContext.request.contextPath}/auth?action=register&type=developer" class="btn btn-primary">Register as Developer</a>
            </div>
            
            <div class="register-option">
                <div class="icon tester-icon"></div>
                <h3>Tester</h3>
                <p>Discover new games, provide professional reviews, earn certificates, and build your testing portfolio.</p>
                <a href="${pageContext.request.contextPath}/auth?action=register&type=tester" class="btn btn-primary">Register as Tester</a>
            </div>
        </div>
        
        <div class="auth-links">
            <p>Already have an account? <a href="${pageContext.request.contextPath}/auth?action=login">Login</a></p>
        </div>
    </div>
</section>

<jsp:include page="../common/footer.jsp" />
