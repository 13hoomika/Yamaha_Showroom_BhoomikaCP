<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Login - Yamaha Showroom</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/static/images/yamaha_icon.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/admin-login.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container">
    <div class="login-box">
        <h2>Admin Login</h2>

        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <!-- Email Form -->
        <form action="${pageContext.request.contextPath}/admin/sendOtp" method="post">
            <label for="adminEmail">Admin Email</label>
            <input type="email" id="adminEmail" name="adminEmail" value="${adminEmail}" placeholder="Enter admin email" required>
            <button type="submit" id="sendOtpBtn">Send OTP</button>
        </form>

        <!-- OTP Form -->
        <c:if test="${sessionScope.otpSent eq true}">
            <form action="${pageContext.request.contextPath}/admin/verifyOtp" method="post">
                <label>Enter OTP</label>
                <input type="text" name="otp" required placeholder="Enter OTP">
                <div class="timer-container">
                    <span>OTP expires in: </span><span id="timer"></span>
                </div>
                <input type="hidden" name="adminEmail" value="${adminEmail}">
                <button type="submit">Verify & Login</button>
            </form>
        </c:if>

        <!-- Back to Home Link -->
        <div class="back-home">
            <a href="${pageContext.request.contextPath}/" class="back-link">Back to Home</a>
        </div>
    </div>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        const sendOtpBtn = document.getElementById('sendOtpBtn');

        <c:if test="${sessionScope.otpSent eq true}">
        if (sendOtpBtn) {
            sendOtpBtn.disabled = true;
        }
        startResendTimer(120);
        </c:if>

        function startResendTimer(duration) {
            let timer = duration;
            const timerDisplay = document.getElementById('timer');
            const interval = setInterval(function () {
                const minutes = String(Math.floor(timer / 60)).padStart(2, '0');
                const seconds = String(timer % 60).padStart(2, '0');
                if (timerDisplay) timerDisplay.textContent = minutes + ":" + seconds;
                if (--timer < 0) {
                    clearInterval(interval);
                    if (timerDisplay) timerDisplay.textContent = "";

                    // Re-enable send OTP button
                    if (sendOtpBtn) {
                        sendOtpBtn.disabled = false;
                        sendOtpBtn.textContent = "Send OTP";
                    }
                }
            }, 1000);
        }
    });
</script>

</body>
</html>