<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome to Yamaha Motors</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f4f4f4;
            text-align: center;
            padding-top: 100px;
        }
        h1 {
            color: #2c3e50;
            font-size: 36px;
        }
        p {
            color: #555;
            font-size: 18px;
        }
    </style>
</head>
<body>
    <h1>Welcome to Yamaha Motors</h1>
    <p>Your destination for premium Yamaha bikes</p>
    <a href="${pageContext.request.contextPath}/user/logout">Logout</a>

</body>
</html>
