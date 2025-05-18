// Main JavaScript file for Game Testing Platform

document.addEventListener('DOMContentLoaded', function() {
    // Smooth scrolling for anchor links
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function(e) {
            e.preventDefault();
            const targetId = this.getAttribute('href');
            
            if (targetId === '#') return;
            
            const targetElement = document.querySelector(targetId);
            if (targetElement) {
                window.scrollTo({
                    top: targetElement.offsetTop - 80,
                    behavior: 'smooth'
                });
            }
        });
    });

    // Mobile menu toggle functionality
    const setupMobileMenu = () => {
        const mobileMenuButton = document.querySelector('.mobile-menu-toggle');
        const navMenu = document.querySelector('nav ul');
        
        if (mobileMenuButton && navMenu) {
            mobileMenuButton.addEventListener('click', () => {
                navMenu.classList.toggle('active');
                mobileMenuButton.classList.toggle('active');
            });
        }
    };
    
    // Add active class to navigation items based on current page
    const setActiveNavItem = () => {
        const currentPath = window.location.pathname;
        const navLinks = document.querySelectorAll('nav ul li a');
        
        navLinks.forEach(link => {
            const linkPath = link.getAttribute('href');
            if (linkPath === currentPath || 
                (currentPath.includes(linkPath) && linkPath !== '/')) {
                link.classList.add('active');
            }
        });
    };
    
    // Form validation
    const setupFormValidation = () => {
        const forms = document.querySelectorAll('form[data-validate="true"]');
        
        forms.forEach(form => {
            form.addEventListener('submit', function(e) {
                let isValid = true;
                const requiredFields = form.querySelectorAll('[required]');
                
                requiredFields.forEach(field => {
                    if (!field.value.trim()) {
                        isValid = false;
                        
                        // Create or update error message
                        let errorMsg = field.nextElementSibling;
                        if (!errorMsg || !errorMsg.classList.contains('error-message')) {
                            errorMsg = document.createElement('div');
                            errorMsg.classList.add('error-message');
                            field.parentNode.insertBefore(errorMsg, field.nextSibling);
                        }
                        
                        errorMsg.textContent = `${field.getAttribute('data-name') || 'Field'} is required`;
                        field.classList.add('input-error');
                    }
                });
                
                // Email validation
                const emailFields = form.querySelectorAll('input[type="email"]');
                const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                
                emailFields.forEach(field => {
                    if (field.value.trim() && !emailRegex.test(field.value.trim())) {
                        isValid = false;
                        
                        // Create or update error message
                        let errorMsg = field.nextElementSibling;
                        if (!errorMsg || !errorMsg.classList.contains('error-message')) {
                            errorMsg = document.createElement('div');
                            errorMsg.classList.add('error-message');
                            field.parentNode.insertBefore(errorMsg, field.nextSibling);
                        }
                        
                        errorMsg.textContent = 'Please enter a valid email address';
                        field.classList.add('input-error');
                    }
                });
                
                // Password validation
                const passwordField = form.querySelector('input[type="password"][data-validate-password="true"]');
                const confirmPasswordField = form.querySelector('input[data-password-confirm="true"]');
                
                if (passwordField && passwordField.value.trim()) {
                    // Check password strength
                    if (passwordField.value.length < 8) {
                        isValid = false;
                        
                        let errorMsg = passwordField.nextElementSibling;
                        if (!errorMsg || !errorMsg.classList.contains('error-message')) {
                            errorMsg = document.createElement('div');
                            errorMsg.classList.add('error-message');
                            passwordField.parentNode.insertBefore(errorMsg, passwordField.nextSibling);
                        }
                        
                        errorMsg.textContent = 'Password must be at least 8 characters long';
                        passwordField.classList.add('input-error');
                    }
                    
                    // Check if passwords match
                    if (confirmPasswordField && confirmPasswordField.value.trim() && 
                        passwordField.value !== confirmPasswordField.value) {
                        isValid = false;
                        
                        let errorMsg = confirmPasswordField.nextElementSibling;
                        if (!errorMsg || !errorMsg.classList.contains('error-message')) {
                            errorMsg = document.createElement('div');
                            errorMsg.classList.add('error-message');
                            confirmPasswordField.parentNode.insertBefore(errorMsg, confirmPasswordField.nextSibling);
                        }
                        
                        errorMsg.textContent = 'Passwords do not match';
                        confirmPasswordField.classList.add('input-error');
                    }
                }
                
                if (!isValid) {
                    e.preventDefault();
                }
            });
            
            // Clear errors on input
            const inputs = form.querySelectorAll('input, textarea, select');
            inputs.forEach(input => {
                input.addEventListener('input', function() {
                    this.classList.remove('input-error');
                    
                    const errorMsg = this.nextElementSibling;
                    if (errorMsg && errorMsg.classList.contains('error-message')) {
                        errorMsg.textContent = '';
                    }
                });
            });
        });
    };
    
    // File upload preview
    const setupFileUploadPreview = () => {
        const fileInputs = document.querySelectorAll('input[type="file"][data-preview="true"]');
        
        fileInputs.forEach(input => {
            const previewContainer = document.querySelector(input.getAttribute('data-preview-container'));
            
            if (previewContainer) {
                input.addEventListener('change', function() {
                    previewContainer.innerHTML = '';
                    
                    if (this.files && this.files.length > 0) {
                        for (let i = 0; i < this.files.length; i++) {
                            const file = this.files[i];
                            
                            // Check if it's an image
                            if (file.type.match('image.*')) {
                                const reader = new FileReader();
                                
                                reader.onload = function(e) {
                                    const img = document.createElement('img');
                                    img.src = e.target.result;
                                    img.classList.add('file-preview');
                                    previewContainer.appendChild(img);
                                };
                                
                                reader.readAsDataURL(file);
                            } else {
                                // For non-image files, show file name
                                const fileInfo = document.createElement('div');
                                fileInfo.classList.add('file-info');
                                fileInfo.textContent = file.name;
                                previewContainer.appendChild(fileInfo);
                            }
                        }
                    }
                });
            }
        });
    };
    
    // Initialize all functions
    setupMobileMenu();
    setActiveNavItem();
    setupFormValidation();
    setupFileUploadPreview();
    
    // Add fade-in animation to elements with fade-in class
    const animateFadeIn = () => {
        const fadeElements = document.querySelectorAll('.fade-in');
        
        const fadeInObserver = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    entry.target.classList.add('visible');
                    fadeInObserver.unobserve(entry.target);
                }
            });
        }, { threshold: 0.1 });
        
        fadeElements.forEach(element => {
            fadeInObserver.observe(element);
        });
    };
    
    if ('IntersectionObserver' in window) {
        animateFadeIn();
    } else {
        // Fallback for browsers that don't support IntersectionObserver
        document.querySelectorAll('.fade-in').forEach(el => {
            el.classList.add('visible');
        });
    }
});
