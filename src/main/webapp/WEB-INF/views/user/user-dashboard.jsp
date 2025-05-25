<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>User Dashboard | Yamaha Motors</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/static/images/yamaha_icon.png">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/icons/bootstrap-icons.css">

    <script src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.bundle.min.js"></script>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/user-styles.css">

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>

<!-- Navigation -->
<nav class="navbar navbar-expand-lg navbar-light fixed-top shadow">
    <div class="container-fluid">
        <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/user/dashboard">
            <img src="${pageContext.request.contextPath}/static/images/yamaha-logo.png" alt="Yamaha" height="40">
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarUserLinks">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse justify-content-end" id="navbarUserLinks">
            <ul class="navbar-nav me-3">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/user/bikes">Bikes</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/user/showrooms">Showrooms</a>
                </li>
            </ul>

            <!-- User Avatar with Dropdown -->
            <ul class="navbar-nav">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle d-flex align-items-center" href="#" id="userDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <img src="${pageContext.request.contextPath}/static/images/user-avatar.png" alt="User" class="rounded-circle me-2" width="32" height="32">
                     </a>
                    <ul class="dropdown-menu dropdown-menu-end shadow" aria-labelledby="userDropdown">
                        <li class="px-3 py-2 text-center">
                            <a href="${pageContext.request.contextPath}/user/updateProfileImg" class="avatar-wrapper" data-bs-toggle="tooltip">
                                <img src="${pageContext.request.contextPath}/static/images/user-avatar.png" alt="User" class="rounded-circle user-avatar">
                                <div class="avatar-overlay d-flex justify-content-center align-items-center">
                                    <i class="bi bi-pencil-fill text-white"></i>
                                </div>
                            </a>
                            <div class="fw-semibold">${user.userName}</div>
                            <div class="text-muted small">${user.userEmail}</div>
                        </li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/user/getProfile">Update Profile</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/user/resetPassword">Reset Password</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item text-danger" href="${pageContext.request.contextPath}/user/logout">Logout</a></li>
                    </ul>
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
</div>

</body>
</html>
