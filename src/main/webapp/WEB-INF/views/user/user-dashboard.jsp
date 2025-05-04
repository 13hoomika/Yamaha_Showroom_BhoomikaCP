<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Dashboard - Yamaha Motors</title>
    <style>
        body {
            margin: 0;
            font-family: 'Segoe UI', sans-serif;
            background-color: #eef2f5;
        }

        .header {
            background-color: #003366;
            color: white;
            padding: 20px;
            text-align: center;
        }

        .content {
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 40px 20px;
        }

        h1 {
            margin: 0;
            font-size: 32px;
        }

        p {
            font-size: 18px;
            color: #555;
            margin-bottom: 30px;
        }

        .buttons {
            display: flex;
            gap: 20px;
        }

        .button {
            padding: 12px 24px;
            font-size: 16px;
            text-decoration: none;
            color: white;
            background-color: #007bff;
            border-radius: 5px;
            transition: background-color 0.3s ease;
        }

        .button:hover {
            background-color: #0056b3;
        }

    </style>
</head>
<body>
<div class="header">
    <h1>Welcome to Yamaha Motors Dashboard</h1>
</div>

<div class="content">
    <p>Your destination for premium Yamaha bikes. What would you like to do today?</p>

    <div class="buttons">
        <a class="button" href="${pageContext.request.contextPath}/user/resetPassword">Reset Password</a>
        <a class="button" href="${pageContext.request.contextPath}/user/logout">Logout</a>
    </div>
</div>
</body>
</html>
