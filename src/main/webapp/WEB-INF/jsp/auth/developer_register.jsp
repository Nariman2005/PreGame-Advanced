<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Developer Registration | Game Testing Platform</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <!-- Font Awesome for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Developer Registration" />
</jsp:include>

<section class="register-section">
    <div class="container">
        <h2 class="section-title">Register as a Game Developer</h2>
        
        <div class="register-form-container">
            <% if (request.getAttribute("error") != null) { %>
                <div class="alert alert-danger">
                    <%= request.getAttribute("error") %>
                </div>
            <% } %>
            
            <form action="${pageContext.request.contextPath}/auth" method="post" class="register-form">
                <input type="hidden" name="action" value="register">
                <input type="hidden" name="userType" value="developer">
                
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="name">Full Name</label>
                        <input type="text" id="name" name="name" class="form-control" required>
                    </div>
                    
                    <div class="form-group col-md-6">
                        <label for="email">Email</label>
                        <input type="email" id="email" name="email" class="form-control" required>
                    </div>
                </div>
                
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="password">Password</label>
                        <input type="password" id="password" name="password" class="form-control" required 
                               minlength="8" pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$" 
                               title="Password must be at least 8 characters and include uppercase, lowercase, and numbers">
                    </div>
                    
                    <div class="form-group col-md-6">
                        <label for="confirmPassword">Confirm Password</label>
                        <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" required>
                    </div>
                </div>
                
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="companyName">Company/Studio Name</label>
                        <input type="text" id="companyName" name="companyName" class="form-control" required>
                    </div>
                    
                    <div class="form-group col-md-6">
                        <label for="age">Age</label>
                        <input type="number" id="age" name="age" class="form-control" min="18" required>
                    </div>
                </div>
                
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="countryCode">Country Code</label>
                        <select id="countryCode" name="countryCode" class="form-control" required>
                            <option value="">Select Country</option>
                            <option value="US">United States</option>
                            <option value="UK">United Kingdom</option>
                            <option value="CA">Canada</option>
                            <option value="AU">Australia</option>
                            <option value="EG">Egypt</option>
                            <option value="FR">France</option>
                            <option value="DE">Germany</option>
                            <option value="IN">India</option>
                            <option value="JP">Japan</option>
                            <!-- Add more countries as needed -->
                        </select>
                    </div>
                    
                    <div class="form-group col-md-6">
                        <label for="telephone">Phone Number</label>
                        <input type="tel" id="telephone" name="telephone" class="form-control" required>
                    </div>
                </div>
                
                <div class="form-group">
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" id="agreeTerms" required>
                        <label class="form-check-label" for="agreeTerms">
                            I agree to the <a href="#" target="_blank">Terms of Service</a> and <a href="#" target="_blank">Privacy Policy</a>
                        </label>
                    </div>
                </div>
                
                <div class="form-group">
                    <button type="submit" class="btn btn-primary btn-block">Register</button>
                </div>
            </form>
            
            <div class="auth-links">
                <p>Already have an account? <a href="${pageContext.request.contextPath}/auth?action=login">Login</a></p>
                <p>Want to register as something else? <a href="${pageContext.request.contextPath}/auth?action=register">Choose another option</a></p>
            </div>
        </div>
    </div>
</section>

<script>
    // Form validation
    document.querySelector('.register-form').addEventListener('submit', function(event) {
        var password = document.getElementById('password').value;
        var confirmPassword = document.getElementById('confirmPassword').value;
        
        if (password !== confirmPassword) {
            alert('Passwords do not match!');
            event.preventDefault();
        }
    });
</script>

<jsp:include page="../common/footer.jsp" />

</body>
</html>

