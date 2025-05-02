<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/header.css">

<header>
    <nav class="navbar">
        <button class="navbar-toggle" id="navbarToggle" aria-label="Toggle navigation">
            <span class="toggler-icon"></span>
        </button>
        <div class="logo">Yamaha Motors</div>
        <ul class="nav-links" id="navLinks">
            <li><a href="#" class="active">Home</a></li> <!-- Add 'active' manually here -->
            <li><a href="${pageContext.request.contextPath}/">Bikes</a></li>
            <li><a href="${pageContext.request.contextPath}/">About</a></li>
            <li><a href="${pageContext.request.contextPath}/">Contact</a></li>
        </ul>
        <div class="auth-buttons" id="authButtons">
            <a href="${pageContext.request.contextPath}/admin/login" class="btn-admin">Admin Login</a>
        </div>
    </nav>
</header>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const navbarToggle = document.getElementById("navbarToggle");
        const navLinks = document.getElementById("navLinks");

        navbarToggle.addEventListener("click", function () {
            navLinks.classList.toggle("active");
        });
    });
</script>
