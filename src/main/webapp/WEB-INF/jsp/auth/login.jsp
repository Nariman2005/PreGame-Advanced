<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Login" />
</jsp:include>

<section class="auth-section">
    <div class="container">
        <div class="auth-form-container">
            <h2>Login to Your Account</h2>
            
            <c:if test="${not empty errorMessage}">
                <div class="error-message">
                    <p>${errorMessage}</p>
                </div>
            </c:if>
            
            <form action="${pageContext.request.contextPath}/auth" method="post" class="auth-form" data-validate="true">
                <input type="hidden" name="action" value="login">
                
                <div class="form-group">
                    <label for="username">Username or Email</label>
                    <input type="text" id="username" name="username" required data-name="Username or Email">
                </div>
                
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" required data-name="Password">
                </div>
                
                <div class="form-group remember-me">
                    <input type="checkbox" id="remember" name="remember">
                    <label for="remember">Remember me</label>
                </div>
                
                <div class="form-group">
                    <button type="submit" class="btn btn-primary">Login</button>
                </div>
                
                <div class="auth-links">
                    <p>Don't have an account? <a href="${pageContext.request.contextPath}/auth?action=register">Register</a></p>
                    <p><a href="${pageContext.request.contextPath}/auth?action=forgot-password">Forgot your password?</a></p>
                </div>
            </form>
        </div>
    </div>
</section>

<jsp:include page="../common/footer.jsp" />
