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
        </div>
    </nav>
</header>

<!-- Admin Login Modal -->
<div id="adminLoginModal" class="modal">
    <div class="modal-content">
        <span class="close">&times;</span>
        <h2>Admin Login</h2>

        <!-- Message Display Area -->
        <div class="message-container">
            <c:if test="${not empty message}">
                <p class="text-success">${message}</p>
            </c:if>
            <c:if test="${not empty error}">
                <p class="text-danger">${error}</p>
            </c:if>
        </div>

        <form id="adminLoginForm" action="${pageContext.request.contextPath}/admin/sendOtp" method="post">
            <div class="form-group">
                <label for="adminEmail">Admin Email</label>
                <input type="email" id="adminEmail" name="adminEmail" value="${adminEmail}" placeholder="Enter Admin Email" required>
                <small class="text-muted">Admin Id is case-sensitive</small>
            </div>
            <button type="submit" class="btn-login">Send OTP</button>
        </form>

        <form id="otpForm" class="otp-section" action="${pageContext.request.contextPath}/admin/verifyOtp" method="post">
            <div class="form-group">
                <label>Enter OTP</label>
                <input type="text" name="otp" required
                       class="form-control" placeholder="Enter OTP">
                <div class="timer-container">
                    <span id="timer">02:00</span>
                </div>
                <input type="hidden" id="otpStartTimeHidden" value="${sessionScope.otpStartTime}" />

            </div>
            <button type="submit" class="btn-login">Verify OTP</button>
        </form>
    </div>
</div>

<!-- Timer Script -->
    <c:if test="${sessionScope.otpSent eq true}">
    <script>
        let timerOn = true;

        function timer(remaining) {
            var m = Math.floor(remaining / 60);
            var s = remaining % 60;

            m = m < 10 ? '0' + m : m;
            s = s < 10 ? '0' + s : s;
            document.getElementById('timer').innerHTML = m + ':' + s;
            remaining -= 1;

            if (remaining >= 0 && timerOn) {
                setTimeout(function() {
                    timer(remaining);
                }, 1000);
                return;
            }

            if (!timerOn) {
                return;
            }

            // Do timeout stuff here
            alert('OTP expired. Please request a new one.');
            const otpForm = document.querySelector("form[action='verifyOtp']");
            if (otpForm) {
                const button = otpForm.querySelector("button[type='submit']");
                const input = otpForm.querySelector("input[name='otp']");
                if (button) button.disabled = true;
                if (input) input.disabled = true;
            }
        }

        // Start countdown from 120 seconds (2 minutes)
        timer(120);
    </script>
    </c:if>

<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/auth.css">
<script>
    document.addEventListener('DOMContentLoaded', function() {
        const navbarToggle = document.getElementById('navbarToggle');
        const navLinks = document.getElementById('navLinks');
        const authButtons = document.getElementById('authButtons');

        navbarToggle.addEventListener('click', function() {
            this.classList.toggle('active');
            navLinks.classList.toggle('active');
            authButtons.classList.toggle('active');
        });

       // Modal handling
        const adminModal = document.getElementById('adminLoginModal');
        const adminLoginBtn = document.getElementById('adminLoginBtn');
        const closeButtons = document.querySelectorAll('.close');

        adminLoginBtn?.addEventListener('click', function(e) {
            e.preventDefault();
            adminModal.style.display = 'block';
        });

        closeButtons.forEach(btn => {
            btn.addEventListener('click', function() {
                adminModal.style.display = 'none';
            });
        });

        window.addEventListener('click', function(event) {
            if (event.target === adminModal) adminModal.style.display = 'none';
        });

    });
    </script>