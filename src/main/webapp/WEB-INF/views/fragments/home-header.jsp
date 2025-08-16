<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/home-header.css">
<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/static/images/yamaha_icon.png">

<header>
    <nav class="navbar">
        <button class="navbar-toggle" id="navbarToggle" aria-label="Toggle navigation">
            <span class="toggler-icon"></span>
        </button>
        <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/user/dashboard">
            <img src="${pageContext.request.contextPath}/static/images/yamaha-logo.png" alt="Yamaha" height="40">
        </a>
        <ul class="nav-links" id="navLinks">
            <li><a href="${pageContext.request.contextPath}/home" class="active">Home</a></li>
            <li><a href="${pageContext.request.contextPath}/">Bikes</a></li>
            <li><a href="${pageContext.request.contextPath}/">About Us</a></li>
            <!-- <li><a href="${pageContext.request.contextPath}/">Contact</a></li> -->
        </ul>
        <div class="auth-buttons" id="authButtons">
            <a href="${pageContext.request.contextPath}/admin/login" class="btn-login">Admin Login</a>
            <a href="${pageContext.request.contextPath}/user/login" class="btn-login">User Login</a>
        </div>
    </nav>
</header>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const navbarToggle = document.getElementById("navbarToggle");
        const navLinks = document.getElementById("navLinks");
        const authButtons = document.getElementById("authButtons");

        navbarToggle.addEventListener("click", function () {
            // Toggle active class on both elements
            navLinks.classList.toggle("active");
            authButtons.classList.toggle("active");

            // Toggle active class on the toggle button itself
            this.classList.toggle("active");
        });
    });
</script>
