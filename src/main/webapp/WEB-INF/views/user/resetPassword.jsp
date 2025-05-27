<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Forgot Password - Yamaha Showroom</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/static/images/yamaha_icon.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/admin-login.css">
    <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=Montserrat:wght@400;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
    <style>
        input[type="submit"] {
            background-color: var(--blue);
            color: white;
            padding: 12px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-weight: bold;
            text-transform: uppercase;
            letter-spacing: 1px;
            transition: background-color 0.3s ease;
        }

        input[type="submit"]:disabled {
            background-color: #cccccc;
            cursor: not-allowed;
        }

        .back-home {
            text-align: center;
            margin-top: 10px;
        }

    </style>
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
