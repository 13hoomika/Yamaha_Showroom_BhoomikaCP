<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Forgot Password - Yamaha Showroom</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/static/images/yamaha_icon.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/icons/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/admin-login.css">
</head>
<body>
<div class="container">
    <div class="login-box">
        <h2>Forgot Password</h2>

        <form action="${pageContext.request.contextPath}/user/resetPassword" method="POST">
            <!-- Display message if any -->
            <c:if test="${not empty success}">
                <div class="alert alert-success">${success}</div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>

            <!-- Email Field -->
            <label for="userEmail">Email</label>
            <input type="email" name="userEmail" id="userEmail" required>

            <!-- New Password Field -->
            <label for="newPassword">New Password</label>
            <input type="password" name="newPassword" id="newPassword" required>

            <!-- Confirm New Password Field -->
            <label for="confirmNewPassword">Confirm New Password</label>
            <input type="password" name="confirmNewPassword" id="confirmNewPassword" required>

            <!-- Submit Button -->
            <input type="submit" value="Reset Password">
        </form>

        <div class="back-home">
            <a href="${pageContext.request.contextPath}/user/login" class="back-link">Click here to return to the login page</a>
        </div>
    </div>
</div>

</body>
</html>
