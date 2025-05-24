<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>User Dashboard | Yamaha Motors</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/static/images/yamaha_icon.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/user-styles.css">

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"/>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>

<!-- Navigation -->
<nav class="navbar navbar-expand-lg navbar-light fixed-top shadow">
    <div class="container-fluid">
        <a class="navbar-brand fw-bold" href="#">Yamaha Motors</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarUserLinks" aria-controls="navbarUserLinks" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse justify-content-end" id="navbarUserLinks">
            <ul class="navbar-nav">
                <!-- <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/user/bikes">Bikes</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/user/showrooms">Showrooms</a
                </li> -->
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/user/getProfile">Update Profile</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/user/logout">Logout</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- Dashboard Main Content -->
<div class="container">
    <div class="welcome-box">
        <h1>Welcome, ${loggedInUser.userName}!</h1>
        <p>Your premium Yamaha experience starts here.</p>

        <div class="action-links">
            <a href="${pageContext.request.contextPath}/user/bikes">View Bikes</a>
            <a href="${pageContext.request.contextPath}/user/showrooms">View Showrooms</a>
            <a href="${pageContext.request.contextPath}/user/resetPassword">Reset Password</a>
        </div>
    </div>
</div>

</body>
</html>
