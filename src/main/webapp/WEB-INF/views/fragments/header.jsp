<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/header.css">
<header>
    <nav class="navbar">
        <button class="navbar-toggle" id="navbarToggle">
            <span class="toggler-icon"></span>
        </button>
        <div class="logo">Yamaha Motors</div>
        <ul class="nav-links" id="navLinks">
            <li><a href="#">Home</a></li>
            <li><a href="${pageContext.request.contextPath}/allBikes">Bikes</a></li>
            <li><a href="${pageContext.request.contextPath}/">About</a></li>
            <li><a href="${pageContext.request.contextPath}/">Contact</a></li>
        </ul>
        <div class="auth-buttons" id="authButtons">
            <a href="#" class="btn-admin" id="adminLoginBtn">Admin Login</a>
            <a href="${pageContext.request.contextPath}/user/register" class="btn-user">User Register</a>
        </div>
    </nav>
</header>

<!-- Admin Login Modal -->
<div id="adminLoginModal" class="modal">
    <div class="modal-content">
        <span class="close">&times;</span>
        <h2>Admin Login</h2>

        <c:if test="${not empty param.adminLoginError}">
            <div class="error-message">${param.adminLoginError}</div>
            <script>
                document.addEventListener('DOMContentLoaded', function() {
                    document.getElementById('adminLoginModal').style.display = 'block';
                    document.getElementById('adminName').value = "${param.adminName}";
                });
            </script>
        </c:if>

        <form id="adminLoginForm" action="${pageContext.request.contextPath}/admin/login" method="post">
            <div class="form-group">
                <label for="adminName">Admin Name</label>
                <input type="text" id="adminName" name="adminName" value="${adminName}" placeholder="Enter Admin Name" required>
                <small class="text-muted">Admin name is case-sensitive</small>
            </div>
            <button type="submit" class="btn-login">Send OTP</button>
        </form>
    </div>
</div>


<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/auth.css">
<script>
    document.addEventListener('DOMContentLoaded', function() {
        // Navbar toggle (unchanged)
        const navbarToggle = document.getElementById('navbarToggle');
        const navLinks = document.getElementById('navLinks');
        const authButtons = document.getElementById('authButtons');
        navbarToggle.addEventListener('click', function() {
            this.classList.toggle('active');
            navLinks.classList.toggle('active');
            authButtons.classList.toggle('active');
        });

        // Modal elements
        const adminModal = document.getElementById('adminLoginModal');
        const adminLoginBtn = document.getElementById('adminLoginBtn');
        const closeButtons = document.querySelectorAll('.close');


        // Show modals
        adminLoginBtn?.addEventListener('click', showAdminModal);

        // Close modals
        closeButtons.forEach(btn => {
            btn.addEventListener('click', closeModal);
        });
        window.addEventListener('click', function(event) {
            if (event.target == adminModal) closeModal();
            if (event.target == userModal) closeModal();
        });

        // Form submissions
        emailForm?.addEventListener('submit', handleEmailSubmit);
        otpForm?.addEventListener('submit', handleOtpSubmit);
        resendOtpLink?.addEventListener('click', handleResendOtp);

        function showAdminModal(e) {
            e.preventDefault();
            adminModal.style.display = 'block';
        }

        function closeModal() {
            adminModal.style.display = 'none';
            userModal.style.display = 'none';
        }

    });
</script>