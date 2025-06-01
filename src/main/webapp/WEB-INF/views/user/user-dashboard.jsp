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
    <div class="wrapper">
    <jsp:include page="../fragments/user-header.jsp" />
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
    </div>
<script src="${pageContext.request.contextPath}/static/js/uploadAvatar.js"></script>
</body>
</html>
