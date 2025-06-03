<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>User Login - Yamaha Showroom</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/static/images/yamaha_icon.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/icons/bootstrap-icons.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/admin-login.css">
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

        .forgot-password, .back-home {
            text-align: center;
            margin-top: 10px;
        }

        .captcha-container {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-bottom: 15px;
        }

        .captcha {
            font-size: 20px;
            font-weight: bold;
            background-color: #f0f0f0;
            padding: 10px;
            border-radius: 5px;
            flex: 1;
            text-align: center;
            margin-right: 10px;
        }

        .captcha-refresh button {
            background: none;
            border: none;
            cursor: pointer;
            color: blue;
            font-size: 2rem;
            transition: color 0.3s ease;
        }

        .captcha-refresh button i {
            -webkit-text-stroke: 1px;
            font-weight: bold;
        }

        .captcha-refresh button:hover {
            transform: scale(1.1);
        }

        .error {
            color: red;
            font-size: 14px;
            margin-top: 5px;
            display: none;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="login-box">
        <h2>User Login</h2>
        <c:if test="${not empty error}">
            <div class="alert alert-danger" id="errorAlert">${error}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/user/loginWithOtp" id="LoginForm" method="post">
            <label for="userEmail">User Email</label>
            <input type="email" id="userEmail" name="userEmail" value="${userEmail}" placeholder="Enter registered email" required>

            <label for="password">Password</label>
            <input type="password" id="password" name="password" placeholder="Enter password" required>
            <span class="text-muted">For first-time login, please enter the OTP sent to your registered email address.</span>

            <div class="captcha-container">
                <div class="captcha">
                    <canvas id="captchaCanvas" width="150" height="50"></canvas>
                </div>
                <div class="captcha-refresh">
                    <button type="button" id="refreshCaptcha" title="Refresh CAPTCHA">
                        <i class="bi bi-arrow-clockwise"></i>
                    </button>
                </div>
            </div>

            <label for="captchaInput">Enter CAPTCHA</label>
            <input type="text" name="captchaInput" id="captchaInput" required>
            <div id="captchaError" class="error">Invalid CAPTCHA. Please try again.</div>

            <input type="submit" id="submitBtn" value="Sign In" disabled>
        </form>

        <div class="forgot-password">
            <a href="${pageContext.request.contextPath}/user/resetPassword">Forgot Password?</a>
        </div>
        <div class="back-home">
            <a href="${pageContext.request.contextPath}/" class="back-link">Back to Home</a>
        </div>
    </div>
</div>

<script>
    let currentCaptcha = "";

    function generateCaptcha() {
        const characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        let captcha = "";
        for (let i = 0; i < 6; i++) {
            captcha += characters.charAt(Math.floor(Math.random() * characters.length));
        }
        return captcha;
    }

    function drawCaptcha(captcha) {
        const canvas = document.getElementById("captchaCanvas");
        const ctx = canvas.getContext("2d");

        ctx.clearRect(0, 0, canvas.width, canvas.height);
        ctx.fillStyle = "#f0f0f0";
        ctx.fillRect(0, 0, canvas.width, canvas.height);

        ctx.font = "28px Arial";
        ctx.fillStyle = "#000";
        ctx.textAlign = "center";
        ctx.textBaseline = "middle";
        ctx.fillText(captcha, canvas.width / 2, canvas.height / 2);

        // Optional noise
        for (let i = 0; i < 30; i++) {
            ctx.fillStyle = `rgba(${Math.random()*255},${Math.random()*255},${Math.random()*255},0.3)`;
            ctx.fillRect(Math.random()*canvas.width, Math.random()*canvas.height, 2, 2);
        }
    }

    function refreshCaptcha() {
        currentCaptcha = generateCaptcha();
        drawCaptcha(currentCaptcha);
        sessionStorage.setItem("captcha", currentCaptcha);
        document.getElementById("captchaInput").value = "";
        validateForm();
    }

    function validateCaptcha() {
        const input = document.getElementById("captchaInput").value.trim();
        const captchaStored = sessionStorage.getItem("captcha");
        return input === captchaStored;
    }

    function validateForm() {
        const email = document.getElementById("userEmail").value.trim();
        const password = document.getElementById("password").value.trim();
        const captchaInput = document.getElementById("captchaInput").value.trim();
        const submitBtn = document.getElementById("submitBtn");

        const isValid = email && password && captchaInput && validateCaptcha();
        document.getElementById("captchaError").style.display = validateCaptcha() || captchaInput === "" ? "none" : "block";
        submitBtn.disabled = !isValid;
    }

    document.addEventListener("DOMContentLoaded", () => {
        refreshCaptcha();

        document.getElementById("userEmail").addEventListener("input", validateForm);
        document.getElementById("password").addEventListener("input", validateForm);
        document.getElementById("captchaInput").addEventListener("input", validateForm);
        document.getElementById("refreshCaptcha").addEventListener("click", refreshCaptcha);

        document.getElementById("LoginForm").addEventListener("submit", function (e) {
            if (!validateCaptcha()) {
                document.getElementById("captchaError").style.display = "block";
                e.preventDefault();
            }
        });

        // --- FADING ERROR ---
        //const errorAlert = document.getElementById("errorAlert");
        // if (errorAlert) {
        //    setTimeout(() => {
        //        errorAlert.style.transition = "opacity 1s ease";
        //        errorAlert.style.opacity = "0";
        //        setTimeout(() => {
        //            errorAlert.style.display = "none";
        //        }, 1000); // Wait for fade-out to finish before hiding it
        //    }, 3000); // Wait 3 seconds before starting fade
        //}
    });
</script>
</body>
</html>
