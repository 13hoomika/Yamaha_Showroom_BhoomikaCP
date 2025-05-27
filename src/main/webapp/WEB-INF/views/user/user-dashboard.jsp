<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>User Dashboard | Yamaha Motors</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/static/images/yamaha_icon.png">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/icons/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/user-styles.css">

    <script src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.bundle.min.js"></script>

</head>
<body>

<!-- Navigation -->
<nav class="navbar navbar-expand-lg navbar-light fixed-top shadow-sm bg-white">
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

            <!-- User Avatar Dropdown -->
            <ul class="navbar-nav">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle d-flex align-items-center" href="#" id="userDropdown" role="button" data-bs-toggle="dropdown">
                       <img src="${empty loggedInUser.profileImage
                                    ? pageContext.request.contextPath.concat('/static/images/user-avatar.png')
                                    : pageContext.request.contextPath.concat(loggedInUser.profileImage)}"
                            alt="User Img" class="rounded-circle me-2" width="32" height="32" />

                        <span class="d-none d-lg-inline">${user.userName}</span>
                    </a>
                    <ul class="dropdown-menu dropdown-menu-end shadow">
                        <li class="px-3 py-2 text-center">
                            <form id="avatarUploadForm" action="${pageContext.request.contextPath}/user/uploadAvatar" method="post" enctype="multipart/form-data">
                                <label for="avatarInput" class="avatar-wrapper" style="cursor: pointer;">
                                    <img src="${empty loggedInUser.profileImage
                                                                        ? pageContext.request.contextPath.concat('/static/images/user-avatar.png')
                                                                        : pageContext.request.contextPath.concat(loggedInUser.profileImage)}"
                                                                alt="User Img" class="rounded-circle me-2" width="32" height="32" />
                                    <div class="avatar-overlay">
                                        <i class="bi bi-camera-fill text-white"></i>
                                    </div>
                                </label>
                                <input type="file" id="avatarInput" name="avatar" accept="image/*" style="display: none;" onchange="uploadAvatar()">
                            </form>
                            <div class="fw-semibold mt-2">${user.userName}</div>
                            <div class="text-muted small">${user.userEmail}</div>
                        </li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/user/getProfile"><i class="bi bi-person me-2"></i>Update Profile</a></li>
                        <!-- <li><a class="dropdown-item" href="${pageContext.request.contextPath}/user/testRides"><i class="bi bi-bicycle me-2"></i>My Test Rides</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/user/bookings"><i class="bi bi-receipt me-2"></i>My Bookings</a></li> -->
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/user/resetPassword"><i class="bi bi-shield-lock me-2"></i>Reset Password</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item text-danger" href="${pageContext.request.contextPath}/user/logout"><i class="bi bi-box-arrow-right me-2"></i>Logout</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>


<!-- Dashboard Main Content -->
<div class="container mt-3">
    <c:if test="${not empty success}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            ${success}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <c:if test="${not empty error}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${error}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>


    <div class="welcome-box">
        <h1 class="display-5 fw-bold mb-3">Welcome, ${loggedInUser.userName}!</h1>
        <p class="lead text-muted mb-4">Your premium Yamaha experience starts here.</p>

        <div class="action-links">
            <a href="${pageContext.request.contextPath}/user/bikes" class="action-btn">
                <i class="bi bi-bicycle me-2"></i> View Bikes
            </a>
            <a href="${pageContext.request.contextPath}/user/showrooms" class="action-btn">
                <i class="bi bi-geo-alt me-2"></i> View Showrooms
            </a>
            <a href="${pageContext.request.contextPath}/user/resetPassword" class="action-btn">
                <i class="bi bi-shield-lock me-2"></i> Reset Password
            </a>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/static/js/uploadAvatar.js"></script>

</body>
</html>
