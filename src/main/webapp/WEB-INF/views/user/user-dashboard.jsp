<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>User Dashboard | Yamaha Motors</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/static/images/yamaha_icon.png">
    <style>
        /* Global Reset */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f0f2f5;
            color: #2c3e50;
            padding-top: 70px;
        }

        /* Navbar */
        .navbar {
            background-color: #1f2d3d;
            color: white;
            padding: 15px 30px;
            position: fixed;
            top: 0;
            width: 100%;
            z-index: 1000;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
        }

        .navbar h2 {
            font-size: 22px;
            color: #ffffff;
        }

        .nav-links a {
            color: #ffffff;
            text-decoration: none;
            margin-left: 20px;
            font-weight: 500;
            transition: color 0.3s ease;
        }

        .nav-links a:hover {
            color: #00aced;
        }

        /* Dashboard Container */
        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 40px 20px;
        }

        .welcome-box {
            background-color: #ffffff;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 6px 20px rgba(0, 0, 0, 0.05);
            text-align: center;
        }

        .welcome-box h1 {
            font-size: 36px;
            margin-bottom: 10px;
            color: #1e3a8a;
        }

        .welcome-box p {
            font-size: 18px;
            color: #555;
            margin-bottom: 30px;
        }

        .action-links {
            display: flex;
            justify-content: center;
            gap: 25px;
            flex-wrap: wrap;
        }

        .action-links a {
            text-decoration: none;
            background-color: #1e3a8a;
            color: white;
            padding: 12px 24px;
            border-radius: 8px;
            font-weight: 600;
            transition: background-color 0.3s ease;
        }

        .action-links a:hover {
            background-color: #3b5998;
        }

        @media (max-width: 768px) {
            .nav-links {
                display: flex;
                flex-direction: column;
                align-items: flex-end;
                gap: 10px;
            }

            .action-links {
                flex-direction: column;
                gap: 15px;
            }
        }
    </style>
</head>
<body>

<!-- Navigation -->
<div class="navbar">
    <h2>Yamaha Motors</h2>
    <div class="nav-links">
        <!--
            <a href="${pageContext.request.contextPath}/user/profile">Profile</a>
            <a href="${pageContext.request.contextPath}/user/bikes">Bikes</a>
            <a href="${pageContext.request.contextPath}/user/schedule">Schedule</a>
            <a href="${pageContext.request.contextPath}/user/history">History</a>
        -->
        <a href="${pageContext.request.contextPath}/user/logout">Logout</a>
    </div>
</div>

<!-- Dashboard Main Content -->
<div class="container">
    <div class="welcome-box">
        <h1>Welcome, Rider!</h1>
        <p>Your premium Yamaha experience starts here.</p>

        <div class="action-links">
            <a href="${pageContext.request.contextPath}/user/bikes">View Bikes</a>
            <a href="${pageContext.request.contextPath}/user/showrooms">View Showrooms</a>
            <!-- <a href="${pageContext.request.contextPath}/user/schedule">Book/Test Ride</a> -->
            <a href="${pageContext.request.contextPath}/user/resetPassword">Reset Password</a>
        </div>
    </div>
</div>

</body>
</html>
