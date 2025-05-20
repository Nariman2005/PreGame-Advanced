<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gamer Registration | Game Testing Platform</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <!-- Font Awesome for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Gamer Registration" />
</jsp:include>

<section class="register-section">
    <div class="container">
        <h2 class="section-title form-title">Register as a Gamer</h2>

        <div class="register-form-container">
            <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger" role="alert">
                <%= request.getAttribute("error") %>
            </div>
            <% } %>

            <form action="${pageContext.request.contextPath}/auth" method="post" class="register-form needs-validation" novalidate>
                <input type="hidden" name="action" value="register">
                <input type="hidden" name="userType" value="gamer">

                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="name" class="form-label">Full Name</label>
                        <input type="text" id="name" name="name" class="form-control" required>
                        <div class="invalid-feedback">Please enter your full name.</div>
                    </div>

                    <div class="form-group col-md-6">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" id="email" name="email" class="form-control" required>
                        <div class="invalid-feedback">Please enter a valid email address.</div>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="password" class="form-label">Password</label>
                        <input type="password" id="password" name="password" class="form-control" required
                               minlength="8" pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$"
                               title="Password must be at least 8 characters and include uppercase, lowercase, and numbers.">
                        <div class="form-text text-muted">
                            At least 8 characters, 1 uppercase, 1 lowercase, 1 number.
                        </div>
                        <div class="invalid-feedback">Please provide a valid password meeting the criteria.</div>
                    </div>

                    <div class="form-group col-md-6">
                        <label for="confirmPassword" class="form-label">Confirm Password</label>
                        <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" required>
                        <div class="invalid-feedback">Please confirm your password.</div>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group col-md-4">
                        <label for="age" class="form-label">Age</label>
                        <input type="number" id="age" name="age" class="form-control" min="13" required>
                        <div class="invalid-feedback">You must be at least 13 years old.</div>
                    </div>

                    <div class="form-group col-md-4">
                        <label for="countryCode" class="form-label">Country</label>
                        <select id="countryCode" name="countryCode" class="form-control" required>
                            <option value="" disabled selected>Select Country</option>
                            <option value="US">United States</option>
                            <option value="UK">United Kingdom</option>
                            <option value="CA">Canada</option>
                            <option value="AU">Australia</option>
                            <option value="EG">Egypt</option>
                            <option value="FR">France</option>
                            <option value="DE">Germany</option>
                            <option value="IN">India</option>
                            <option value="JP">Japan</option>

                        </select>
                        <div class="invalid-feedback">Please select your country.</div>
                    </div>

                    <div class="form-group col-md-4">
                        <label for="telephone" class="form-label">Phone Number (Optional)</label>
                        <input type="tel" id="telephone" name="telephone" class="form-control" placeholder="e.g., +1 555 123456">
                        <div class="invalid-feedback">Please enter a valid phone number.</div>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="level" class="form-label">Gaming Experience Level</label>
                        <select id="level" name="level" class="form-control">
                            <option value="1">Beginner</option>
                            <option value="2">Intermediate</option>
                            <option value="3">Advanced</option>
                            <option value="4">Expert</option>
                        </select>
                    </div>
                </div>

                <div class="form-group">
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" id="agreeTerms" name="agreeTerms" required>
                        <label class="form-check-label" for="agreeTerms">
                            I agree to the <a href="${pageContext.request.contextPath}/terms" target="_blank">Terms of Service</a> and <a href="${pageContext.request.contextPath}/privacy" target="_blank">Privacy Policy</a>
                        </label>
                        <div class="invalid-feedback">You must agree to the terms and conditions.</div>
                    </div>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary btn-lg">Register</button>
                </div>
            </form>

            <div class="auth-links">
                <p>Already have an account? <a href="${pageContext.request.contextPath}/auth?action=login">Login</a></p>
                <p>Want to register as something else? <a href="${pageContext.request.contextPath}/register-choice">Choose another option</a></p>
            </div>
        </div>
    </div>
</section>

<script>
    // Basic client-side password confirmation
    document.querySelector('.register-form').addEventListener('submit', function(event) {
        var passwordInput = document.getElementById('password');
        var confirmPasswordInput = document.getElementById('confirmPassword');

        if (passwordInput.value !== confirmPasswordInput.value) {
            // Instead of alert, you can use a more integrated error message display
            confirmPasswordInput.setCustomValidity('Passwords do not match.'); // For HTML5 validation API
            confirmPasswordInput.reportValidity(); // Show the validation message
            // Or display a custom error message near the confirmPassword field
            // console.error('Passwords do not match!');
            event.preventDefault(); // Stop form submission
        } else {
            confirmPasswordInput.setCustomValidity(''); // Clear any previous custom error
        }

        // Example for Bootstrap 5 validation (if you add 'needs-validation' to form and 'novalidate')
        // if (!this.checkValidity()) {
        //   event.preventDefault();
        //   event.stopPropagation();
        // }
        // this.classList.add('was-validated');

    });

    // Optional: Add event listener to password fields to clear confirm password error on change
    document.getElementById('password').addEventListener('input', clearConfirmPasswordError);
    document.getElementById('confirmPassword').addEventListener('input', clearConfirmPasswordError);

    function clearConfirmPasswordError() {
        document.getElementById('confirmPassword').setCustomValidity('');
    }

</script>

<jsp:include page="../common/footer.jsp" />

</body>
</html>

