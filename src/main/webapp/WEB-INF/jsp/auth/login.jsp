<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Login" />
</jsp:include>

<section class="auth-section">
    <div class="container">
        <div class="auth-form-container" style="display: flex; align-items: center; justify-content: space-between;">
            <div style="flex: 1;">
                <h2>Login to Your Account</h2>

                <%-- Replaced c:if with JSP scriptlet for error message display --%>
                <%
                    String errorMessage = (String) request.getAttribute("errorMessage");
                    if (errorMessage != null && !errorMessage.isEmpty()) {
                %>
                <div class="error-message">
                    <p><%= errorMessage %></p>
                </div>
                <%
                    }
                %>

                <form action="<%= request.getContextPath() %>/auth" method="post" class="auth-form" data-validate="true">
                    <input type="hidden" name="action" value="login">

                    <label for="username">Username or Email</label>
                    <input type="text" id="username" name="username" required data-name="Username or Email">
                    <br>

                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" required data-name="Password">

                    <div>
                        <input type="checkbox" id="remember" name="remember">
                        <label for="remember">Remember me</label>
                    </div>

                    <button type="submit" class="btn btn-primary">Login</button>

                    <p>Don't have an account? <a href="<%= request.getContextPath() %>/auth?action=register">Register</a></p>
                    <p><a href="<%= request.getContextPath() %>/auth?action=forgot-password">Forgot your password?</a></p>
                </form>
            </div>

            <div style="flex: 1; text-align: center;">
                <img src="<%= request.getContextPath() %>/images/login-illustration.png" alt="Login Illustration" style="max-width: 100%; height: auto;">
            </div>
        </div>
    </div>
</section>

<jsp:include page="../common/footer.jsp" />

